package com.thinkgem.jeesite.common.websocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.inoculate.service.QueneService;

/**
 * 
 * @author chenmaolong
 * @date 2017年3月4日 上午11:46:41
 * @description TODO socket 及时通讯
 */
@ServerEndpoint(value = "/checkOfWebSocket/{id}")
public class WebSocket {

	private static Logger logger = LoggerFactory.getLogger(WebSocket.class);

	public WebSocket() {
	}
	

	/**
	 * 连接对象集合
	 */
	private static Map<String, Session> sessionMap = new HashMap<String, Session>();

	private String id;

	@OnMessage
	public synchronized void onMessage(String message, Session session) throws IOException, InterruptedException {
		try {
			logger.info("客户端发送过来数据: " + message);

			Map<String, String> params = null;
			String responseText = "";
			if (StringUtils.isNotEmpty(message)) {
				params = new HashMap<String, String>();
				params.put("id", this.id);

				responseText = JSONObject.toJSONString(responseText);
				logger.info("服务器send信息responseText: " + responseText);
				// System.out.println("服务器send信息responseText: " + responseText);
			}
			// session.getBasicRemote().sendText(responseText);
			logger.info("当前SOCKET在线人数：" + sessionMap.size() + "=====当前登录ID: " + id);
			// System.out.println("当前SOCKET在线人数：" + sessionMap.size() +
			// "=====当前登录ID: " + id);
		} catch (Exception e) {
			logger.error("WebSocket异常信息：", e);
			// System.out.println("WebSocket异常信息："+e);
			e.printStackTrace();
			remove(this.id);
		}
	}

	/**
	 * 错误信息响应
	 * 
	 * @param throwable
	 */
	@OnError
	public void onError(Throwable throwable) {
		logger.info(throwable.getMessage());
		remove(this.id);
	}

	@SuppressWarnings("unchecked")
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "id") String id) {
		this.id = id;
		put(id, session);
		logger.info("用户ID:" + this.id + "新链接打开");
		if(StringUtils.isBlank(id) || !id.contains("_")){
			logger.info("id=" + id + ",id异常,连接失败");
			return ;
		}
		String[] ids = id.split("_");
		if(ids.length < 2){
			logger.info("id=" + id + ",id异常,连接失败");
			return ;
		}
		final String localcode = ids[1];
		if(StringUtils.isBlank(localcode)){
			logger.info("id=" + id + ",id异常,连接失败");
			return ;
		}
		
		//连接成功发送叫号操作
		if(id.contains("queue_")){
			logger.info("用户ID:" + this.id + "新链接打开,刷新叫号信息");
			QueneService ser = SpringContextHolder.getBean(QueneService.class);
			ser.refresh(localcode);
		}
		
		//推送屏连接成功操作
		if(id.contains("notice_")){
			logger.info("用户ID:" + this.id + "新链接打开,刷新推送信息");
			//对文件进行读写操作
			File file = new File("notice.dat");
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("读文件地址异常推送", e);
				}
			}
			BufferedReader br;
			StringBuilder str = new StringBuilder();
			String context = null;
			String video = null;
			try {
				br = new BufferedReader(new FileReader(file));// 读取原始json文件  
				String line = null;
				while((line = br.readLine()) != null){
					System.out.println(line);//一次读一行，并不能识别换行
					str.append(line);
				}
				if(str.length() != 0){
					Map<String, Object> dataJson = (Map<String, Object>) JsonMapper.fromJsonString(str.toString(), Map.class);
					context = (String) dataJson.get("text");
					video =  (String) dataJson.get("video");
					//打印日志
					logger.info("推送信息json:" + JsonMapper.toJsonString(dataJson));
				}
				logger.info("推送信息json:" + JsonMapper.toJsonString(null));
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("读文件内容异常推送", e);
			}
			QueneService ser = SpringContextHolder.getBean(QueneService.class);
			ser.refreshMsg(context,video,localcode);
		}
		
		
		//第一次连接的时候创建 线程
		if(!Global.getInstance().isCreateSocketThread(localcode)){
			logger.info("创建留观屏推送线程");
			new Thread(){
				@Override
				public void run() {
					QueneService ser = SpringContextHolder.getBean(QueneService.class);
					while(true){
						String json = ser.getObserv(localcode);
						logger.info("获取留观数据:" + json);
						for (String key : sessionMap.keySet()) {
							if (!key.contains("observ_" + localcode))
								continue;
							logger.info("发送留观消息给用户ID:" + key + "  msg:" + json);
							Session session = sessionMap.get(key);
							synchronized (session) {
								try {
									session.getBasicRemote().sendText(json);
								} catch (IOException e) {
									logger.error("发送异常留观", e);
								}
							}
						}
						try {
							Thread.sleep(15*1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}				
			}.start();
		}

	}

	@OnClose
	public void onClose() {
		logger.info("用户ID:" + this.id + "链接中断");
		System.out.println("用户ID:" + this.id + "链接中断");
		remove(this.id);
	}

	public synchronized void put(String id, Session session) {
		// public void put(String id, Session session) {
		sessionMap.put(id, session);
	}

	public synchronized void remove(String id) {
		// public void remove(String id) {
		sessionMap.remove(id);
	}

	/**
	 * 
	 * @author chenmaolong
	 * @date 2017年3月4日 上午11:48:08
	 * @description TODO 主动发消息通知连接中用户
	 * @param message
	 *
	 */
	public static void sendBroadCast(String message, String localcode) {
		try {
			for (String key : sessionMap.keySet()) {
				logger.info("发送消息");
				if (!key.contains("queue_" + localcode))
					continue;
				logger.info("发送消息给用户ID:" + key + "   msg:" + message);
				Session session = sessionMap.get(key);

				// 线程同步控制并发访问
				synchronized (session) {
					session.getBasicRemote().sendText(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author zhouqj
	 * @date 2017年4月6日 下午5:27:15
	 * @description 
	 *		TODO  推送消息
	 * @param message
	 *
	 */
	public static void sendMsgCast(String message,String localcode) {
		try {
			for (String key : sessionMap.keySet()) {
				logger.info("发送消息给用户ID:" + key + "   msg:" + message);
				if (!key.contains("notice_" + localcode))
					continue;
				Session session = sessionMap.get(key);

				// 线程同步控制并发访问
				synchronized (session) {
					session.getBasicRemote().sendText(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
