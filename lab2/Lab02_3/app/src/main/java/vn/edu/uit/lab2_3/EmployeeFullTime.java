package vn.edu.uit.lab2_3;

public class EmployeeFullTime extends Employee {

    @Override
    public double tinhLuong() {
        // Return fixed salary for full-time employee.
        return 500.0;
    }

    @Override
    public String toString() {
        // Return formatted employee information along with full-time salary.
        return super.toString() + " -->Chính thức=" + tinhLuong();
    }
}
