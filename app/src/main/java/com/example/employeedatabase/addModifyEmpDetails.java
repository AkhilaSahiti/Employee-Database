package com.example.employeedatabase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Currency;
import java.text.NumberFormat;

import com.example.employeedatabase.models.Employee;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addModifyEmpDetails extends AppCompatActivity {
    private double amount;
    private int PICK_IMAGE = 1;
    Button save_btn, image_btn;
    EditText id, name, email, phone, salary; //designation, field,
    String addId, addName, addDesignation, addField, addEmail, addPhone, addSalary, addImage, currency;
    Spinner designation, field;
    ImageView imageView;
    byte[] photo;
    Bitmap bitmap;
    EmployeeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*int addId = 0;
        int addPhone = 0;
        int addSalary = 0;*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_emp_details);
        Intent intent = new Intent();
        //final Employee employee = (Employee) intent.getSerializableExtra("employee");

        id = (EditText) findViewById(R.id.enter_the_id);
        name = (EditText) findViewById(R.id.enter_the_name);
        designation = (Spinner) findViewById(R.id.enter_the_designation);
        field = (Spinner) findViewById(R.id.enter_the_field);
        email = (EditText) findViewById(R.id.enter_the_email);
        phone = (EditText) findViewById(R.id.enter_the_phone);
        salary = (EditText) findViewById(R.id.enter_the_salary);
        imageView = (ImageView) findViewById(R.id.image_display);
        save_btn = (Button) findViewById(R.id.save_btn);
        image_btn = (Button) findViewById(R.id.image_btn);
        db = new EmployeeDatabase(this);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    saveData();
                } else {
                    validation();
                }
            }
        });
        image_btn.setOnClickListener(new View.OnClickListener() {

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
            Uri uri = data.getData();
            bitmap = decodeUri(uri, 400);
            imageView.setImageBitmap(bitmap);
            /*try {
                InputStream inputStream;
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView = (ImageView) findViewById(R.id.imageView);
                 imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }




    public boolean validation() {
        boolean valid = true;

        addId = id.getText().toString();
        addName = name.getText().toString().trim();
        addDesignation = designation.getSelectedItem().toString();
        addField = field.getSelectedItem().toString();
        addEmail = email.getText().toString();
        addPhone = phone.getText().toString();
        addSalary = salary.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String idPattern = "[0-9]";
        String phonePattern = "[0-9]{10}";
        int empNo = Integer.parseInt(addId);

        //imageView.setImageResource(employee.getPhoto());
        /*if (addId.isEmpty() || (addId.length() < 0)) {
            Toast.makeText(this, "Id length should be upto 4 digits !", Toast.LENGTH_LONG).show();
            valid = false;
        } else if ( empNo > 4 || (!addId.matches(idPattern)) ) {
            Toast.makeText(this, "Id length should be upto 4 digits !", Toast.LENGTH_LONG).show();
            valid = false;
        } */

        if ((addName.length() < 3) || (addName.isEmpty())) {
            Toast toast = Toast.makeText(this, "Name should contain atleast 3 letters!", Toast.LENGTH_SHORT);
            toast.show();
            valid = false;
        } else if (addDesignation.equalsIgnoreCase("Choose any") || addDesignation.isEmpty()) {
            Toast toast = Toast.makeText(this, "Choose any Department!", Toast.LENGTH_SHORT);
            toast.show();
            valid = false;
        } else if (addField.equalsIgnoreCase("Choose any") || addField.isEmpty()) {
            Toast toast = Toast.makeText(this, "Choose any one Field!", Toast.LENGTH_SHORT);
            toast.show();
            valid = false;
        }/*else if (addEmail.isEmpty() || !addEmail.matches(emailPattern)) {
            Toast.makeText(this, "Enter valid email address!", Toast.LENGTH_SHORT).show();
            valid = false;
        } */else if (addPhone.isEmpty() || !addPhone.matches(phonePattern)) {
            Toast.makeText(this, "Enter valid mobile number!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            if (addSalary.isEmpty()) {
                Toast toast = Toast.makeText(this, "Enter valid amount!", Toast.LENGTH_SHORT);
                toast.show();
                valid = false;
            } else {
                amount = Double.parseDouble(addSalary);
                if (amount < 2000 || amount > 25000 || amount == 0) {
                    Toast toast = Toast.makeText(this, "Enter valid amount!", Toast.LENGTH_SHORT);
                    toast.show();
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

    public void saveData() {

        photo = profileImage(bitmap);
        long id = db.addData(Integer.parseInt(addId), addName, addDesignation, addField, addEmail, Long.parseLong(addPhone), amount, photo);
        Intent intent = new Intent(addModifyEmpDetails.this, MainActivity.class);
        startActivity(intent);
        finish();

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
