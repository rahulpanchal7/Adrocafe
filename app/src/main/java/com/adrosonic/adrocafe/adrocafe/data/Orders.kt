package com.adrosonic.adrocafe.adrocafe.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.adrosonic.adrocafe.adrocafe.BR

@Entity
data class Orders(
    @Ignore
    var orderDetails: Array<OrderDetails>?,
    @Ignore
    var useremail: String?,
    @Ignore
    var orderDetailsList: Array<OrderDetailsList>,
    var datemodified: String?,
    var datecreated: String?,
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @Embedded(prefix = "user_")
    var user: User?,
    var status: String?
) : BaseObservable() {

    constructor(id: String, datemodified: String?, datecreated: String?, status: String?): this( arrayOf<OrderDetails>(), "",
        arrayOf<OrderDetailsList>(),datemodified, datecreated, id, User(), status)


    var isProgress: Boolean = false
    @Bindable get() = status == "In Progress"
    set(value) {
        field = value
//        notifyPropertyChanged(BR.progress)   //error
    }



    var newStatus: String
    @Bindable get() = status.let { it } ?: ""
    set(value) {
        status = value
//        notifyPropertyChanged(BR.newStatus)  //error
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Orders

        if (orderDetails != null) {
            if (other.orderDetails == null) return false
            if (!orderDetails!!.contentEquals(other.orderDetails!!)) return false
        } else if (other.orderDetails != null) return false
        if (datemodified != other.datemodified) return false
        if (datecreated != other.datecreated) return false
        if (id != other.id) return false
        if (user != other.user) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = orderDetails?.contentHashCode() ?: 0
        result = 31 * result + (datemodified?.hashCode() ?: 0)
        result = 31 * result + (datecreated?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }

}

