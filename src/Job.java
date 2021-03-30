import java.util.ArrayList;

public class Job {
    String name, companyName;
    boolean isOpen;
    Constraint<Integer> graduationYear, experience;
    Constraint<Double> grade;
    Integer salary, noPositions;
    ArrayList<User> candidates;
    Department department;

    public Job(String name, String companyName, boolean isOpen, Constraint<Integer> graduationYear,
               Constraint<Integer> experience, Constraint<Double> grade, Integer salary, Integer noPositions) {
        this.name = name;
        this.companyName = companyName;
        this.isOpen = isOpen;
        this.graduationYear = graduationYear;
        this.experience = experience;
        this.grade = grade;
        this.salary = salary;
        this.noPositions = noPositions;
        this.candidates = new ArrayList<>();
    }

    public void apply(User user){
        Application app = Application.getInstance();
        app.getCompany(companyName).addObserver(user);
        if(meetsRequirments(user)) {
            candidates.add(user);
            //se evaluează userul si se va trimite astfel si requestul pentru job
            app.getCompany(companyName).getRecruiter(user).evaluate(this, user);
            //se adauga un nou observer la acest job
        } else {
            //notificare trimisă daca un user a fost respins
            user.update(new ObserverNotification("Job Requirments",
                    user.getFullName() + ", you have not met the job requirments for " + name
                            +" at " + companyName));
        }
    }

    public boolean meetsRequirments(User user){
        return graduationYear.respectsBoundaries(user.getGraduationYear()) &&
        experience.respectsBoundaries(user.getExperineceYears()) &&
        grade.respectsBoundaries(user.meanGPA());
    }
}
