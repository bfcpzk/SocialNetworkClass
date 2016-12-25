package sinaWeibo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by zhaokangpan on 2016/11/25.
 */
public class TestHeader {

    //工具类
    private static HttpClient client = new DefaultHttpClient();//httpclient

    public static void getByTime(){}

    public static void main(String[] args) throws IOException,ParseException{

        String url = "https://p.3.cn/prices/mgets?callback=jQuery353071&type=1&area=1_72_2799_0&pdtk=&pduid=1258239888&pdpin=&pdbp=0&skuIds=J_3158054";
        HttpGet get = new HttpGet(url);
        HttpResponse res = client.execute(get);
        String responseContent = null; // 响应内容
        HttpEntity entity = res.getEntity();
        responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
        String json = responseContent.split("\\[")[1].split("\\]")[0];
        System.out.println(json);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        JsonObject jo = jsonparer.parse(json).getAsJsonObject();
        String id = jo.get("id").getAsString();
        String p = jo.get("p").getAsString();
        String m = jo.get("m").getAsString();
        String op = jo.get("op").getAsString();
        System.out.println("id:" + id + " p:" + p + " m:" + m + " op:" + op);
        /* String keyword = "房地产";
        String startTime = "2016-11-08";
        String endTime = "2016-11-18";
        String url = "http://s.weibo.com/weibo/" + keyword + "&timescope=custom:" + startTime + ":" + endTime + "&page=";
        System.out.println(url);
        HttpGet get = new HttpGet(url);
        String host = "s.weibo.com";
        String user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36";
        String cookie = "SWB=usrmdinst_20; _s_tentry=-; Apache=342389060785.7283.1480070806432; SINAGLOBAL=342389060785.7283.1480070806432; ULV=1480070806441:1:1:1:342389060785.7283.1480070806432:; WBtopGlobal_register_version=5b56985b93d98642; SCF=AjD4wGLjplA6aS9ISYaZOT93yVQSS9XOEg0LTW9RAsMUzH1Brk-bkNbifqq-ivkn14tOVIS6o3A042y0uPplIpM.; SUB=_2A251PGbqDeTxGeNI6VUW9y3Mzj6IHXVWSN8irDV8PUNbmtANLXbYkW94qgnsPYAX6B4rc_mDhpLjZuNYbA..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhAT2amv_CEHv-mRx.bvGNf5JpX5K2hUgL.Fo-ceoMNS0e7SKz2dJLoIE8b9JS7wJjLxK-LBo5L12qLxKqL1-eL12zLxK-L12qL12zt; SUHB=0P1cu_R8OXUOoV; ALF=1480675642; SSOLoginState=1480070842; un=bfcpzk@163.com; WBStorage=2c466cc84b6dda21|undefined";
        get.setHeader("cookie",cookie);
        get.setHeader("host",host);
        get.setHeader("user-agent",user_agent);
        for(int i = 1 ; i < 10 ; i++){
            try{
                Thread.sleep(2000);
            }catch(Exception e){
                e.printStackTrace();
            }
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            System.out.println("page:" + i);
            System.out.println(responseContent);
        }*/
    }
}
