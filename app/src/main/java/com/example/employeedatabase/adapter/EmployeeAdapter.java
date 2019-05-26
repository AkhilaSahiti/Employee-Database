package com.example.employeedatabase.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeedatabase.EditDataActivity;
import com.example.employeedatabase.EmployeeDatabase;
import com.example.employeedatabase.MainActivity;
import com.example.employeedatabase.R;
import com.example.employeedatabase.models.Employee;

import java.util.ArrayList;
import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private Context context;
    private List<Employee> employeeList;
    private EmployeeDatabase employeeDatabase;
    private EmployeeAdapterCallback listener;

    public interface EmployeeAdapterCallback {
        void onEmployeeClicked(Employee employee);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public TextView name, designation, field;
        ImageButton delete, edit;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            designation = (TextView) itemView.findViewById(R.id.designation);
            field = (TextView) itemView.findViewById(R.id.field);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            edit = (ImageButton) itemView.findViewById(R.id.edit_btn);
            // imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.on_click);
        }
    }


    public EmployeeAdapter(Context context, List<Employee> employeeList, EmployeeDatabase employeeDatabase, EmployeeAdapterCallback listener) {
        this.context = context;
        this.employeeList = employeeList;
        this.employeeDatabase = employeeDatabase;
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
        // viewHolder.imageView.setImageResource(employee.getPhoto());
       /*viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(employee);
            }
        });*/

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteNote(viewHolder.getAdapterPosition());
                /*employeeDatabase.delete(employee.getId());
                Employee removedEmployee = employeeList.get(viewHolder.getAdapterPosition());
                employeeList.remove(viewHolder.getAdapterPosition());
                notifyItemRemoved(viewHolder.getAdapterPosition());*/
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EditDataActivity.class);
                intent.putExtra("position", String.valueOf(viewHolder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEmployeeClicked(employeeList.get(viewHolder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    private void deleteNote(int position){
        employeeDatabase.deleteData(employeeList.get(position));
        Employee removedEmployee = employeeList.get(position);
        employeeList.remove(position);
        notifyItemRemoved(position);
    }

   /* private void edit(final Employee employee){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_add_modify_emp_details, null);

        final EditText id = (EditText)subView.findViewById(R.id.enter_id);
        final EditText name = (EditText)subView.findViewById(R.id.enter_name);
        final EditText designation = (EditText)subView.findViewById(R.id.enter_designation);
        final EditText field = (EditText)subView.findViewById(R.id.enter_field);
        final EditText email = (EditText)subView.findViewById(R.id.enter_email);
        final EditText phone = (EditText)subView.findViewById(R.id.enter_phone);
        final EditText salary = (EditText)subView.findViewById(R.id.enter_salary);

        if(employee!= null){
            id.setText(employee.getId());
            name.setText(employee.getName());
            designation.setText(employee.getDesignation());
            field.setText(employee.getField());
            email.setText(employee.getEmail());
            phone.setText(employee.getPhone());
            salary.setText(employee.getSalary());
        }
    }*/


}
