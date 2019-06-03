package com.example.employeedatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeedatabase.models.Employee;
import com.example.employeedatabase.storage.ImageStorageManager;

public class DisplayEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        Intent intent = getIntent();
        final Employee employee = (Employee) intent.getSerializableExtra("employee");

        TextView id = findViewById(R.id.emp_id);
        id.setText(String.valueOf(employee.getId()));

        TextView name = findViewById(R.id.emp_name);
        name.setText(employee.getName());

        TextView designation = findViewById(R.id.emp_designation);
        designation.setText(employee.getDesignation());

        TextView field = findViewById(R.id.emp_field);
        field.setText(employee.getField());

        TextView email = findViewById(R.id.emp_email);
        email.setText(employee.getEmail());

        final TextView phone = findViewById(R.id.emp_phone);
        phone.setText(String.valueOf(employee.getPhone()));

        TextView salary = findViewById(R.id.emp_salary);
        salary.setText(String.valueOf(employee.getSalary()));

        final ImageView photo = findViewById(R.id.image);
        ImageStorageManager.getInstance().getPhoto(DisplayEmployeeActivity.this, employee.getPhoto(), new ImageStorageManager.ImageStorageCallback() {
            @Override
            public void onPhotoSaved(String fileName) {

            }

            @Override
            public void onPhotoGenerated(final Bitmap bitmap) {
                photo.setImageBitmap(bitmap);
            }
        });

        ImageButton emailButton = findViewById(R.id.emailbtn);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmail(employee);
            }
        });

        ImageButton messageButton = findViewById(R.id.messagebtn);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSms(employee);
            }
        });

        ImageButton phoneButton = findViewById(R.id.phonebtn);
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhone();
            }
        });
    }

    protected void onEmail(Employee employee) {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setData(Uri.parse("mailto: " + employee.getEmail()));
        mailIntent.setType("text/html");
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Employee Data: ");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + employee.getName() + " has joined the " + employee.getField() + " as " + employee.getDesignation() + " with a monthly Salary of " + employee.getSalary() + ". EmailId: " + employee.getEmail() + " and Phone: " + employee.getPhone());
        startActivity(mailIntent);
    }

    protected void onSms(Employee employee) {
        Intent smsIntent = new Intent(Intent.ACTION_SEND);
        smsIntent.setData(Uri.parse("sms:"));
        smsIntent.putExtra(Intent.EXTRA_SUBJECT, "New Employee Data: ");
        smsIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + employee.getName() + " has joined the " + employee.getField() + " as " + employee.getDesignation() + " with a monthly Salary of " + employee.getSalary() + ". EmailId: " + employee.getEmail() + " and Phone: " + employee.getPhone());
        startActivity(smsIntent);
    }

    protected void onPhone() {
        Intent phoneIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(phoneIntent);


    }

}
