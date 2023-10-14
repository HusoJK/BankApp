package com.olmez.bankapp.model

/**
 * Şubelerin modellendiği ve Database tablosunun oluşturulduğu class
 */

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Branch(
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "dc_SEHIR")
    @SerializedName("dc_SEHIR")
    val city: String,

    @ColumnInfo(name = "dc_ILCE")
    @SerializedName("dc_ILCE")
    val district: String,

    @ColumnInfo(name = "dc_BANKA_SUBE")
    @SerializedName("dc_BANKA_SUBE")
    val bankBranch: String,

    @ColumnInfo(name = "dc_BANKA_TIPI")
    @SerializedName("dc_BANKA_TIPI")
    val bankType: String,

    @ColumnInfo(name = "dc_BANK_KODU")
    @SerializedName("dc_BANK_KODU")
    val bankCode: String,

    @ColumnInfo(name = "dc_ADRES_ADI")
    @SerializedName("dc_ADRES_ADI")
    val addressName: String,

    @ColumnInfo(name = "dc_ADRES")
    @SerializedName("dc_ADRES")
    val address: String,

    @ColumnInfo(name = "dc_POSTA_KODU")
    @SerializedName("dc_POSTA_KODU")
    val postCode: String,

    @ColumnInfo(name = "dc_ON_OFF_LINE")
    @SerializedName("dc_ON_OFF_LINE")
    val onOffLine: String,

    @ColumnInfo(name = "dc_ON_OFF_SITE")
    @SerializedName("dc_ON_OFF_SITE")
    val onOffSite: String,

    @ColumnInfo(name = "dc_BOLGE_KOORDINATORLUGU")
    @SerializedName("dc_BOLGE_KOORDINATORLUGU")
    val regionalCoordinator: String,

    @ColumnInfo(name = "dc_EN_YAKIM_ATM")
    @SerializedName("dc_EN_YAKIM_ATM")
    val nearestAtm: String
){

}
