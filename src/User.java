import java.util.ArrayList;

public class User extends Consumer implements Observer{
    ArrayList<String> interestedCompany;
    ArrayList<ObserverNotification> observerNotificantions = new ArrayList<>();
    ArrayList<Subject> subscriptions = new ArrayList<>();

    public User(Resume resume, ArrayList<Consumer> socialGroup, ArrayList<String> interestedCompany) {
        this.resume = resume;
        this.socialGroup = socialGroup;
        this.interestedCompany = interestedCompany;
    }
    public User(Employee employee) {
        resume = employee.resume;
        socialGroup = employee.socialGroup;
        interestedCompany = new ArrayList<>();
    }
    public Employee convert(){
        return new Employee(resume, socialGroup);
    }

    public Double getTotalScore(){
        return 1.5 * getExperineceYears() + meanGPA();
    }

    public int getExperineceYears(){
        int years = 0;
        for(Experience e:resume.experience) {
            years += e.begin.numberOfDaysUntilDate(e.end) / 372;
            if(e.begin.numberOfDaysUntilDate(e.end) % 372 != 0)
                years ++;
        }
        return years;
    }

    public void update(ObserverNotification observerNotification) {
        observerNotificantions.add(observerNotification);
    }

    public void cancelAllSubscriptions() {
        for(int i = 0; i < subscriptions.size(); i++) {
            subscriptions.get(i).removeObserver(this);
        }
    }

    public void cancelSubscription(Subject subject){
        subscriptions.remove(subject);
    }

    public void addSubscription(Subject subject) {
        subscriptions.add(subject);
    }
}
