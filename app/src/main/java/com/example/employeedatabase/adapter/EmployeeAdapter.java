package com.example.employeedatabase.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
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
import com.example.employeedatabase.storage.ImageStorageManager;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> employees;
    private EmployeeAdapterCallback listener;

    public void addAll(List<Employee> employees) {
        if (employees != null) {
            if (this.employees != null && !this.employees.isEmpty()) {
                this.employees.clear();
            }
            this.employees = employees;
            notifyDataSetChanged();
        }
    }

    public interface EmployeeAdapterCallback {
        void onEmployeeClicked(Employee employee);

        void onEmployeeDelete(Employee employee);

        void onEmployeeEdit(Employee employee);
    }

    public EmployeeAdapter(EmployeeAdapterCallback listener) {
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
        Employee employee = employees.get(viewHolder.getAdapterPosition());
        viewHolder.name.setText(employee.getName());
        viewHolder.designation.setText(employee.getDesignation());
        viewHolder.field.setText(employee.getField());
        ImageStorageManager.getInstance().getPhoto(viewHolder.itemView.getContext(), employee.getPhoto(), new ImageStorageManager.ImageStorageCallback() {
            @Override
            public void onPhotoSaved(String fileName) {
                //ToDo: Nothing
            }

            @Override
            public void onPhotoGenerated(final Bitmap bitmap) {
                if (bitmap != null) {
                    viewHolder.photo.setImageBitmap(bitmap);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name, designation, field;
        ImageButton delete, edit;
        LinearLayout mainLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            designation = itemView.findViewById(R.id.designation);
            field = itemView.findViewById(R.id.field);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit_btn);
            photo = itemView.findViewById(R.id.imageView);
            mainLayout = itemView.findViewById(R.id.on_click);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!employees.isEmpty()) {
                        Employee employee = employees.get(getAdapterPosition());
                        employees.remove(getAdapterPosition());
                        listener.onEmployeeDelete(employee);
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!employees.isEmpty()) {
                        listener.onEmployeeEdit(employees.get(getAdapterPosition()));
                    }
                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!employees.isEmpty()) {
                        listener.onEmployeeClicked(employees.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
