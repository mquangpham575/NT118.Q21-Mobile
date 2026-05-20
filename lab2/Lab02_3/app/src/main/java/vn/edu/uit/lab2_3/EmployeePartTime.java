package vn.edu.uit.lab2_3;

public class EmployeePartTime extends Employee {

    @Override
    public double tinhLuong() {
        // Return fixed salary for part-time employee.
        return 150.0;
    }

    @Override
    public String toString() {
        // Return formatted employee information along with part-time salary.
        return super.toString() + " -->Thời vụ=" + tinhLuong();
    }
}
