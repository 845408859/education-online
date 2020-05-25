package com.atguigu.member.controller

import com.atguigu.member.bean.{DwsMember, DwsMember_Result}
import com.atguigu.member.service.DwsMemberService
import com.atguigu.util.HiveUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DwsMemberController {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "atguigu") //定义一个系统属性，系统属性的名称，系统属性的值
    val sparkConf = new SparkConf().setAppName("dws_member_import").setMaster("local[*]")
//      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//      .registerKryoClasses(Array(classOf[DwsMember]))
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext
    HiveUtil.openDynamicPartition(sparkSession) //开启动态分区
    HiveUtil.openCompression(sparkSession) //开启压缩
    HiveUtil.useSnappyCompression(sparkSession) //使用snappy压缩
    //    DwsMemberService.importMember(sparkSession, "20190722") //根据用户信息聚合用户表数据
    DwsMemberService.importMemberUseApi(sparkSession, "20190722")
  }
}
