package jianshu.datapreprocess;

import util.Jdbc_Util;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaokangpan on 2016/11/28.
 */

class Parameter implements Serializable{
    public double viewCount = 0.0;
    public double commentCount = 0.0;
    public double likesCount = 0.0;
    public double rewardsCount = 0.0;
    public double publicNote = 0.0;
    public double followerCount = 0.0;
    public double totalLikeCount = 0.0;
    public int count = 0;
}

public class CalculateWeightIndex {

    //工具类
    static Jdbc_Util db = new Jdbc_Util();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //全局量
    static Map<String, Parameter> userMap = new HashMap<String, Parameter>();
    static String targetTime = "2016-11-25 00:00:00";



    public static void main(String[] args) throws SQLException, ParseException, IOException{
        String sql = "select author_id, views_count, comments_count, likes_count, rewards_total_count, author_public_notes_count, author_followers_count, author_total_likes_count, article_time from article";
        ResultSet rs = db.select(sql);

        while (rs.next()){
            String author_id = rs.getString("author_id");
            String views_count = rs.getString("views_count");
            String comments_count = rs.getString("comments_count");
            String likes_count = rs.getString("likes_count");
            String rewards_total_count = rs.getString("rewards_total_count");
            String author_public_notes_count = rs.getString("author_public_notes_count");
            String author_followers_count = rs.getString("author_followers_count");
            String author_total_likes_count = rs.getString("author_total_likes_count");
            String article_time = rs.getString("article_time") + "0:00";

            //double timePeriod = (sdf.parse(targetTime).getTime() - sdf.parse(article_time).getTime())/86400000;
            double timePeriod = 1.0;

            Parameter p = new Parameter();
            p.viewCount = Integer.parseInt(views_count) * 1.0/timePeriod;
            p.commentCount = Integer.parseInt(comments_count) * 1.0/timePeriod;
            p.likesCount = Integer.parseInt(likes_count) * 1.0/timePeriod;
            p.rewardsCount = Integer.parseInt(rewards_total_count) * 1.0/timePeriod;
            p.publicNote = Integer.parseInt(author_public_notes_count) * 1.0/timePeriod;
            p.followerCount = Integer.parseInt(author_followers_count) * 1.0/timePeriod;
            p.totalLikeCount = Integer.parseInt(author_total_likes_count) * 1.0/timePeriod;
            p.count = 1;

            if(userMap.containsKey(author_id)){
                Parameter pTemp = userMap.get(author_id);
                pTemp.viewCount += p.viewCount;
                pTemp.commentCount += p.commentCount;
                pTemp.likesCount += p.rewardsCount;
                pTemp.rewardsCount += p.rewardsCount;
                pTemp.publicNote += p.publicNote;
                pTemp.followerCount += p.followerCount;
                pTemp.totalLikeCount += p.totalLikeCount;
                pTemp.count += p.count;
                userMap.put(author_id, pTemp);
            }else{
                userMap.put(author_id, p);
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("userIndex.txt")), "utf-8"));

        for(Object key : userMap.keySet()){
            Parameter p = userMap.get(key);
            p.viewCount /= p.count;
            p.commentCount /= p.count;
            p.likesCount /= p.count;
            p.rewardsCount /= p.count;
            p.publicNote /= p.count;
            p.followerCount /= p.count;
            p.totalLikeCount /= p.count;
            String output = key.toString() + "\t" + p.viewCount + "\t" + p.commentCount + "\t" + p.likesCount + "\t" + p.rewardsCount + "\t" + p.publicNote + "\t" + p.followerCount + "\t" + p.totalLikeCount + "\n";
            bw.write(output);
        }
        bw.close();
    }
}
