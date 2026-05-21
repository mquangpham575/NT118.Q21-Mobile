package vn.edu.uit.lab2_3;

public class EmployeePartTime extends Employee {

    @Override
    public double tinhLuong() {
        
        return 150.0;
    }

    @Override
    public String toString() {
        
        return super.toString() + " -->Thời vụ=" + tinhLuong();
    }
}
