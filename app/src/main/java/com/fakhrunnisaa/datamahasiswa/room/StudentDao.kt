package com.fakhrunnisaa.datamahasiswa.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhrunnisaa.datamahasiswa.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM studentsTable")
    fun getAllStudent(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Query("DELETE FROM studentsTable WHERE _id = :id")
    suspend fun delete(id: Long)
}