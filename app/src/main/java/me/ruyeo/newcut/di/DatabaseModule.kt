package me.ruyeo.newcut.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.ruyeo.newcut.data.local.BookingDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): BookingDatabase =
        Room.databaseBuilder(context, BookingDatabase::class.java, "booking").build()

    @Provides
    @Singleton
    fun getBasketDao(database: BookingDatabase) = database.getBookingDao()
}