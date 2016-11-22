package data_analysis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by zhaokangpan on 16/10/25.
  */
object SimpleDataProcess {

  def main(args : Array[String]): Unit ={
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    //设置运行环境
    val conf = new SparkConf().setAppName("SimpleDataProcess").setMaster("local")
    val sc = new SparkContext(conf)

    val closeness = sc.textFile("facebook/closeness.txt").map( l => {
      val p = l.split(" ")
      (p(0).toLong, p(1).toDouble)
    })
    println(closeness.map(l=>l._2).stats())
    //println(closeness.map(l => l._2).max)
    //closeness.collect.foreach(println)

    /*val result = closeness.map( l => {
      var p = l._2
      val step = 5
      ((p/step + 1).toInt, 1)
    }).reduceByKey(_+_)

    val index = sc.parallelize((1 until 36)).map(l => (l, 0))

    val fin_res = index.leftOuterJoin(result).map( l => (l._1, l._2._2.getOrElse(0)))

    fin_res.sortBy(_._1).map(l => l._1 + "\t" + l._2).repartition(1).saveAsTextFile("close_dist_0")*/
  }
}
