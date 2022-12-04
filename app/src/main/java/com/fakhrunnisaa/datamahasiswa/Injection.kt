package com.fakhrunnisaa.datamahasiswa

import android.content.Context
import com.fakhrunnisaa.datamahasiswa.data.Repository
import com.fakhrunnisaa.datamahasiswa.room.StudentDatabase

object Injection {
    fun provideStoryAppRepository(context: Context): Repository {
        val database = StudentDatabase.getInstance(context)
        return Repository(database!!)
    }
}