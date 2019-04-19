package com.employeeservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;


/**
 * @author Raitses Vadim
 */

@Document(collection = "employee")
public class Employee implements Updatable {

    @Id
    private String id;
    @Indexed(name = "email", unique = true)
    private String email;
    private String fullName;
    private Date birthday;
    @DBRef
    private Department department;


    public String getId() {
        return id;
    }

    public Employee setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;

    }

    public String getFullName() {
        return fullName;
    }

    public Employee setFullName(String fullName) {
        this.fullName = fullName;
        return this;

    }

    public Date getBirthday() {
        return birthday;
    }

    public Employee setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;

    }

    public Department getDepartment() {
        return department;
    }

    public Employee setDepartment(Department department) {
        this.department = department;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthday=" + birthday +
                ", department=" + department +
                '}';
    }

    @JsonIgnore
    @Override
    public Update getUpdatedData() {

        return new Update()
                .set("email", this.email)
                .set("fullname", this.fullName)
                .set("birthday", this.birthday)
                .set("department", department);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        if (fullName != null ? !fullName.equals(employee.fullName) : employee.fullName != null) return false;
        if (birthday != null ? !birthday.equals(employee.birthday) : employee.birthday != null) return false;
        return department != null ? department.equals(employee.department) : employee.department == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }
}
