package com.theappexperts.supergit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.sql.Timestamp

data class Commit(
    val timestamp: Long,
    var isHeader: Boolean = false,
    val repoId: Long = -1,
    val authorName: String = "",
    val message: String = ""
)