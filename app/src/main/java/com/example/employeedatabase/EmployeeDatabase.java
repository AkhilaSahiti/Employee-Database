package com.example.employeedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.employeedatabase.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabase extends SQLiteOpenHelper {

    private static final int dbVersion = 1;
    private static final String dbName = "employeeDb";
    public static final String tableName = "employeeTable";

    public static final String column_id = "id";
    public static final String column_name = "name";
    public static final String column_designation = "designation";
    public static final String column_field = "field";
    public static final String column_email = "email";
    public static final String column_phone = "phone";
    public static final String column_salary = "salary";


    public EmployeeDatabase(Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " (" + column_id + " INTEGER PRIMARY KEY AUTOINCREMENT," + column_name + " TEXT, " + column_designation + " TEXT, " + column_field + " TEXT, " + column_email + " TEXT, " + column_phone + " INTEGER, " + column_salary + " INTEGER" +")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public long addData(int id, String name, String designation, String field, String email, int phone, int salary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(column_id, id);
        values.put(column_name, name);
        values.put(column_designation, designation);
        values.put(column_field, field);
        values.put(column_field, email);
        values.put(column_phone, phone);
        values.put(column_salary, salary);
        long result = db.insert(tableName, null, values);
        db.close();
        return result;
        /*if(result == -1) {
            return false;
        } else {
            return true;
        }*/

    }

    public int updateData(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(column_id, employee.getId());
        values.put(column_name, employee.getName());
        values.put(column_designation, employee.getDesignation());
        values.put(column_field, employee.getField());
        values.put(column_email, employee.getEmail());
        values.put(column_phone, employee.getPhone());
        values.put(column_salary, employee.getSalary());

        return db.update(tableName, values, "id" + " = ?", new String[]{String.valueOf(employee.getId())});
    }

    public Employee findData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName,
                new String[]{column_id, column_name, column_designation, column_field, column_email, column_phone, column_salary}, column_name + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Employee employee = new Employee(
                cursor.getInt(cursor.getColumnIndex(column_id)),
                cursor.getString(cursor.getColumnIndex(column_name)),
                cursor.getString(cursor.getColumnIndex(column_designation)),
                cursor.getString(cursor.getColumnIndex(column_field)),
                cursor.getString(cursor.getColumnIndex(column_email)),
                cursor.getInt(cursor.getColumnIndex(column_phone)),
                cursor.getInt(cursor.getColumnIndex(column_salary))
                );
        cursor.close();
        return employee;
    }

    public List<Employee> getAllData() {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getInt(cursor.getColumnIndex(column_id)));
                employee.setName(cursor.getString(cursor.getColumnIndex(column_name)));
                employee.setDesignation(cursor.getString(cursor.getColumnIndex(column_designation)));
                employee.setField(cursor.getString(cursor.getColumnIndex(column_field)));
                employee.setEmail(cursor.getString(cursor.getColumnIndex(column_email)));
                employee.setPhone(cursor.getInt(cursor.getColumnIndex(column_phone)));
                employee.setSalary(cursor.getInt(cursor.getColumnIndex(column_salary)));

                employees.add(employee);
            } while (cursor.moveToNext());
        }
        db.close();
        return employees;
    }

    public void deleteData(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, column_id + "=?", new String[]{String.valueOf(employee.getId())});
    }
}
    /* int id = Integer.parseInt(cursor.getString(0));
       String name = cursor.getString(0);
       String designation = cursor.getString(1);
       String field = cursor.getString(2);
       String email = cursor.getString(3);
       int phno = Integer.parseInt(cursor.getString(5));
       int salary = Integer.parseInt(cursor.getString(6));
       employeeList.add(new Employee(name, designation, field, email)); //, phno, salary));
       } while (cursor.moveToNext());
       }
       cursor.close();
       return employeeList;
    }
    Employee employee1 = new Employee();
     employee1.setId(Integer.parseInt(cursor.getString(0)));
     employee1.setName(cursor.getString(1));
     employee1.setDesignation(cursor.getString(2));
     employee1.setField(cursor.getString(3));
     employee1.setEmail(cursor.getString(4));
     employee1.setPhone(Integer.parseInt(cursor.getString(5)));
     employee1.setSalary(Integer.parseInt(cursor.getString(6)));
     employeeList.add(employee1);


    public Employee find(String employeeName) {
        String query = "SELECT * FROM " + tableName + " WHERE " + name + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Employee employee = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            int Id = Integer.parseInt(cursor.getString(0));
            String Name = cursor.getString(1);
            String Designation = cursor.getString(2);
            String Field = cursor.getString(3);
            String Email = cursor.getString(4);
            int Phone = Integer.parseInt(cursor.getString(5));
            int Salary = Integer.parseInt(cursor.getString(6));

            employee = new Employee(Name, Designation, Field, Email); //, Phone, Salary);
        }
        cursor.close();
        return employee;
    }*/
