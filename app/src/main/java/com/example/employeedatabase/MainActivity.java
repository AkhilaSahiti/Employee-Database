package com.example.employeedatabase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.employeedatabase.adapter.EmployeeAdapter;
import com.example.employeedatabase.models.Employee;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //List<Employee> employeeList = new ArrayList<>();
    public static EmployeeAdapter employeeAdapter;
    RecyclerView recyclerView;
    EmployeeDatabase employeeDatabase;

    public static void notifyAdapter() {
        employeeAdapter.notifyDataSetChanged();
    }
    //ImageButton search_btn, add_btn, edit_btn;
    //EditText find_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        employeeDatabase = new EmployeeDatabase(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addModifyEmpDetails.class);
                startActivity(intent);
                finish();
            }
        });
        employeeAdapter = new EmployeeAdapter(this, employeeDatabase.getAllData(), employeeDatabase);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(employeeAdapter);


    }
}
        /*employeeList = employeeDatabase.getAllData();

        if(employeeList.size()>0) {
            recyclerView.setVisibility(View.VISIBLE);
            employeeAdapter = new EmployeeAdapter(this, employeeList);
            recyclerView.setAdapter(employeeAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "Empty Database", Toast.LENGTH_LONG).show();
        }

        search_btn = (ImageButton) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        add_btn = (ImageButton) findViewById(R.id.fab);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        edit_btn = (ImageButton) findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void search(){
        EmployeeDatabase employeeDatabase = new EmployeeDatabase(this);
        Employee employee = employeeDatabase.findData(find_name.getText().toString());
        if (employee !=null){
            Intent intent = new Intent(this, DisplayScreenActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"No match found", Toast.LENGTH_SHORT).show();
        }
    }

    public void add(){
        startActivity(new Intent(MainActivity.this, addModifyEmpDetails.class));
    }
}*/
