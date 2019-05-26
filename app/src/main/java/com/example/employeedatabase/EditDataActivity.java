package com.example.employeedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.employeedatabase.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EditDataActivity extends AppCompatActivity {
    
    Button modify_btn;
    EditText edit_id, edit_name, edit_designation, edit_field, edit_email, edit_phone, edit_salary;
    String editId, editName, editDesignation, editField, editEmail, editPhone, editSalary;
    EmployeeDatabase db;
    int position;
    String str_position;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<Employee> employeeList = new ArrayList<>();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_name= (EditText) findViewById(R.id.edit_name);
        edit_designation = (EditText) findViewById(R.id.edit_designation);
        edit_field= (EditText) findViewById(R.id.edit_field);
        edit_email= (EditText) findViewById(R.id.edit_email);
        edit_phone= (EditText) findViewById(R.id.edit_phone);
        edit_salary= (EditText) findViewById(R.id.edit_salary);
        modify_btn = findViewById(R.id.modify_btn);
        Bundle bundle = getIntent().getExtras();
        str_position = bundle.getString("position");
        db = new EmployeeDatabase(this);
        employeeList.addAll(db.getAllData());
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee employee = employeeList.get(position);

                employee.setId(Integer.parseInt(edit_id.getText().toString()));
                employee.setName(edit_name.getText().toString());
                employee.setDesignation(edit_designation.getText().toString());
                employee.setField(edit_field.getText().toString());
                employee.setEmail(edit_email.getText().toString());
                employee.setPhone(Integer.parseInt(edit_phone.getText().toString()));
                employee.setSalary(Integer.parseInt(edit_salary.getText().toString()));

                db.updateData(employee);
                MainActivity.notifyAdapter();
                Intent intent = new Intent(EditDataActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
