package com.ricardoteixeira.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ricardoteixeira.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

// Provider injects lazily

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback
    @Inject constructor(private val database: Provider<TaskDatabase>,
    @ApplicationScope private val applicationScope: CoroutineScope)
        : RoomDatabase.Callback() {

        //Run once when created the db
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the dhishes", important = true))
                dao.insert(Task("Learn Kotlin"))
                dao.insert(Task("prepare Christmas"))
                dao.insert(Task("Call mom", completed = true))
            }
        }
    }



}