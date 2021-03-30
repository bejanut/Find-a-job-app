import java.util.ArrayList;

public class Employee extends Consumer{
    String companyName;
    int salary;
    boolean isRecruiter = false;

    public Employee(Resume resume, ArrayList<Consumer> socialGroup, String companyName, int salary) {
        this.resume = resume;
        this.socialGroup = socialGroup;
        this.companyName = companyName;
        this.salary = salary;
    }

    public Employee(Resume resume, ArrayList<Consumer> socialGroup) {
        this(resume, socialGroup, "", 0);
    }

    public int getExperineceInDepartment(String department){
        int years = 0;
        for(Experience e:resume.experience) {
            if(e.department.equals(department))
                years += e.begin.yearsBetween(e.end);
        }
        return years;
    }
    public boolean isRecruiter() {
        return isRecruiter;
    }
}
