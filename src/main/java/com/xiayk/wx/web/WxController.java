package com.xiayk.wx.web;

import com.xiayk.wx.utils.CheckUtil;
import com.xiayk.wx.utils.MessageUtil;
import com.xiayk.wx.utils.TuLingUtil;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class WxController {

    protected Logger log = Logger.getLogger(WxController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void main(HttpServletRequest req, HttpServletResponse resp){
        try {
            String signature = req.getParameter("signature");
            String timestamp = req.getParameter("timestamp");
            String nonce = req.getParameter("nonce");
            String echostr = req.getParameter("echostr"); //随机字符串
            PrintWriter out = resp.getWriter();
            if(CheckUtil.checkSignature(signature, timestamp, nonce)){
                log.debug("token验证成功");
                out.print(echostr);
            }
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void wx(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            Map<String,String> map = MessageUtil.xmlToMap(req);
            //用户openid
            String fromUserName = map.get("FromUserName");
            //
            String toUserName = map.get("ToUserName");
            System.out.println(fromUserName+"   "+toUserName);
            req.getSession().setAttribute("user", fromUserName);
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String message = null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                System.out.println(content);
                if("1".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                }else if("2".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
                }else if("菜单".equals(content) || "？".equals(content) || "0".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else{
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.tuLing(content));
                }
            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }
            }else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
                String picUrl = map.get("PicUrl");
                String mediaId = map.get("MediaId");
                System.out.println(picUrl+  "    " + mediaId);
                message = MessageUtil.initText(toUserName, fromUserName,picUrl);
            }
            log.debug(content + "--->" + message);
            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }
}