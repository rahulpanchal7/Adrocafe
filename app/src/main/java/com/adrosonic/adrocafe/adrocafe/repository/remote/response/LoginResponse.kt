package com.adrosonic.adrocafe.adrocafe.repository.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("companyid")
    @Expose
    var companyid: Int? = null

    @SerializedName("isvaliduser")
    @Expose
    var isvaliduser: Boolean? = false

    @SerializedName("usercontactnumber")
    @Expose
    var usercontactnumber: Long? = null

    @SerializedName("datemodified")
    @Expose
    var datemodified: String? = null

    @SerializedName("userimage")
    @Expose
    var userimage: Any? = null

    @SerializedName("roles")
    @Expose
    var roles: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("datecreated")
    @Expose
    var datecreated: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("useremail")
    @Expose
    var useremail: String? = null

    @SerializedName("userdesignation")
    @Expose
    var userdesignation: String? = null

    @SerializedName("jwtToken")
    @Expose
    var jwtToken:String? = null
}