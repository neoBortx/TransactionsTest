package com.bortxapps.infrastructure.database.di

import android.content.Context
import androidx.room.Room
import com.bortxapps.infrastructure.database.dao.TransactionsDao
import com.bortxapps.infrastructure.database.database.TransactionsDataBase
import com.bortxapps.infrastructure.database.database.TransactionsDataBase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            TransactionsDataBase::class.java,
            DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideElectionDao(appDatabase: TransactionsDataBase): TransactionsDao {
        return appDatabase.transactionsDao()
    }
}