package com.example.employeedatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeedatabase.models.Employee;
import com.example.employeedatabase.storage.EmployeeDatabase;
import com.example.employeedatabase.storage.ImageStorageManager;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddEmployeeDetailsActivity extends AppCompatActivity {
    private double amount;
    private int PICK_IMAGE = 1;
    private TextView idTextView;
    private Button saveButton, addPhotoButton;
    private EditText name, email, phone, salary;
    private String addId, addName, addDesignation, addField, addEmail, addPhone, addSalary, currency;
    private Spinner designation, field;
    private ImageView imageView;
    private EmployeeDatabase employeeDatabase;
    private Bitmap selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_emp_details);
        employeeDatabase = new EmployeeDatabase(this);

        idTextView = findViewById(R.id.idTextView);
        name = findViewById(R.id.enter_the_name);
        designation = findViewById(R.id.enter_the_designation);
        field = findViewById(R.id.enter_the_field);
        email = findViewById(R.id.enter_the_email);
        phone = findViewById(R.id.enter_the_phone);
        salary = findViewById(R.id.enter_the_salary);
        imageView = findViewById(R.id.image_display);
        saveButton = findViewById(R.id.save_btn);
        addPhotoButton = findViewById(R.id.image_btn);

        final Intent intent = getIntent();
        final Employee employee = (Employee) intent.getSerializableExtra("employee");

        if (employee != null) {
            addId = employee.getId();
            idTextView.setText(employee.getId());
            name.setText(employee.getName());
            ArrayAdapter myAdap1 = (ArrayAdapter) designation.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition1 = myAdap1.getPosition(employee.getDesignation());
            designation.setSelection(spinnerPosition1);
            ArrayAdapter myAdap2 = (ArrayAdapter) field.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition2 = myAdap2.getPosition(employee.getField());
            field.setSelection(spinnerPosition2);
            email.setText(employee.getEmail());
            phone.setText(String.valueOf(employee.getPhone()));
            salary.setText(String.valueOf(employee.getSalary()));
            ImageStorageManager.getInstance().getPhoto(this, employee.getPhoto(), new ImageStorageManager.ImageStorageCallback() {
                @Override
                public void onPhotoSaved(String fileName) {

                }

                @Override
                public void onPhotoGenerated(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    final Employee employee = new Employee();
                    employee.setId(addId);
                    employee.setName(addName);
                    employee.setDesignation(addDesignation);
                    employee.setField(addField);
                    employee.setEmail(addEmail);
                    employee.setPhone(Long.parseLong(addPhone));
                    employee.setSalary(Double.parseDouble(addSalary));
                    if (selectedPhoto == null) {
                        Toast.makeText(AddEmployeeDetailsActivity.this, "Select photo!", Toast.LENGTH_SHORT).show();
                    } else {
                        ImageStorageManager.getInstance().savePhoto(AddEmployeeDetailsActivity.this, selectedPhoto, new ImageStorageManager.ImageStorageCallback() {
                            @Override
                            public void onPhotoSaved(final String fileName) {
                                employee.setPhoto(fileName);
                                employeeDatabase.addEmployee(AddEmployeeDetailsActivity.this, employee, new EmployeeDatabase.EmployeeDatabaseCallback() {
                                    @Override
                                    public void onCompletion(boolean success) {
                                        if (success) {
                                            Intent intent = new Intent(AddEmployeeDetailsActivity.this, EmployeeListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(AddEmployeeDetailsActivity.this, "Employee not inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onEmployeesRetrieved(List<Employee> employees) {
                                        //ToDo: Nothing
                                    }
                                });
                            }

                            @Override
                            public void onPhotoGenerated(Bitmap bitmap) {
                            }
                        });
                    }
                } else {
                    validation();
                }
            }
        });

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction((Intent.ACTION_GET_CONTENT));
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            try {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    selectedPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    if (selectedPhoto != null) {
                        imageView.setImageBitmap(selectedPhoto);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validation() {
        boolean valid = true;
        if (addId == null) {
            addId = UUID.randomUUID().toString().substring(0, 5);
        }
        addName = name.getText().toString().trim();
        addDesignation = designation.getSelectedItem().toString();
        addField = field.getSelectedItem().toString();
        addEmail = email.getText().toString();
        addPhone = phone.getText().toString();
        addSalary = salary.getText().toString();

        if ((addName.length() < 3) || (addName.isEmpty())) {
            Toast.makeText(this, "Name should contain atleast 3 letters!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (addDesignation.equalsIgnoreCase("Choose any") || addDesignation.isEmpty()) {
            Toast.makeText(this, "Choose any Department!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (addField.equalsIgnoreCase("Choose any") || addField.isEmpty()) {
            Toast.makeText(this, "Choose any one Field!", Toast.LENGTH_SHORT).show();
            valid = false;
        }/* else if (TextUtils.isEmpty(addEmail) || !Patterns.EMAIL_ADDRESS.matcher(addEmail).matches()) {
            Toast.makeText(this, "Enter valid email address!", Toast.LENGTH_SHORT).show();
            valid = false;
        }*/ else if (TextUtils.isEmpty(addPhone) || !Patterns.PHONE.matcher(addPhone).matches() && addPhone.length() != 10) {
            Toast.makeText(this, "Enter valid mobile number!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            if (addSalary.isEmpty()) {
                Toast.makeText(this, "Enter valid amount!", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                amount = Double.parseDouble(addSalary);
                if (amount < 2000 || amount > 25000 || amount == 0) {
                    Toast.makeText(this, "Enter valid amount!", Toast.LENGTH_SHORT).show();
                    valid = false;
                } else {
                    Locale locale = Locale.US;
                    Currency currencyx = Currency.getInstance(locale);
                    String getSymbol = currencyx.getSymbol(locale);
                    currency = NumberFormat.getInstance(Locale.US).format(amount).concat(getSymbol);
                }
            }
        }
        return valid;
    }
}
