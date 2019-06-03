package com.example.employeedatabase.storage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.employeedatabase.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase extends SQLiteOpenHelper {

    private static final int dbVersion = 2;
    private static final String dbName = "employeeDb";
    private static final String tableName = "employeeTable";
    private static final String column_id = "id";
    private static final String column_name = "name";
    private static final String column_designation = "designation";
    private static final String column_field = "field";
    private static final String column_email = "email";
    private static final String column_phone = "phone";
    private static final String column_salary = "salary";
    private static final String column_image = "image";

    public EmployeeDatabase(Context context) {
        super(context, dbName, null, dbVersion);
    }

    public interface EmployeeDatabaseCallback {
        void onCompletion(boolean success);

        void onEmployeesRetrieved(List<Employee> employees);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " (" + column_id + " TEXT PRIMARY KEY," + column_name + " TEXT, " + column_designation + " TEXT, " + column_field + " TEXT, " + column_email + " TEXT, " + column_phone + " LONG, " + column_salary + " DOUBLE, " + column_image + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public void addEmployee(final Context context, final Employee employee, final EmployeeDatabaseCallback listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = EmployeeDatabase.this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(column_id, employee.getId());
                contentValues.put(column_name, employee.getName());
                contentValues.put(column_designation, employee.getDesignation());
                contentValues.put(column_field, employee.getField());
                contentValues.put(column_email, employee.getEmail());
                contentValues.put(column_phone, employee.getPhone());
                contentValues.put(column_salary, employee.getSalary());
                contentValues.put(column_image, employee.getPhoto());

                boolean result = false;
                Cursor cursor = db.query(tableName, new String[]{column_id}, column_id + "=?", new String[]{String.valueOf(employee.getId())}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        if (db.update(tableName, contentValues, column_id + "=?", new String[]{String.valueOf(employee.getId())}) > 0) {
                            result = true;
                        }
                    } else {
                        if (db.insert(tableName, null, contentValues) > 0) {
                            result = true;
                        }
                    }
                    cursor.close();
                }
                db.close();
                final boolean res = result;
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCompletion(res);
                    }
                });
            }
        });
    }

    public void deleteEmployee(final Context context, final Employee employee, final EmployeeDatabaseCallback listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = EmployeeDatabase.this.getWritableDatabase();

                boolean result = false;
                if (db.delete(tableName, column_id + "=?", new String[]{String.valueOf(employee.getId())}) == -1) {
                    result = true;
                }
                db.close();
                final boolean res = result;
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCompletion(res);
                    }
                });
            }
        });
    }

    public void searchEmployees(final Context context, final String query, final EmployeeDatabaseCallback listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Employee> employees = new ArrayList<>();
                String searchQuery = query.trim();
                SQLiteDatabase db = EmployeeDatabase.this.getReadableDatabase();
                Cursor cursor = db.query(tableName,
                        new String[]{column_id, column_name, column_designation, column_field, column_email, column_phone, column_salary, column_image}, column_name + "=? OR " + column_designation + "=? OR " + column_field + "=?",
                        new String[]{searchQuery, searchQuery, searchQuery}, null, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    do {
                        Employee employee = new Employee();
                        employee.setId(cursor.getString(cursor.getColumnIndex(column_id)));
                        employee.setName(cursor.getString(cursor.getColumnIndex(column_name)));
                        employee.setDesignation(cursor.getString(cursor.getColumnIndex(column_designation)));
                        employee.setField(cursor.getString(cursor.getColumnIndex(column_field)));
                        employee.setEmail(cursor.getString(cursor.getColumnIndex(column_email)));
                        employee.setPhone(cursor.getLong(cursor.getColumnIndex(column_phone)));
                        employee.setSalary(cursor.getDouble(cursor.getColumnIndex(column_salary)));
                        employee.setPhoto(cursor.getString(cursor.getColumnIndex(column_image)));
                        employees.add(employee);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                cursor.close();
                db.close();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onEmployeesRetrieved(employees);
                    }
                });
            }
        });
    }

    public void getEmployees(final Context context, final EmployeeDatabaseCallback listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Employee> employees = new ArrayList<>();
                String sql = "SELECT * FROM " + tableName;
                SQLiteDatabase db = EmployeeDatabase.this.getWritableDatabase();
                Cursor cursor = db.rawQuery(sql, null);

                if (cursor.moveToFirst()) {
                    do {
                        Employee employee = new Employee();
                        employee.setId(cursor.getString(cursor.getColumnIndex(column_id)));
                        employee.setName(cursor.getString(cursor.getColumnIndex(column_name)));
                        employee.setDesignation(cursor.getString(cursor.getColumnIndex(column_designation)));
                        employee.setField(cursor.getString(cursor.getColumnIndex(column_field)));
                        employee.setEmail(cursor.getString(cursor.getColumnIndex(column_email)));
                        employee.setPhone(cursor.getLong(cursor.getColumnIndex(column_phone)));
                        employee.setSalary(cursor.getDouble(cursor.getColumnIndex(column_salary)));
                        employee.setPhoto(cursor.getString(cursor.getColumnIndex(column_image)));

                        employees.add(employee);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onEmployeesRetrieved(employees);
                    }
                });
            }
        });
    }
}
