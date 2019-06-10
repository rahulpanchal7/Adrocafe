package com.adrosonic.adrocafe.adrocafe.data

data class User(
    val id: Int,
    var companyid: Int,
    var isvaliduser: Boolean,
    var usercontactnumber: Long,
    var datemodified: String,
    var roles: String,
    var datecreated: String,
    var username: String,
    var useremail: String,
    var userdesignation: String,
    var jwtToken: String
)