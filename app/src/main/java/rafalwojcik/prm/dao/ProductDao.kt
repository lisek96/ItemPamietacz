package rafalwojcik.prm.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rafalwojcik.prm.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)
}