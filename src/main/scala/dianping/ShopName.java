package dianping;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaokangpan on 2016/11/24.
 */
public class ShopName {

    //工具类
    private static HttpClient client = new DefaultHttpClient();//httpclient
    //private static Jdbc_Util db = new Jdbc_Util();//数据库操作类

    public static List<String> getName(String url) throws IOException{
        List<String> namelist = new ArrayList<String>();
        HttpGet get = new HttpGet(url);
        HttpResponse res = client.execute(get);
        String responseContent = null; // 响应内容
        HttpEntity entity = res.getEntity();
        responseContent = EntityUtils.toString(entity, "UTF-8");

        String[] listH4 = responseContent.split("<h4>");
        for(int i = 3 ; i < listH4.length - 2 ; i++){
            namelist.add(listH4[i].split("</h4>")[0]);
            System.out.println(listH4[i].split("</h4>")[0]);
        }
        return namelist;
    }




    public static void main(String[] args) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("wdSZ.txt")),"utf-8"));
        String base_url = "http://www.dianping.com/search/category/7/45/g149p";
        List<String> resultList;
        //String sql = "";
        String province = "深圳";
        String category = "舞蹈";
        for(int i = 1 ; i <= 9 ; i++){
            System.out.println("第" + i + "页");
            try{
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
            }
            resultList = getName(base_url + i + "m3");
            for(int j = 0 ; j < resultList.size() ; j++){
                bw.write(resultList.get(j) + "\t" + province + "\t" + category + "\n");
                //sql = "insert into dianping (`name`,`province`) values ('" + resultList.get(j) + "', '" + province + "')";
                //db.add(sql);
            }
        }
        bw.close();
    }
}
