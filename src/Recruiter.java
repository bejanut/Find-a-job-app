import java.util.ArrayList;

public class Recruiter extends Employee{
    Double rating = 5.0;
    public Recruiter(Resume resume, ArrayList<Consumer> socialGroup, String companyName, int salary) {
        super(resume, socialGroup, companyName, salary);
    }
    public Recruiter(Employee employee) {
        this(employee.resume, employee.socialGroup, employee.companyName, employee.salary);
    }
    public double evaluate(Job job, User user) {
        double score = user.getTotalScore() * rating;
        Company company = Application.getInstance().getCompany(companyName);
        company.manager.requests.add(new Request<Job, Consumer>(job, user, this, score));
        rating += 0.1;

        return score;
    }
}
