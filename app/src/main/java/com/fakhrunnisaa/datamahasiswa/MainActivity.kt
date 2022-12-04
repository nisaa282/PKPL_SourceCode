package com.fakhrunnisaa.datamahasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhrunnisaa.datamahasiswa.data.MainViewModel
import com.fakhrunnisaa.datamahasiswa.data.ViewModelFactory
import com.fakhrunnisaa.datamahasiswa.databinding.ActivityMainBinding
import com.fakhrunnisaa.datamahasiswa.databinding.PopupItemBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StudentRvAdapter
    private var dataStudent = ArrayList<Student>()
    private var currentSortingType = 0

    override fun onResume() {
        super.onResume()

        loadData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        recyclerViewSetup()
        loadData()
        addStudentButton()
    }

    private fun loadData() {
        viewModel.getData().observe(this) { result ->
            if (result.isNotEmpty()) {
                binding.textResult.visibility = View.GONE
                dataStudent = ArrayList(result)
                sortingData()
            } else {
                adapter.setData(ArrayList())
                binding.textResult.visibility = View.VISIBLE
            }
        }
    }

    private fun addStudentButton() {
        binding.btnAddStudent.setOnClickListener {
            startActivity(Intent(this, AddNewStudentActivity::class.java))
        }
    }

    private fun recyclerViewSetup() {
        adapter = StudentRvAdapter()

        adapter.onItemClick = { student ->
            val dialogBinding = PopupItemBinding.inflate(layoutInflater)
            val view = AlertDialog.Builder(this).create()
            view.setView(dialogBinding.root)
            dialogBinding.detail.setOnClickListener {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("EXTRA_ID", student)
                startActivity(intent)
                view.dismiss()
            }
            dialogBinding.delete.setOnClickListener {
                val confirmDialog = AlertDialog.Builder(this)
                confirmDialog.setMessage("Apakah anda ingin menghapus data ${student.name}")
                confirmDialog.setTitle("Peringatan!")
                confirmDialog.setPositiveButton("Yes") { _, _ ->
                    if(student.id != null) {
                        viewModel.deleteData(student.id!!)
                        confirmDialog.create().dismiss()
                        view.dismiss()
                        loadData()
                    }
                    else Toast.makeText(this, "Pilih Data", Toast.LENGTH_SHORT).show()
                }
                confirmDialog.setNegativeButton("No") { _, _ ->
                    confirmDialog.create().dismiss()
                    view.dismiss()
                }
                confirmDialog.show()
            }
            view.show()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ids_asc -> {
                currentSortingType = 1
                sortingData(currentSortingType)
            }
            R.id.ids_dsc -> {
                currentSortingType = 2
                sortingData(currentSortingType)
            }
            R.id.ipk_asc -> {
                currentSortingType = 3
                sortingData(currentSortingType)
            }
            R.id.ipk_dsc -> {
                currentSortingType = 4
                sortingData(currentSortingType)
            }
            R.id.name_asc -> {
                currentSortingType = 5
                sortingData(currentSortingType)
            }
            R.id.name_dsc -> {
                currentSortingType = 6
                sortingData(currentSortingType)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortingData(typeSorting: Int? = null) {
        when (typeSorting) {
            1 -> {
                dataStudent = ArrayList(dataStudent.sortedBy { it.ids })
                adapter.setData(dataStudent)
            }
            2 -> {
                dataStudent = ArrayList(dataStudent.sortedByDescending { it.ids })
                adapter.setData(dataStudent)
            }
            3 -> {
                dataStudent = ArrayList(dataStudent.sortedBy { it.ipk })
                adapter.setData(dataStudent)
            }
            4 -> {
                dataStudent = ArrayList(dataStudent.sortedByDescending { it.ipk })
                adapter.setData(dataStudent)
            }
            5 -> {
                dataStudent = ArrayList(dataStudent.sortedBy { it.name })
                adapter.setData(dataStudent)
            }
            6 -> {
                dataStudent = ArrayList(dataStudent.sortedByDescending { it.name })
                adapter.setData(dataStudent)
            }
            else -> adapter.setData(dataStudent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}