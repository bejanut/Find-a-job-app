import java.util.ArrayList;
import java.util.List;

public class Application {
    ArrayList<Company> companyList;
    ArrayList<User> userList;
    private static final Application instance = new Application();

    private Application() {
        companyList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    public ArrayList<Company> getCompanies() {
        return companyList;
    }
    public Company getCompany(String name){
        for(Company c:companyList) {
            if(c.name.equals(name))
                return c;
        }
        return null;
    }
    public void add(Company company) {
        companyList.add(company);
    }
    public void add(User user) {
        userList.add(user);
    }
    public boolean remove(Company company) {
        return companyList.remove(company);
    }
    public boolean remove(User user) {
        return userList.remove(user);
    }
    public ArrayList<Job> getJobs(List<String> companies) {
        Company curentCompany;
        ArrayList<Job> jobs = new ArrayList<>();
        for(String name: companies) {
            curentCompany = getCompany(name);
            if(curentCompany != null)
                jobs.addAll(curentCompany.getJobs());
        }
        return jobs;
    }

    public static Application getInstance (){
        return instance;
    }
}
