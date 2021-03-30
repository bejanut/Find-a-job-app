import java.util.ArrayList;

public class Marketing extends Department{

    public Marketing(ArrayList<Employee> employees, ArrayList<Job> availableJobs) {
        super(employees, availableJobs, "Marketing");
    }
    public Marketing() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public double getTotalSalaryBudget() {
        double sum = 0;
        for(Employee e: employees){
            if(e.salary > 5000)
                sum += e.salary*1.1;
            else
                if(e.salary < 3000)
                    sum += e.salary;
                else
                    sum += e.salary * 1.16;
        }
        return sum;
    }
}
