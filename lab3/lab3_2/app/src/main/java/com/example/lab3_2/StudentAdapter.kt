package com.example.lab3_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val students: List<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var onItemClickListener: ((Student) -> Unit)? = null

    fun setOnItemClickListener(listener: (Student) -> Unit) {
        onItemClickListener = listener
    }

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvMssv: TextView = view.findViewById(R.id.tvMssv)
        val tvLop: TextView = view.findViewById(R.id.tvLop)
        val tvDiem: TextView = view.findViewById(R.id.tvDiem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvMssv.text = "MSSV: ${student.mssv}"
        holder.tvLop.text = "Lớp: ${student.lop}"
        holder.tvDiem.text = String.format("%.1f", student.diem)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(student)
        }
    }

    override fun getItemCount(): Int = students.size
}
