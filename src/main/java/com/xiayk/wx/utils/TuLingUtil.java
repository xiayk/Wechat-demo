package com.xiayk.wx.utils;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *http://www.tuling123.com/openapi/api?key=3f00650b1b294711a73d1a5f3b723d91&info=
 */
public class TuLingUtil {
    protected static Logger log = Logger.getLogger(TuLingUtil.class);

    public static String tuLing(String str){
        try {
            String INFO = URLEncoder.encode(str, "utf-8");
            String getURL = "http://www.tuling123.com/openapi/api?key=" + WXUtil.TULINGAPIKEY + "&info=" + INFO;
            String sb = urlGet(getURL);
            JSONObject jsonObject =  JSONObject.fromObject(sb);
            String res = "";
            if(200000==Integer.parseInt(jsonObject.get("code").toString())){
                res = jsonObject.getString("url");
            }else{
                res = jsonObject.getString("text");
            }
            log.debug(res);
            return res;
        }catch (Exception e){
            return "error: "+e.getMessage();
        }
    }

    public static String urlGet(String getURL) throws IOException {
        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();

        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        // 断开连接
        connection.disconnect();
        return sb.toString();
    }
}
