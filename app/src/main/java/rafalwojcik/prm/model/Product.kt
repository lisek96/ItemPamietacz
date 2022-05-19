package rafalwojcik.prm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name="filePath") val filePath: String,
    @ColumnInfo(name="productName") val productName: String,
    @ColumnInfo(name=)


){

}