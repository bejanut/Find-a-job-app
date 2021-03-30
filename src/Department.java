import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Department {
    String departmentName;
    ArrayList<Employee> employees;
    ArrayList<Job> availableJobs;

    public Department(ArrayList<Employee> employees, ArrayList<Job> availableJobs, String departmentName) {
        this.employees = employees;
        this.availableJobs = availableJobs;
        this.departmentName = departmentName;
    }

    public abstract double getTotalSalaryBudget();

    public ArrayList<Job> getJobs() {
        return availableJobs;
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public Employee getEmployee(String fullName) {
        for(Employee e: employees) {
            if(e.getFullName().equals(fullName))
                return e;
        }
        return null;
    }
    public void add(Job job) {
        availableJobs.add(job);
        job.department = this;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public String toString() {
        return  "Department:" + departmentName + "\n" +
                "Employees=" + employees;
    }
}

class DepartmentFactory {

    public static Department createNewDepartment(String type) {
        switch (type) {
            case "IT":
                return new IT();

            case "Marketing":
                return new Marketing();

            case "Management":
                return new Management();

            case "Finance":
                return new Finance();
        }
        throw new IllegalArgumentException("Department type: " + type +" is not recognized.");
    }
}
