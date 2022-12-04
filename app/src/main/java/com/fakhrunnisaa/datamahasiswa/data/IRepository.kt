package com.fakhrunnisaa.datamahasiswa.data

import com.fakhrunnisaa.datamahasiswa.Student
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getData(): Flow<List<Student>>

    suspend fun insertData(student: Student)

    suspend fun deleteData(id: Long)
}