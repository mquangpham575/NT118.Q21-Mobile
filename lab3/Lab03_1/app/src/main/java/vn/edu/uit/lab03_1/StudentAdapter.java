package vn.edu.uit.lab03_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private final OnStudentClickListener clickListener;

    public interface OnStudentClickListener {
        void onItemClick(Student student);
        void onItemLongClick(Student student);
    }

    public StudentAdapter(List<Student> studentList, OnStudentClickListener clickListener) {
        this.studentList = studentList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Student> newList) {
        this.studentList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bind(student, clickListener);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvInfo;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_student_name);
            tvInfo = itemView.findViewById(R.id.tv_student_info);
        }

        public void bind(final Student student, final OnStudentClickListener listener) {
            tvName.setText(student.getName());
            String infoText = "MSSV: " + student.getStudentId() + " | Lớp: " + student.getClassName();
            tvInfo.setText(infoText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(student);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemLongClick(student);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
