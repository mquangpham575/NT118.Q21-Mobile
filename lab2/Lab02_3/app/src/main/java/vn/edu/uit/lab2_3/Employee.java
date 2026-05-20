package vn.edu.uit.lab2_3;

public abstract class Employee {
    private String id;
    private String name;

    public String getId() {
        // Return the employee unique identification string.
        return id;
    }

    public void setId(String id) {
        // Set the employee unique identification string.
        this.id = id;
    }

    public String getName() {
        // Return the employee's name.
        return name;
    }

    public void setName(String name) {
        // Set the employee's name.
        this.name = name;
    }

    public abstract double tinhLuong();

    @Override
    public String toString() {
        // Return the formatted base information of the employee.
        return id + " - " + name;
    }
}
