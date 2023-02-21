package com.ablec.module_base.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ablec.module_base.db.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SearchHistoryEntity): Long

    @Query("select * from search_history order by id DESC limit 5")
    suspend fun selectHis(): List<SearchHistoryEntity>

    @Query("delete from search_history")
    suspend fun deleteAll()

    @Query("delete from search_history where content = :kw")
    suspend fun deleteByContent(kw: String)
}