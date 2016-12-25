package jianshu.datapreprocess;

import com.hankcs.hanlp.HanLP;
import util.Jdbc_Util;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaokangpan on 2016/11/26.
 */
public class KeyWordExtract {

    //工具类
    static Jdbc_Util db = new Jdbc_Util();

    static Map<String, String> author_article = new HashMap<String, String>();

    public static void main(String[] args) throws SQLException, IOException{
        String sql = "select author_id, text from article where current_group='电竞·游戏'";
        String line = "";
        ResultSet rs;
        rs = db.select(sql);

        while(rs.next()){
            String author_id = rs.getString("author_id");
            String text = rs.getString("text");
            if(author_article.containsKey(author_id)){
                String origin_text = author_article.get(author_id);
                origin_text += text.toLowerCase().replace("\t|\n|\r ","");
                author_article.put(author_id, origin_text);
            }else{
                author_article.put(author_id, text.toLowerCase().replace("\t|\n|\r ", ""));
            }
        }
        int count = 0;
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("author_keyword_game.txt")), "utf-8"));
        for(Object key : author_article.keySet()){
            count++;
            String text = author_article.get(key);
            List<String> extractResult = HanLP.extractKeyword(text, 1000);
            text = key.toString() + "\t";
            for(int i = 0 ; i < extractResult.size() ; i++){
                text += extractResult.get(i) + ",";
            }
            bw.write(text + "\n");
            System.out.println(count);
            //System.out.println(extractResult.toString());
        }
        bw.close();
    }
}
