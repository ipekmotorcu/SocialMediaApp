package com.cs394.socialmediaapp.Model

import android.net.Uri

data class Post (val username:String?,
                  val time: Long?=null,
                  val caption: String?="",
                  val likes: Int?= 0,
                  val postUri: Uri?=Uri.EMPTY,
                  val userid: String?,//username yeterli olabilir aslında buna ihtiyaç olmayabilir
                  val downloadUrl : Int=0,//post olarak eklediğimiz resim şimdilik drawable'dan alıyoruz bunları
                    //ben bunun String olacağını düşünerek çalıştım :/ -e
)
