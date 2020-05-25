package com.atguigu.member.controller

import com.atguigu.member.bean.QueryResult
import com.atguigu.member.service.AdsMemberService
import com.atguigu.util.HiveUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object AdsMemberController {
  def main(args: Array[String]): Unit = {
//    System.setProperty("HADOOP_USER_NAME", "atguigu")
    val sparkConf = new SparkConf().setAppName("ads_member_controller")//.setMaster("local[*]")
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext
    HiveUtil.openDynamicPartition(sparkSession) //开启动态分区
    AdsMemberService.queryDetailApi(sparkSession, "20190722")
    AdsMemberService.queryDetailSql(sparkSession, "20190722")
  }
}
