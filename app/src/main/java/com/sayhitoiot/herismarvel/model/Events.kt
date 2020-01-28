package com.sayhitoiot.marvelretrofit.model

data class Events(
    val available: String,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: String
)