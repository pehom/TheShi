package com.pehom.theshi.data.localdata.approomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.MapConverter


@Database(entities = [
    WordbookRoomItem::class,
    TaskRoomItem::class,
    AvailableVocabularyRoomItem::class,
    StudentRoomItem::class,
    AvailableWordsRoomItem::class,
    MentorRoomItem::class,
    UserRoomItem::class
                     ], version = 27)
@TypeConverters(MapConverter::class)
abstract class AppRoomDatabase: RoomDatabase() {

    abstract fun getWordbookDao(): WordbookDao
    abstract fun getTaskRoomDao(): TaskRoomDao
    abstract fun getVocabularyRoomDao(): AvailableVocabularyRoomDao
    abstract fun getStudentDao(): StudentDao
    abstract fun getAvailableWordsRoomDao(): AvailableWordsRoomDao
    abstract fun getMentorRoomDao(): MentorRoomDao
    abstract fun getUserRoomDao(): UserRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {

            return if (INSTANCE == null ) {
               INSTANCE =  Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    Constants.THE_SHI_ROOM_DATABASE
                ).fallbackToDestructiveMigration()  // attention to migration type
                   .build()
                INSTANCE as AppRoomDatabase

            } else INSTANCE as AppRoomDatabase
        }
    }
}