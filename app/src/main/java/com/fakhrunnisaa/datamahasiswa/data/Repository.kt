package com.fakhrunnisaa.datamahasiswa.data

import androidx.lifecycle.LiveData
import com.fakhrunnisaa.datamahasiswa.Student
import com.fakhrunnisaa.datamahasiswa.room.StudentDao
import com.fakhrunnisaa.datamahasiswa.room.StudentDatabase
import kotlinx.coroutines.flow.Flow

class Repository(private val database: StudentDatabase): IRepository {
    override fun getData(): Flow<List<Student>> {
        return database.studentDao().getAllStudent()
    }

    override suspend fun insertData(student: Student) {
        database.studentDao().insertStudent(student)
    }

    override suspend fun deleteData(id: Long) {
        database.studentDao().delete(id)
    }
}