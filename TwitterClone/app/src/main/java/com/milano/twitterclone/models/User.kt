package com.milano.twitterclone.models

import com.google.firebase.firestore.PropertyName

data class User(
    @get:PropertyName("username") @set:PropertyName("username") var username: String = ""
   //var age : Int = 0
)