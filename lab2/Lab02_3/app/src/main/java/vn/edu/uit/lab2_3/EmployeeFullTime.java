package vn.edu.uit.lab2_3;

public class EmployeeFullTime extends Employee {

    @Override
    public double tinhLuong() {
        
        return 500.0;
    }

    @Override
    public String toString() {
        
        return super.toString() + " -->Chính thức=" + tinhLuong();
    }
}
