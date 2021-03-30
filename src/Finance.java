import java.util.ArrayList;

public class Finance extends Department{

    public Finance(ArrayList<Employee> employees, ArrayList<Job> availableJobs) {
        super(employees, availableJobs, "Finance");
    }

    public Finance() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public double getTotalSalaryBudget() {
        double sum = 0;
        for(Employee e: employees){
            if(e.getExperineceInDepartment(departmentName) > 1)
                sum += e.salary * 1.16;
            else
                sum += e.salary * 1.1;
        }
        return sum;
    }
}
