package jianshu.datapreprocess;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by zhaokangpan on 2016/11/26.
 */
public class CalculateSimilarity {

    static Map<String, Vector<Double>> userFeat = new HashMap<String, Vector<Double>>();

    public static double cosSim(Vector<Double> v1, Vector<Double> v2){
        double inner = 0.0;
        double v1square = 0.0;
        double v2square = 0.0;
        for(int i = 0 ; i < v1.size() ; i++){
            inner += v1.get(i) * v2.get(i);
            v1square += Math.pow(v1.get(i), 2);
            v2square += Math.pow(v2.get(i), 2);
        }
        return inner/(Math.sqrt(v1square) * Math.sqrt(v2square));
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("userFeatures1.txt")), "utf-8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("userSimilarityTotal.txt")),"utf-8"));
        String line = "";

        while((line = br.readLine()) != null){
            String[] temp = line.split("\t");
            String userId = temp[0];
            Vector<Double> feat = new Vector<Double>();
            String[] temp1 = temp[1].split(",");
            for(int i = 0 ; i < temp1.length ; i++){
                feat.add(Double.parseDouble(temp1[i]));
            }
            userFeat.put(userId, feat);
        }

        for(Object key : userFeat.keySet()){
            double totalSim = 0.0;
            for(Object key1 : userFeat.keySet()){
                if(!key.equals(key1)){
                    totalSim += cosSim(userFeat.get(key), userFeat.get(key1));
                }
            }
            bw.write(key + "\t" + totalSim + "\n");
        }
        br.close();
        bw.close();
    }
}
