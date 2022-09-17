package `in`.kay.furture.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FurnitureModel(
    var name: String? = null,
    val drawable: Int = 0,
    val link: String? = null,
    val price : Int? = 0,
    val description : String ? = "",
    val type:String? = ""
) : Parcelable
