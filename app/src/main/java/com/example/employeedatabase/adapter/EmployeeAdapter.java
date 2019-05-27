package com.example.employeedatabase.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.employeedatabase.R;
import com.example.employeedatabase.models.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> employeeList;
    private EmployeeAdapterCallback listener;

    public interface EmployeeAdapterCallback {
        void onEmployeeClicked(Employee employee);

        void onEmployeeDelete(Employee employee);

        void onEmployeeEdit(Employee employee);
    }

    public EmployeeAdapter(List<Employee> employeeList, EmployeeAdapterCallback listener) {
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Employee employee = employeeList.get(viewHolder.getAdapterPosition());

        viewHolder.name.setText(employee.getName());
        viewHolder.designation.setText(employee.getDesignation());
        viewHolder.field.setText(employee.getField());
        //viewHolder.photo.setImageURI(Uri.parse(employee.getPhoto()));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name, designation, field;
        ImageButton delete, edit;
        LinearLayout mainLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            designation = (TextView) itemView.findViewById(R.id.designation);
            field = (TextView) itemView.findViewById(R.id.field);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            edit = (ImageButton) itemView.findViewById(R.id.edit_btn);
            photo = (ImageView) itemView.findViewById(R.id.imageView);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.on_click);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!employeeList.isEmpty()) {
                        Employee employee = employeeList.get(getAdapterPosition());
                        employeeList.remove(getAdapterPosition());
                        listener.onEmployeeDelete(employee);
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!employeeList.isEmpty()) {
                        listener.onEmployeeEdit(employeeList.get(getAdapterPosition()));
                    }
                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!employeeList.isEmpty()) {
                        listener.onEmployeeClicked(employeeList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
