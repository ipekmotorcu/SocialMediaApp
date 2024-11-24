package com.cs394.socialmediaapp.model

data class Post (val username:String,
                  val time: Long?=null,
                  val caption: String?="",
                  val likes: Int?= 0,
                  val userid: String,//username yeterli olabilir aslında buna ihtiyaç var mı emin değilm
                  val downloadUrl : Int//post olarak eklediğimiz resim şimdilik drawable'dan alıyoruz bunları
)
