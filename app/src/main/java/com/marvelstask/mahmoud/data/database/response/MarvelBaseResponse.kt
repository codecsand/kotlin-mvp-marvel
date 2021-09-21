package com.marvelstask.mahmoud.data.database.response

class MarvelBaseResponse<T>(
    var code: Int,
    var status: String,
    var data: T?
)