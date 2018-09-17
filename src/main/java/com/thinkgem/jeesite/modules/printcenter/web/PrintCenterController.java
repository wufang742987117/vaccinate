package com.thinkgem.jeesite.modules.printcenter.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.modules.printcenter.vo.PrintCenterVo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * Created by wcy on 2016/8/2.
 */
@Controller
@RequestMapping(value = "${adminPath}/printcenter/printcenter")
public class PrintCenterController {
    private static String filename = "E:\\ideaworksp\\vaccinatehtai\\src\\main\\resources\\printcenterxml.xml";

    @RequestMapping("printlist")
    public String form(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println(UserUtils.getUser().getId());
        return "modules/printcenter/printCenter";
    }

    @RequestMapping("printbyid")
    public void PrintById(User user, HttpServletRequest request, HttpServletResponse response, Model model){
        String printid = request.getParameter("printid");
        String addr = getFileAdrr(printid);
        System.out.print("-->"+addr);
    }

    @SuppressWarnings("unchecked")
	public String getFileAdrr(String id){
        String addr = "";
        try{
            SAXReader reader = new SAXReader();
            //本地使用这段
			File file = new File(filename);
            //服务器上使用这段
//            URL url = new URL("");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            InputStream file = conn.getInputStream();

            Document document = reader.read(file);
            Element root = document.getRootElement();
            List<Element> childElements = root.elements();

            List<PrintCenterVo> plist = new ArrayList<>();
            for (Element child : childElements)
            {
                PrintCenterVo p = new PrintCenterVo();
                List<Element> elementList = child.elements();
                for (Element ele : elementList)
                {
                    if("id".equals(ele.getName()))
                    {
                        p.setId(ele.getText());
                    }
                    if("name".equals(ele.getName()))
                    {
                        p.setName(ele.getText());
                    }
                    if("addr".equals(ele.getName()))
                    {
                        p.setAddr(ele.getText());
                    }
                }
                plist.add(p);
            }
            for (int i = 0; i<plist.size();i++){
                if (id.equals(plist.get(i).getId())){
                    addr = plist.get(i).getAddr();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return addr;
    }

}
