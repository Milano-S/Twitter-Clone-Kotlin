package com.milano.twitterclone.models

import com.google.firebase.firestore.PropertyName


data class Post(
    var user: User? = null,
    @get:PropertyName("description") @set:PropertyName("description") var description: String = "",
    @get:PropertyName("creation_time_ms") @set:PropertyName("creation_time_ms") var creationTimeMs: Long = 0
)