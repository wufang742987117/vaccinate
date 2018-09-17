/* websocket */
var socket;  
var connectStatus = false;

function Connect(){  
    try{  
    	if(socket && socket.readyState == 3){
    		socket.close();
    		socket = null;
    	}
        socket=new WebSocket('ws://127.0.0.1:40000');              
    }catch(e){  
        console.error("WebSocket 连接失败" ,e) 
        return;  
    }  
    socket.onopen = sOpen;  
    socket.onerror = sError;
    socket.onmessage= sMessage;
    socket.onclose= sClose;
}  
function sOpen(){
    console.info("WebSocket sOpen" ,"打开连接成功")
    connectStatus = true;
}
function sError(e){
	console.error("WebSocket sError" ,e) 
	error("签字程序连接失败,请刷新页面再试");
	var iframe = $(document.getElementsByTagName("iframe"));
	if(iframe && iframe.length > 0){
		iframe[0].contentWindow.layer.closeAll();
		iframe[0].contentWindow.error("签字程序连接失败,请刷新页面再试");
		if(iframe[0].contentWindow.signatureError){
			iframe[0].contentWindow.signatureError();
		}
	}
 	socket.close();
}
function sMessage(msg){  
	console.info("WebSocket sMessage" ,msg) 
	if(isExitsFunction('ifSignatureSuccess')){
		ifSignatureSuccess(msg.data);
	}
}
function sClose(e){
	console.info("WebSocket sClose" ,e) 
	connectStatus = false;
}  

function Send(recordId, url){
	if(socket ){
		socket.close();
	}
	Connect();
	var intCount = 0;
	var interval = setInterval(function(){
		console.info("sockect 等待连接...")
		if(intCount++ > 20){
			 clearInterval(interval);
		}
		if(connectStatus && socket){
	    	  socket.send("open," + url + "," + recordId);
	    	  clearInterval(interval);
		}
	},100);
} 

function dofinish(recordId, url){
	if(socket ){
		socket.close();
	}
	Connect();
	var intCount = 0;
	var interval = setInterval(function(){
		console.info("sockect 等待连接...")
		if(intCount++ > 20){
			 clearInterval(interval);
		}
		if(connectStatus && socket){
	    	  socket.send("finish");
	    	  clearInterval(interval);
		}
	},100);
} 

function Close(){
    socket.close();
}
/* websocket end */