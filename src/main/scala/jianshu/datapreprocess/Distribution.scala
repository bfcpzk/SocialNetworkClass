package jianshu.datapreprocess

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by zhaokangpan on 2016/12/24.
  */
object Distribution {

  def main(args : Array[String]): Unit ={
    val conf = new SparkConf().setAppName("Distribution").setMaster("local")
    val sc = new SparkContext(conf)

    val thre_closeness = 988
    val thre_index = 393

    val path = "viewCount_game"
    val buffer = new Array[(Int, Double, Int, Double)](140)
    val file = sc.textFile(path).map(l => {
      val p = l.split("\t")
      (p(1).toDouble, p(2).toDouble)
    }).filter(l => l._1 < thre_closeness && l._2 < thre_index).collect()

    for(i <- 0 until buffer.length){
      buffer(i) = (i * 50, 0.0, 0, 0.0)
    }

    for(t <- file){
      val a = t._1
      val b = t._2
      val index = (a/50).toInt
      buffer(index) = (buffer(index)._1, buffer(index)._2 + b, buffer(index)._3 + 1, 0.0)
    }

    for(i <- 0 until buffer.length){
      val t = buffer(i)
      if(t._3 > 0){
        val avg = t._2/t._3
        buffer(i) = (t._1, t._2, t._3, avg)
      }
      println(i + "   " + buffer(i))
    }

    sc.parallelize(buffer).map(l => {
      val res = l._1 + "\t" + l._2 + "\t" + l._3 + "\t" + l._4
      res
    }).repartition(1).saveAsTextFile("hist_game")

  }
}