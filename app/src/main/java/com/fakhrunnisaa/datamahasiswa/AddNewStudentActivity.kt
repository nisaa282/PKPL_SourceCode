package com.fakhrunnisaa.datamahasiswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.fakhrunnisaa.datamahasiswa.data.MainViewModel
import com.fakhrunnisaa.datamahasiswa.data.ViewModelFactory
import com.fakhrunnisaa.datamahasiswa.databinding.ActivityAddNewStudentBinding
import com.fakhrunnisaa.datamahasiswa.room.StudentDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNewStudentActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var _binding: ActivityAddNewStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNewStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        addStudentButton()
        backButton()
    }

    private fun backButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addStudentButton() {
        binding.btnAddStudent.setOnClickListener {
            val name = binding.inputName.text.toString().trim()
            val ids = binding.inputNim.text.toString().trim()
            val ipk = binding.inputIpk.text.toString().trim()
            if (name.isEmpty()) {
                binding.inputName.error = "Masukkan Nama"
            } else if (ids.isEmpty()) {
                binding.inputNim.error = "Masukkan NIM"
            } else if (ipk.isEmpty()) {
                binding.inputIpk.error = "Masukkan IPK"
            } else if (ipk.toDouble() !in 0.0..4.0)
                binding.inputIpk.error = "IPK hanya 0 - 4"
            else {
                val ipkValues = ipk.toFloat()
                viewModel.insertData(Student(name = name, ids = ids, ipk = ipkValues))
                Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}