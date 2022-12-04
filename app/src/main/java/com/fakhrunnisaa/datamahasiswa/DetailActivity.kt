package com.fakhrunnisaa.datamahasiswa

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.fakhrunnisaa.datamahasiswa.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListener()
    }

    private fun initListener() {
        initData()
    }

    private fun initData() {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_ID", Student::class.java)
        } else {
            intent.getParcelableExtra("EXTRA_ID")
        }
        if(data != null) {
            binding.name.text = String.format("NAME : ${data.name}")
            binding.ids.text = String.format("NIM : ${data.ids}")
            binding.ipk.text = String.format("IPK : ${data.ipk}")
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