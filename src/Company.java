import java.util.ArrayList;
import java.util.Collections;

public class Company implements Subject{
    String name;
    Manager manager;
    ArrayList<Department> departments;
    ArrayList<Recruiter> recruiters;
    ArrayList<Observer> observers = new ArrayList<>();

    public Company(String name, Manager manager, ArrayList<Department> departments, ArrayList<Recruiter> recruiters) {
        this.name = name;
        this.manager = manager;
        this.departments = departments;
        this.recruiters = recruiters;
    }

    public Company(String name, Manager manager) {
        this(name, manager, new ArrayList<>(), new ArrayList<>());
    }
    public void add(Department department) {
        departments.add(department);
    }
    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }
    public void add(Employee employee, Department department) {
        department.add(employee);
    }
    public void remove(Employee employee) {
        //se seteaza data finala a angajarii ca fiind data curenta
        employee.resume.experience.first().end = new Date();
        Department department = getDepartment(employee.resume.experience.first().department);
        department.remove(employee);
        if(employee.isRecruiter()) {
            for(Recruiter r:recruiters) {
                if(r.compareTo(employee) == 0) {
                    recruiters.remove(r);
                    return;
                }
            }
        }
        User oldEmployee = new User(employee);
        oldEmployee.update(new ObserverNotification("You have been fired",
                "You have been fired form " + name));
        Application.getInstance().add(oldEmployee);
    }
    public void remove(Department department){
        departments.remove(department);
    }
    public void remove(Recruiter recruiter){
        recruiters.remove(recruiter);
    }
    public void move(Department source, Department destination) {
        destination.availableJobs.addAll(source.availableJobs);
        destination.employees.addAll(source.employees);
        for(Employee e: source.getEmployees()) {
            e.resume.experience.first().department = destination.departmentName;
        }
        departments.remove(source);
    }
    public void move(Employee employee, Department newDepartment) {
        remove(employee);
        add(employee, newDepartment);
    }
    public boolean contains(Department department) {
        return departments.contains(department);
    }
    public boolean contains(Employee employee) {
        for(Department d: departments) {
            if(d.employees.contains(employee))
                return true;
        }
        return false;
    }
    public boolean contains(Recruiter recruiter) {
        return recruiters.contains(recruiter);
    }
    public Recruiter getRecruiter(User user) {
        int highestDegree = 0, current;
        Recruiter selectedRecruiter = null;
        for(Recruiter r: recruiters) {
            current = r.getDegreeInFriendship(user);
            if(current > highestDegree) {
                selectedRecruiter = r;
                highestDegree = current;
            }
            else
                if(current == highestDegree && selectedRecruiter != null && selectedRecruiter.rating < r.rating)
                    selectedRecruiter = r;
        }
        //caz special in care userul nu are niciun recruiter in lista de conexiuni
        if (selectedRecruiter == null) {
            selectedRecruiter = recruiters.get(0);
        }
        return selectedRecruiter;
    }
    public ArrayList<Job> getJobs(){
        ArrayList<Job> totalJobs = new ArrayList<>();
        for(Department d: departments) {
            totalJobs.addAll(d.getJobs());
        }
        return totalJobs;
    }
    public Department getDepartment (String department) {
        for (Department d: departments) {
            if(d.departmentName.equals(department))
                return d;
        }
        return null;
    }
    public String toString() {
        return "Company:" + name + "\nDepartment" + departments
                + "\n\nRecruiters" + recruiters;
    }

    public void addObserver(Observer observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
            observer.addSubscription(this);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
        observer.cancelSubscription(this);
    }

    public void notifyAllObservers(ObserverNotification notification) {
        for(Observer o:observers) {
            o.update(notification);
        }
    }
}
