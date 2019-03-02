package com.xiayk.wx.utils;

import com.thoughtworks.xstream.XStream;
import com.xiayk.wx.entity.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";

    /**
     * xml 转 map
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();

        //从 request中获取输入流
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for(Element e:list){
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }


    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(String.valueOf(new Date().getTime()));
        text.setContent(content);
        return textMessageToXml(text);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("请按照菜单提示进行操作：\n\n");
        sb.append("1. 介绍\n");
        sb.append("2. 博客\n");
        sb.append("\nHOME：https://xiayk.com/\n");
        sb.append("回复“菜单”调出此菜单... ");
        return sb.toString();
    }

    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("介绍微信公众号开发");

        return sb.toString();
    }

    public static String secondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("https://blog.xiayk.com/");

        return sb.toString();
    }

    public static String tuLing(String str){
        StringBuffer sb = new StringBuffer();
        sb.append(TuLingUtil.tuLing(str));
        return sb.toString();
    }
}
