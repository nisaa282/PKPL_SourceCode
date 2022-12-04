package com.fakhrunnisaa.datamahasiswa

import android.os.Parcelable
import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "studentsTable")
data class Student(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(index = true, name = BaseColumns._ID) var id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ids") val ids: String,
    @ColumnInfo(name = "ipk") val ipk: Float,
): Parcelable