package com.adrosonic.adrocafe.adrocafe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
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
) {

    constructor(): this(0, 0, false, 0L,"","","","","","","")

}