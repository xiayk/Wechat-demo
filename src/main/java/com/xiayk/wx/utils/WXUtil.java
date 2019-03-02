package com.xiayk.wx.utils;

import java.io.IOException;

/**
 * @Author: XiaYk
 * @create:2019-02-28
 **/
public class WXUtil {
    /**
     * 微信公众号token
     */
    public static final String TOKEN = "xiayk";
    private static final String APPID = "wx3f05b0102022af4c";
    private static final String AppSecret = "c2fdfb7ee828b995e64124e3167c8e6e";
    public static final String EncodingAESKey = "p85zzh7UVn0beetpytcCHQXQqdIZMrI3mPZMz4FgItA";
    /**
     * 图灵机器人api Key
     */
    public static final String TULINGAPIKEY = "3f00650b1b294711a73d1a5f3b723d91";

    public static String getAccessToken(){
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+EncodingAESKey;
        try {
            String a = TuLingUtil.urlGet(getTokenUrl);
        }catch (IOException e){

        }
        return "";
    }
}
