package rafalwojcik.prm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
class Product(
    @ColumnInfo(name="filePath") val filePath: String,
    @ColumnInfo(name="productName") var productName: String,
    @ColumnInfo(name="productAddress") var productAddress: String,
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    @ColumnInfo(name="createdDate")
    var createdDate: String = LocalDate.now().toString()
    @ColumnInfo(name="latitude")
    var latitude: Double = 0.0
    @ColumnInfo(name="longitude")
    var longitude: Double = 0.0
}