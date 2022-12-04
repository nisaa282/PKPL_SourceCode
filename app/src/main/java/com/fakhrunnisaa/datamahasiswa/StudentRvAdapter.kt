package com.fakhrunnisaa.datamahasiswa

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fakhrunnisaa.datamahasiswa.databinding.ItemStudentBinding

class StudentRvAdapter: RecyclerView.Adapter<StudentRvAdapter.ViewHolder>() {
    private val data = ArrayList<Student>()
    var onItemClick: ((Student) -> Unit)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemStudentBinding.bind(view)
        fun bind(student: Student) {
            binding.name.text = String.format("Nama : ${student.name}")
            binding.idStudent.text = String.format("NIM : ${student.ids}")
            binding.ipk.text = String.format("IPK : ${student.ipk}")
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<Student>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}