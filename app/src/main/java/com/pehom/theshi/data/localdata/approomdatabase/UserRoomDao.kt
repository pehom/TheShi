package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.*

@Dao
interface UserRoomDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserRoomItem(user: UserRoomItem)

    @Query("SELECT * FROM users_table WHERE userFsId =:userFsId")
    suspend fun getUserRoomItemByFsId(userFsId: String): UserRoomItem?

    @Query("SELECT * FROM users_table WHERE email =:email AND password =:password")
    suspend fun getUserRoomItemByEmailAndPassword(email: String, password: String): UserRoomItem?

    @Update
    suspend fun updateUserRoomItem(userRoomItem: UserRoomItem)

    @Delete
    suspend fun deleteUserRoomItem(userRoomItem: UserRoomItem)
}