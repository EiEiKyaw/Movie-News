package com.android.tutorial.couch_potato.model

import com.google.gson.annotations.SerializedName

class Rating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
) {
}

//"Source": "Internet Movie Database",
//"Value": "7.5/10"