package com.ablec.module_base.db.entity

import androidx.annotation.Keep
import androidx.room.*
import java.util.*

/**
 * Created by zlx on 2020/9/23 10:29
 * Email: 1170762202@qq.com
 * Description: 搜索历史
 */
@Keep
@Entity(tableName = "search_history", indices = [Index(value = ["content"], unique = true)])
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val data:Date? = Date(),
    //扩展字段
    @ColumnInfo(name = "type") val type: Int? = 1,
    @ColumnInfo(name = "content") val content: String?)