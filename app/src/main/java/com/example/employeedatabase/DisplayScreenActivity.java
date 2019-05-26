package com.example.employeedatabase;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.employeedatabase.models.Employee;

public class DisplayScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        Intent intent = getIntent();
        final Employee employee = (Employee) intent.getSerializableExtra("employee");

        TextView id = (TextView) findViewById(R.id.emp_id);
        id.setText(String.valueOf(employee.getId()));

        TextView name = (TextView) findViewById(R.id.emp_name);
        name.setText(employee.getName());

        TextView designation = (TextView) findViewById(R.id.emp_designation);
        designation.setText(employee.getDesignation());

        TextView field = (TextView) findViewById(R.id.emp_field);
        field.setText(employee.getField());

        TextView email = (TextView) findViewById(R.id.emp_email);
        email.setText(employee.getEmail());

        TextView phone = (TextView) findViewById(R.id.emp_phone);
        phone.setText(String.valueOf(employee.getPhone()));

        TextView salary = (TextView) findViewById(R.id.emp_salary);
        salary.setText(String.valueOf(employee.getSalary()));

        //ImageView photo = (ImageView) findViewById(R.id.image);
        //photo.setImageResource(employee.getPhoto());

        ImageButton emailbtn = (ImageButton) findViewById(R.id.emailbtn);
        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onemail(employee);
            }
        });

        ImageButton messagebtn = (ImageButton) findViewById(R.id.messagebtn);
        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onsms(employee);
            }
        });

        ImageButton phonebtn = (ImageButton) findViewById(R.id.phonebtn);
        phonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onphone();
            }
        });
    }

    protected void onemail(Employee employee) {
        Intent mailintent = new Intent(Intent.ACTION_SENDTO);
        mailintent.setType("text/html");
        mailintent.setData(Uri.parse("mailto: racherlakhila@gmail.com"));
        mailintent.putExtra(Intent.EXTRA_SUBJECT, "New Employee Data: ");
        mailintent.putExtra(Intent.EXTRA_TEXT, "Name: " + employee.getName() + " has joined the " + employee.getField() + " as " + employee.getDesignation() + " with a monthly Salary of " + employee.getSalary() + ". EmailId: " + employee.getEmail() + " and Phone: " + employee.getPhone());
        startActivity(mailintent);
    }

    protected void onsms(Employee employee) {
        Intent smsintent = new Intent(Intent.ACTION_SEND);
        smsintent.setData(Uri.parse("sms:"));
        smsintent.putExtra(Intent.EXTRA_SUBJECT, "New Employee Data: ");
        smsintent.putExtra(Intent.EXTRA_TEXT, "Name: " + employee.getName() + " has joined the " + employee.getField() + " as " + employee.getDesignation() + " with a monthly Salary of " + employee.getSalary() + ". EmailId: " + employee.getEmail() + " and Phone: " + employee.getPhone());
        startActivity(smsintent);
    }

    protected void onphone() {
        Intent phoneintent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(phoneintent);


    }

}
