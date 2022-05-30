package rafalwojcik.prm.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import rafalwojcik.prm.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}