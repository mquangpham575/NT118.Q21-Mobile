package vn.edu.uit.lab03_1;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String name;
    private String studentId;
    private String className;

    public Student() {
    }

    public Student(int id, String name, String studentId, String className) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.className = className;
    }

    public Student(String name, String studentId, String className) {
        this.name = name;
        this.studentId = studentId;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
