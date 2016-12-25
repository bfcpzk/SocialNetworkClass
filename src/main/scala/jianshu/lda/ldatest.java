package jianshu.lda;

import java.io.*;
import java.util.Map;



public class ldatest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// 1. Load corpus from disk
		Corpus corpus = Corpus.load_file("author_keyword_game.txt");
		//Corpus corpus = Corpus.load("data/mini");
		// 2. Create a LDA sampler
		LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
		// 3. Train it
		ldaGibbsSampler.gibbs(10);
		// 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
		double[][] phi = ldaGibbsSampler.getPhi();
		Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
		LdaUtil.explain(topicMap);
		double[][] theta = ldaGibbsSampler.getTheta();

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("userFeatures_game.txt")), "utf-8"));

		for(int i = 0 ; i < theta.length ; i++){
			//bw.write(l.get(i) + ",");
			for(int j = 0 ; j < theta[i].length - 1 ; j++){
				bw.write(theta[i][j] + ",");
			}
			bw.write(theta[i][theta[i].length - 1] + "\n");
		}
		bw.close();
	}
}