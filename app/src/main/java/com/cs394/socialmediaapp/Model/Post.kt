package com.cs394.socialmediaapp.Model

data class Post (val username:String,
                  val time: Long?=null,
                  val caption: String?="",
                  val likes: Int?= 0,
                  val userid: String,//username yeterli olabilir aslında buna ihtiyaç olmayabilir
                  val downloadUrl : Int//post olarak eklediğimiz resim şimdilik drawable'dan alıyoruz bunları
                    //ben bunun String olacağını düşünerek çalıştım :/ -e
)
