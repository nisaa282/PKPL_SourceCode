package com.fakhrunnisaa.datamahasiswa.data

import android.content.Context
import androidx.lifecycle.*
import com.fakhrunnisaa.datamahasiswa.Injection
import com.fakhrunnisaa.datamahasiswa.Student
import kotlinx.coroutines.launch

class MainViewModel(private val repository: IRepository): ViewModel() {
    fun getData(): LiveData<List<Student>> {
        return repository.getData().asLiveData()
    }

    fun insertData(student: Student) {
        viewModelScope.launch {
            repository.insertData(student)
        }
    }

    fun deleteData(id: Long) {
        viewModelScope.launch {
            repository.deleteData(id)
        }
    }
}

class ViewModelFactory private constructor(private val repository: IRepository): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideStoryAppRepository(context))
        }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}