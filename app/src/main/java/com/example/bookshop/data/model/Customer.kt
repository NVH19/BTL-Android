package com.example.bookshop.data.model
import com.google.gson.annotations.SerializedName
data class Customer(
    @SerializedName("id")
    var customerId: Int? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("new_pass")
    var newPassword: String = "",
    @SerializedName("password_again")
    var passwordAgain: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("mobPhone")
    var mobPhone: String = "",
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = null,
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("status")
    var status: String? = null,
)