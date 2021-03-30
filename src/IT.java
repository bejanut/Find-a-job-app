import java.util.ArrayList;

public class IT extends Department{

    public IT(ArrayList<Employee> employees, ArrayList<Job> availableJobs) {
        super(employees, availableJobs, "IT");
    }

    public IT() {
       this(new ArrayList<>(), new ArrayList<>());
    }

    public double getTotalSalaryBudget() {
        double sum = 0;
        for(Employee e: employees){
            sum += e.salary;
        }
        return sum;
    }
}
