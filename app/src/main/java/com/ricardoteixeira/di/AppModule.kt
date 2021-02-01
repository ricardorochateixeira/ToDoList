package com.ricardoteixeira.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ricardoteixeira.data.TaskDao
import com.ricardoteixeira.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //Module used in applicationcomponent
object AppModule {

    @Provides //used in classes that we cannot change. otherwise we could use inject constructor
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: TaskDatabase.Callback) = Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()


    @Provides
    //Room guarantees that Dao is a singleton
    fun provideTaskDao(database: TaskDatabase) = database.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob()) // Coroutine scope lives as long as the app lives. Supervisor tells to not kill all the childs when one fails
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope