package vn.edu.uit.lab2_4;

public class Employee {
    private String id;
    private String fullName;
    private boolean isManager;

    public Employee(String id, String fullName, boolean isManager) {
        // Initialize an Employee instance.
        this.id = id;
        this.fullName = fullName;
        this.isManager = isManager;
    }

    public String getId() {
        // Return employee id.
        return id;
    }

    public void setId(String id) {
        // Set employee id.
        this.id = id;
    }

    public String getFullName() {
        // Return employee full name.
        return fullName;
    }

    public void setFullName(String fullName) {
        // Set employee full name.
        this.fullName = fullName;
    }

    public boolean isManager() {
        // Return true if the employee is a manager.
        return isManager;
    }

    public void setManager(boolean manager) {
        // Set manager status of the employee.
        isManager = manager;
    }
}
