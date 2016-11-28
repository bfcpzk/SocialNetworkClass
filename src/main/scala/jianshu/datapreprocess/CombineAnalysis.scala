package jianshu.datapreprocess

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by zhaokangpan on 2016/11/28.
  */
object CombineAnalysis {


  def main(args : Array[String]): Unit ={
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //设置运行环境
    val conf = new SparkConf().setAppName("CombineAnalysis").setMaster("local")
    val sc = new SparkContext(conf)

    val userIntensity = sc.textFile("userSimilarityTotal.txt").map(l => {
      val p = l.split("\t")
      (p(0), p(1).toDouble)
    })

    val userParam = sc.textFile("userIndex.txt").map(l => {
      val p = l.split("\t")
      val param = new Parameter
      val author_id = p(0)
      param.viewCount = p(1).toDouble
      param.commentCount = p(2).toDouble
      param.likesCount = p(3).toDouble
      param.rewardsCount = p(4).toDouble
      param.publicNote = p(5).toDouble
      param.followerCount = p(6).toDouble
      param.totalLikeCount = p(7).toDouble
      (author_id, param)
    })

    userIntensity.leftOuterJoin(userParam).map(l => (l._1, (l._2._1, l._2._2.getOrElse(new Parameter))))
      .map(l => l._1 + "\t" + l._2._1 + "\t" + l._2._2.viewCount).repartition(1).saveAsTextFile("viewCount")
  }
}
