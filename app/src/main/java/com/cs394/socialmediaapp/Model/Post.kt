package com.cs394.socialmediaapp.Model

data class Post (val username:String,
                  val time: Long?=null,
                  val caption: String?="",
                  val likes: Int?= 0,
                  val userid: String,//username yeterli olabilir aslında buna ihtiyaç var mı emin değilm
                  val downloadUrl : String,//post olarak eklediğimiz resim
)
