import java.util.ArrayList;

public class Management extends Department{
    public Management(ArrayList<Employee> employees, ArrayList<Job> availableJobs) {
        super(employees, availableJobs, "Management");
    }

    public Management() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public double getTotalSalaryBudget() {
        double sum = 0;
        for(Employee e: employees){
            sum += e.salary;
        }
        return sum * 1.16;
    }
}
