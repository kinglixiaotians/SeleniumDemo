package com.selenium.Login;

import com.selenium.test.baidu.OcrTest;
import com.selenium.utils.baidu.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginValidate {
    public static final String APP_ID = "15819857";
    public static final String API_KEY = "wC9kBK4NsZFCelWECe5utmGu";
    public static final String SECRET_KEY = "tS1XI21mFXd00V0s4wn8iMqGp2DAamWs";

    public static String orcImage(String imageUrl) {
        AipOcr client = new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        //String path = "E:\\2019\\test.png";
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        JSONObject jsonObject = client.basicGeneral(imageUrl,options);
        JSONArray array = jsonObject.getJSONArray("words_result");
        JSONObject json = array.getJSONObject(0);
        String words = json.getString("words");
        System.out.println(words);
        System.out.println(jsonObject.toString(2));
        return words;
    }

    public static void main(String[] args) {
        String ss = OcrTest.orcImage("E:\\2019\\test.png");
        System.out.println("sss"+ss);
    }
}
