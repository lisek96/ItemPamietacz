package rafalwojcik.prm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name="filePath") val filePath: String,
    @ColumnInfo(name="productName") val productName: String,
    @ColumnInfo(name="latitude") val latitude: Double,
    @ColumnInfo(name="longitude") val longitude: Double,
    @ColumnInfo(name="city") val city: String,
    @ColumnInfo(name="street") val street: String,
    @ColumnInfo(name="streetNumber") val streetNumber: String
){

}