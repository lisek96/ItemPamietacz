package rafalwojcik.prm.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import rafalwojcik.prm.activity.MainActivity

class DatabaseGiver() {

    companion object {
        private var db: AppDatabase? = null

        fun getDb(context : Context): AppDatabase {
            db = db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java, "products-database"
            ).build()
            return db!!
        }

    }
}
