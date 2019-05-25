package com.example.employeedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.employeedatabase.models.Employee;

public class addModifyEmpDetails extends AppCompatActivity {

    Button save_btn;
    EditText id, name, designation, field, email, phone, salary;
    String addId, addName, addDesignation, addField, addEmail, addPhone, addSalary;
    EmployeeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*int addId = 0;
        int addPhone = 0;
        int addSalary = 0;*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_emp_details);


        id = (EditText) findViewById(R.id.enter_the_id);
        name = (EditText) findViewById(R.id.enter_the_name);
        designation = (EditText) findViewById(R.id.enter_the_designation);
        field = (EditText) findViewById(R.id.enter_the_field);
        email = (EditText) findViewById(R.id.enter_the_email);
        phone = (EditText) findViewById(R.id.enter_the_phone);
        salary = (EditText) findViewById(R.id.enter_the_salary);
        save_btn = findViewById(R.id.save_btn);
        db = new EmployeeDatabase(this);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addId = id.getText().toString();
                addName = name.getText().toString();
                addDesignation = designation.getText().toString();
                addField = field.getText().toString();
                addEmail = email.getText().toString();
                addPhone = phone.getText().toString();
                addSalary = salary.getText().toString();

                long id = db.addData(Integer.parseInt(addId), addName, addDesignation, addField, addEmail, Integer.parseInt(addPhone), Integer.parseInt(addSalary));
                Intent intent = new Intent(addModifyEmpDetails.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
       /* if (empId.isEmpty()) {
            empId = "0";
        }
        if (empPhone.isEmpty()) {
            empPhone = "0";
        }
        if (empSalary.isEmpty()) {
            empSalary = "0";
        }

        addId = Integer.parseInt(empId);
        addPhone = Integer.parseInt(empPhone);
        addSalary = Integer.parseInt(empSalary);

        if (TextUtils.isEmpty(addName)) {
            Toast.makeText(getApplicationContext(), "Fill all the details...", Toast.LENGTH_SHORT).show();
            Employee employee = new Employee(addName, addDesignation, addField, addEmail); //, empPhone, empSalary);
            EmployeeDatabase employeeDatabase = new EmployeeDatabase(this);
            boolean result = employeeDatabase.add(addName, addDesignation, addField, addEmail);
            if (result == true){
                Toast.makeText(addModifyEmpDetails.this,"Entered", Toast.LENGTH_SHORT  ).show();
            } else{
                Toast.makeText(addModifyEmpDetails.this,"Not Entered", Toast.LENGTH_SHORT  ).show();
            }
        //}
            //id.setText("");
            name.setText("");
            designation.setText("");
            field.setText("");
            email.setText("");
            //phone.setText("");
            //salary.setText("");
            save_btn = (Button) findViewById(R.id.save_btn);
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });


        }


    }*/
