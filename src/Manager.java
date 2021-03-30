import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Manager extends Employee{
    ArrayList<Request<Job, Consumer>> requests;
    ArrayList<Job> availableJobs = new ArrayList<>();
    public Manager(Resume resume, ArrayList<Consumer> socialGroup) {
        super(resume, socialGroup);
        requests = new ArrayList<>();
    }
    public Manager(Employee employee) {
        super(employee.resume, employee.socialGroup, employee.companyName, employee.salary);
        requests = new ArrayList<>();
    }

    void process(Job job){
        ArrayList<Request<Job, Consumer>> toBeProcessed = new ArrayList<>();
        for(Request<Job, Consumer> r: requests) {
            if(r.getKey() == job)
                toBeProcessed.add(r);
        }
        //se sorteaza coletia de requesturi in ordine descrescătoare
        Collections.sort(toBeProcessed, new Comparator<Request<Job, Consumer>>() {

            public int compare(Request<Job, Consumer> o1, Request<Job, Consumer> o2) {
                double out = o2.getScore() - o1.getScore();
                if(out == 0) {
                    out = ((Recruiter)o2.getValue2()).rating - ((Recruiter)o1.getValue2()).rating;
                }
                if(out == 0)
                    return 0;
                if(out < 0)
                    return -1;
                else
                    return 1;
            }
        });
        //angajarea noilor useri
        Application app = Application.getInstance();
        for(Request<Job, Consumer> r: toBeProcessed) {
            if(job.noPositions > 0)
                hire(r);
            else
                decline(r);
        }
        availableJobs.remove(job);
        job.department.availableJobs.remove(job);
        job.isOpen = false;
    }
    public void closeJob(Job job) {
        ArrayList<Request<Job, Consumer>> toBeProcessed = new ArrayList<>();
        for(Request<Job, Consumer> r: requests) {
            if(r.getKey() == job)
                toBeProcessed.add(r);
        }
        requests.removeAll(toBeProcessed);
        job.isOpen = false;
        availableJobs.remove(job);
        job.department.availableJobs.remove(job);
        for(Request<Job, Consumer> r: toBeProcessed){
            ((User) r.getValue1()).update(new ObserverNotification("Job application",
                    "We are sorry to inform you that you didn't get the position" +
                            job.name + " at " + job.companyName));
        }
    }
    public void add(Job job ,Department department) {
        job.department = department;
        department.add(job);
        Application.getInstance().getCompany(companyName)
                .notifyAllObservers(new ObserverNotification("New Job Available",
                "The company "+ companyName + " has a new job available. Check the attached file.",
                job));
        availableJobs.add(job);
    }
    public String numberOfApplications (Job job) {
        Integer nr = 0;
        for(Request<Job, Consumer> r:requests) {
            if(r.getKey() == job)
                nr++;
        }
        return  nr.toString();
    }
    public void hire(Request<Job, Consumer> request) {
            User user = (User) request.getValue1();
            Job job = request.getKey();

            removeOtherRequests(user);
        if(Application.getInstance().userList.contains(user)) {
            Application.getInstance().userList.remove(user);
            //se dezaboneaza Userul
            user.cancelAllSubscriptions();
            //se seteaza toate campurile angajatului
            Employee newEmployee = user.convert();
            newEmployee.companyName = companyName;
            newEmployee.salary = job.salary;
            newEmployee.add(new Experience(new Date(), null, job.name, companyName, job.department.departmentName));
            //se adauga angajatul la departamentul potrivit
            job.department.add(newEmployee);

            job.noPositions--;
        }
    }
    public void removeOtherRequests(User user) {
        requests.removeIf(r -> r.getValue1().compareTo(user) == 0);
    }

    public void decline(Request<Job, Consumer> request) {
        requests.remove(request);
        ((User) request.getValue1()).update(new ObserverNotification("Job application",
                "We are sorry to inform you that you didn't get the position " +
                        request.getKey().name + " at " + request.getKey().companyName));
    }
}
