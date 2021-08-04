package com.example.news_demo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remoteKeys WHERE  newsTitle= :title")
    suspend fun remoteKeysRepoId(title:String): RemoteKey?

    @Query("DELETE FROM remoteKeys")
    suspend fun clearRemoteKeys()

}