package com.adrosonic.adrocafe.adrocafe.data

data class UserResetModel(
    var useremail: String,
    var oldpassword: String,
    var newpassword: String
)