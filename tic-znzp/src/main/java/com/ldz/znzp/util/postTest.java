package com.ldz.znzp.util;

import net.sf.json.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


/**
 * Created by Administrator on 2018/10/23.
 */
public class postTest {
    public static void main(String[] args) {
        JsonParser parse =new JsonParser();  //创建json解析器
        try {
            JsonArray json=(JsonArray) parse.parse(new FileReader("D:\\AJC099.json"));  //创建jsonObject对象
            for(int i=0;i<json.size();i++){
                System.out.println("JSON报文:"+json.get(i).toString());
                fahuo("http://47.98.39.45:9095/api/device/gps",json.get(i).toString());
                System.out.println("第"+i+"条报文 ");
            }


        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//        fahuo("","");
    public static void fahuo(String url2,String json){
        url2="http://47.98.39.45:9095/api/device/gps";
        boolean isSend = true;
//      System.out.println("调用接口"+"调用接口fahuo");
      try {
//      System.out.println("调用接口开始循环"+"调用接口fahuo循环");
       DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url2);  //请求的连接
         try {
           StringEntity s = new StringEntity(json.toString());  //请求的参数
           s.setContentEncoding("UTF-8");
           s.setContentType("application/json");//发送json数据需要设置contentType
           post.setEntity(s);
           HttpResponse res = client.execute(post);
           if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                System.out.println("请求网络成功："+"200");
//             String result = EntityUtils.toString(res.getEntity());// 返回json符串：
//             System.out.println("支付成返回的结果11："+result);
           }else{
//           System.out.println("请求网络成功："+res.getEntity().toString());
            System.out.println("请求网络失败："+"400");
           }
             Thread.sleep(1000*1);
          } catch (Exception e) {
           throw new RuntimeException(e);
          } 

        } catch (Exception e) {
          e.printStackTrace();
        }

    }
}
