package com.example.employeedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.employeedatabase.adapter.EmployeeAdapter;
import com.example.employeedatabase.models.Employee;
import com.example.employeedatabase.storage.EmployeeDatabase;

import java.util.List;

public class EmployeeListActivity extends AppCompatActivity implements EmployeeAdapter.EmployeeAdapterCallback {

    private EmployeeAdapter employeeAdapter;
    private EmployeeDatabase employeeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employeeDatabase = new EmployeeDatabase(this);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(mLayoutManager);

        ImageButton addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeListActivity.this, AddEmployeeDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        employeeDatabase.getEmployees(this, new EmployeeDatabase.EmployeeDatabaseCallback() {
            @Override
            public void onCompletion(boolean success) {
                //ToDo: Nothing
            }

            @Override
            public void onEmployeesRetrieved(List<Employee> employees) {
                if (employees != null && !employees.isEmpty()) {
                    employeeAdapter = new EmployeeAdapter(EmployeeListActivity.this);
                    employeeAdapter.addAll(employees);
                    recyclerView.setAdapter(employeeAdapter);
                }

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchEmployee(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    employeeDatabase.getEmployees(EmployeeListActivity.this, new EmployeeDatabase.EmployeeDatabaseCallback() {
                        @Override
                        public void onCompletion(boolean success) {

                        }

                        @Override
                        public void onEmployeesRetrieved(List<Employee> employees) {
                            if (employees != null && !employees.isEmpty()) {
                                employeeAdapter.addAll(employees);
                            }
                        }
                    });
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onEmployeeClicked(Employee employee) {
        Intent intent = new Intent(this, DisplayEmployeeActivity.class);
        intent.putExtra("employee", employee);
        startActivity(intent);
    }

    @Override
    public void onEmployeeDelete(Employee employee) {

        employeeDatabase.deleteEmployee(this, employee, new EmployeeDatabase.EmployeeDatabaseCallback() {
            @Override
            public void onCompletion(boolean success) {

            }

            @Override
            public void onEmployeesRetrieved(List<Employee> employees) {
                //Todo: Nothing
            }
        });
    }


    @Override
    public void onEmployeeEdit(Employee employee) {
        if (employee != null) {
            Intent intent = new Intent(this, AddEmployeeDetailsActivity.class);
            intent.putExtra("employee", employee);
            startActivity(intent);
        }
    }

    private void searchEmployee(String searchQuery) {
        EmployeeDatabase employeeDatabase = new EmployeeDatabase(this);
        employeeDatabase.searchEmployees(this, searchQuery, new EmployeeDatabase.EmployeeDatabaseCallback() {
            @Override
            public void onCompletion(boolean success) {
                //Todo: Nothing
            }

            @Override
            public void onEmployeesRetrieved(List<Employee> employees) {
                if (employees != null && !employees.isEmpty()) {
                    employeeAdapter.addAll(employees);
                } else {
                    Toast.makeText(getApplicationContext(), "No match found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}