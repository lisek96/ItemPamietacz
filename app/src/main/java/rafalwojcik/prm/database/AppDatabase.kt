package rafalwojcik.prm.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.dao.ProductDao
import rafalwojcik.prm.model.Product
import kotlin.reflect.KClass

@Database(entities = [Product::class], version = 1 )
abstract class AppDatabase() : RoomDatabase() {
    abstract fun productDao() : ProductDao
}