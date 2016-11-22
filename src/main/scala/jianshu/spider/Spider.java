package jianshu.spider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jianshu.model.Article;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import util.Jdbc_Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaokangpan on 2016/11/7.
 */
public class Spider {

    //工具类
    private static HttpClient client = new DefaultHttpClient();//httpclient
    private static JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
    private static Jdbc_Util db = new Jdbc_Util();//数据库操作类

    //正则规则
    //过滤html
    private static String regEx_html="<[^>]+>";

    private static String article_url = "<h4 class=\"title\"><a target=\"_blank\" href=\"(.*?)\">.*?</a></h4>";

    public static List<String> getArticleUrl(String url) throws IOException{

        List<String> url_list = new ArrayList<String>();
        HttpGet get = new HttpGet(url);
        HttpResponse res = client.execute(get);
        String responseContent = null; // 响应内容
        HttpEntity entity = res.getEntity();
        responseContent = EntityUtils.toString(entity, "UTF-8");

        Pattern pat_article_url = Pattern.compile(article_url);
        Matcher m_article_url = pat_article_url.matcher(responseContent);

        while(m_article_url.find()) url_list.add(m_article_url.group(1));

        return url_list;
    }

    public static Article parseArticle(String article_url) throws IOException{
        HttpGet get = new HttpGet(article_url);
        HttpResponse res = client.execute(get);
        String responseContent = null; // 响应内容
        HttpEntity entity = res.getEntity();
        responseContent = EntityUtils.toString(entity, "UTF-8");

        String article_json = responseContent.split("<script type='application/json' data-name='note'>")[1].split("<script type='application/json' data-name='uuid'>")[0].split("</script>")[0].trim();
        JsonObject articleObj = jsonparer.parse(article_json).getAsJsonObject();
        Article a = new Article();
        if(articleObj.has("id")){
            a.setArticle_id(articleObj.get("id").getAsInt());
        }
        if(articleObj.has("slug")){
            a.setArticle_slug(articleObj.get("slug").getAsString());
        }
        if(articleObj.has("views_count")){
            a.setViews_count(articleObj.get("views_count").getAsInt());
        }
        if(articleObj.has("wordage")){
            a.setWordage(articleObj.get("views_count").getAsInt());
        }
        if(articleObj.has("likes_count")){
            a.setLikes_count(articleObj.get("likes_count").getAsInt());
        }
        if(articleObj.has("comments_count")){
            a.setComments_count(articleObj.get("comments_count").getAsInt());
        }
        if(articleObj.has("rewards_total_count")){
            a.setRewards_total_count(articleObj.get("rewards_total_count").getAsInt());
        }

        String author_json = responseContent.split("<script type='application/json' data-name='author'>")[1].split("<script type='application/json' data-name='current-user'>")[0].split("</script>")[0].trim();
        JsonObject authorObj = jsonparer.parse(author_json).getAsJsonObject();
        if(authorObj.has("id")){
            a.setAuthor_id(authorObj.get("id").getAsInt());
        }
        if(authorObj.has("nickname")){
            a.setAuthor_nickname(authorObj.get("nickname").getAsString());
        }
        if(authorObj.has("slug")){
            a.setAuthor_slug(authorObj.get("slug").getAsString());
        }
        if(authorObj.has("public_notes_count")){
            a.setAuthor_public_notes_count(authorObj.get("public_notes_count").getAsInt());
        }
        if(authorObj.has("followers_count")){
            a.setAuthor_followers_count(authorObj.get("followers_count").getAsInt());
        }
        if(authorObj.has("total_likes_count")){
            a.setAuthor_total_likes_count(authorObj.get("total_likes_count").getAsInt());
        }

        String title = responseContent.split("<div class=\"article\">")[1].split("<h1 class=\"title\">")[1].split("</h1>")[0].trim();
        a.setTitle(title);
        String text = responseContent.split("<div class=\"show-content\">")[1].split("<div class=\"visitor_edit\">")[0];
        Pattern pat_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html = pat_html.matcher(text);
        text = m_html.replaceAll("");
        a.setText(text.replaceAll("'",""));

        return a;
    }


    public static void main(String[] args) throws IOException{
        String url = "http://www.jianshu.com/collections/219/notes?order_by=added_at&page=";
        String sql = "";
        for(int i = 5 ; i < 10 ; i++){//页数
            System.out.println("page:" + i);
            List<String> url_list = Spider.getArticleUrl(url + i);
            for(int j = 0 ; j < url_list.size() ; j++){
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Article arti = parseArticle("http://www.jianshu.com" + url_list.get(j));
                System.out.println(arti.getTitle());
                sql = "insert into article (`article_id`, `article_slug`, `title`, `text`, `wordage`, `views_count`, `comments_count`, `likes_count`, `rewards_total_count`, `author_id`, `author_nickname`, `author_slug`, `author_public_notes_count`, `author_followers_count`, `author_total_likes_count`) VALUES ('" + arti.getArticle_id() + "', '" + arti.getArticle_slug() + "', '" + arti.getTitle() + "', '" + arti.getText() + "', '" + arti.getWordage() + "', '" + arti.getViews_count() + "', '" + arti.getComments_count() + "', '" + arti.getLikes_count() + "', '" + arti.getRewards_total_count() + "', '" + arti.getArticle_id() + "', '" + arti.getAuthor_nickname() + "', '" + arti.getArticle_slug() + "', '" + arti.getAuthor_public_notes_count() + "', '" + arti.getAuthor_followers_count() + "', '" + arti.getAuthor_total_likes_count() + "')";
                db.add(sql);
            }
        }
    }
}
