
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Test {

    public static void main(String[] args) throws Exception {
        Test helper = new Test();
        String resourceName = "consumers.json";
        Application app = Application.getInstance();
        String jsonContent = new String(Files.readAllBytes(Paths.get(resourceName)));
        JSONObject consumers = new JSONObject(jsonContent);
        JSONArray employees, recruiters, users, managers;
        ArrayList<Consumer> allConsumers = new ArrayList<>();

        employees = consumers.getJSONArray("employees");
        recruiters = consumers.getJSONArray("recruiters");
        users = consumers.getJSONArray("users");
        managers = consumers.getJSONArray("managers");

        helper.createCompanies(managers);
        for(int i = 0; i < employees.length(); i++){
            JSONObject curentEmployee = employees.getJSONObject(i);

            Employee newEmployee = helper.getEmployee(curentEmployee);
            Company company = app.getCompany(newEmployee.companyName);
            company.getDepartment(newEmployee.resume.experience.first().department)
                    .add(newEmployee);

            allConsumers.add(newEmployee);
        }
        for(int i = 0; i < recruiters.length(); i++) {
            Recruiter newRecruiter = helper.getRecruiter(recruiters.getJSONObject(i));
            app.getCompany(newRecruiter.companyName).add(newRecruiter);

            allConsumers.add(newRecruiter);
        }
        for(int i = 0; i < users.length(); i++){
            JSONObject curentUser = users.getJSONObject(i);

            User newUser = helper.getUser(curentUser);
            app.add(newUser);

            allConsumers.add(newUser);
        }

        helper.setSocialGroups(allConsumers);
        helper.setJobs();
        helper.finalPart();

    }
    private void finalPart() {
        Application app = Application.getInstance();
        for(User u: app.userList) {
            applyToJobs(u);
        }
        //Parte in care joburile sunt procesate, pentru a lucra in interfața grafică
        //ar fi recomandat să nu se proceseze joburile
//        for (Company c: app.getCompanies()) {
//            while(!c.manager.requests.isEmpty()) {
//                Job job = c.manager.requests.get(0).getKey();
//                c.manager.process(job);
//            }
//        }
    }
    private void applyToJobs(User u) {
        Application app = Application.getInstance();
        for (String s: u.interestedCompany) {
            Company current = app.getCompany(s);
            Department department = current.getDepartment("IT");
            for(Job j:department.getJobs()) {
                j.apply(u);
            }
        }
    }
    private void setJobs() throws IOException {
        Application app = Application.getInstance();
        String resourceName = "jobs.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(resourceName)));
        JSONObject jobs = new JSONObject(jsonContent);
        JSONArray jobsArray = jobs.getJSONArray("jobs");
        for(Object obj: jobsArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Company curentCompany = app.getCompany(jsonObject.getString("company"));
            Integer graduationLow = null, graduationHigh = null, experienceLow = null, experienceHigh = null;
            Double averageLow = null, averageHigh = null;

            if(jsonObject.has("graduationLow")) {
                graduationLow = jsonObject.getInt("graduationLow");
            }
            if(jsonObject.has("graduationHigh")) {
                graduationHigh = jsonObject.getInt("graduationHigh");
            }
            if(jsonObject.has("experienceLow")) {
                experienceLow = jsonObject.getInt("experienceLow");
            }
            if(jsonObject.has("experienceHigh")) {
                experienceHigh = jsonObject.getInt("experienceHigh");
            }
            if(jsonObject.has("averageLow")) {
                averageLow = jsonObject.getDouble("averageLow");
            }
            if(jsonObject.has("averageHigh")) {
                averageHigh = jsonObject.getDouble("averageHigh");
            }
            curentCompany.manager.add(new Job(jsonObject.getString("name"),
                    curentCompany.name, true,
                    new Constraint<Integer>(graduationLow, graduationHigh),
                    new Constraint<Integer>(experienceLow, experienceHigh),
                    new Constraint<Double>(averageLow, averageHigh),
                    jsonObject.getInt("salary"), jsonObject.getInt("positions")),
                    curentCompany.getDepartment(jsonObject.getString("department")));
        }
    }
    private void setSocialGroups(ArrayList<Consumer> array) {
        File f = new File("SocialGroup.txt");
        try {
            Scanner input = new Scanner(f);
            for(int i = 0; i < array.size(); i++){
                String line = input.nextLine();
                StringTokenizer tokens = new StringTokenizer(line, " ");
                while(tokens.hasMoreTokens()) {
                    array.get(i).add(array.get(Integer.parseInt(tokens.nextToken())));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void createCompanies(JSONArray managers) {
        Application app = Application.getInstance();
        for(Object obj: managers) {
            JSONObject jObj = (JSONObject) obj;
            Manager manager = new Manager((getEmployee(jObj)));
            Company company = new Company(manager.companyName,manager);

            JSONArray departmentsArray = jObj.getJSONArray("company_departments");
            for(Object departmentsName: departmentsArray) {
                company.add(DepartmentFactory.createNewDepartment((String) departmentsName));
            }
            app.add(company);
            AccountsDatabase.getInstance().addConsumer(
                    manager.getFullName(),
                    "password", manager);
        }
    }
    private Employee getEmployee(JSONObject currentEmployee) {

        JSONArray educationArrray = currentEmployee.getJSONArray("education"),
                experienceArray = currentEmployee.getJSONArray("experience");
        Integer salary = currentEmployee.getInt("salary");
        Employee newEmployee = new Employee(new Resume.ResumeBuilder()
                .information(createInformation(currentEmployee))
                .addEducation(createEducation(educationArrray.getJSONObject(0)))
                .build(),
                new ArrayList<>(), "", salary);

        for(int edu = 1; edu < educationArrray.length(); edu++) {
            newEmployee.add(createEducation(educationArrray.getJSONObject(edu)));
        }
        for(int exp = 0; exp < experienceArray.length(); exp++) {
            newEmployee.add(createExperience(experienceArray.getJSONObject(exp)));
        }
        newEmployee.companyName = newEmployee.resume.experience.first().company;

        AccountsDatabase.getInstance().addConsumer(
                newEmployee.getFullName(),
                "password", newEmployee);

        return newEmployee;
    }
    private Recruiter getRecruiter(JSONObject currentRecruiter) {
        Employee newEmployee = getEmployee(currentRecruiter);
        //se creeaza un nou employee, cu datele specifice de recruiter, care va fi adaugat
        //in departamentul de IT, al companiei sale
        newEmployee.resume.experience.first().department = "IT";
        newEmployee.isRecruiter = true;

        Company company = Application.getInstance().getCompany(newEmployee.companyName);
        company.getDepartment(newEmployee.resume.experience.first().department)
                .add(newEmployee);

        return new Recruiter(newEmployee);
    }
    private User getUser (JSONObject currentUser) {
        JSONArray educationArray = currentUser.getJSONArray("education"),
                experienceArray = currentUser.getJSONArray("experience"),
                interestedCompanies = currentUser.getJSONArray("interested_companies");
        ArrayList<String> companies = new ArrayList<>();

        for(int c = 0; c < interestedCompanies.length(); c++){
            companies.add(interestedCompanies.getString(c));
        }

        User newUser = new User(new Resume.ResumeBuilder()
                .information(createInformation(currentUser))
                .addEducation(createEducation(educationArray.getJSONObject(0)))
                .build(),
                new ArrayList<>(), companies);

        for(int edu = 1; edu < educationArray.length(); edu++) {
            newUser.add(createEducation(educationArray.getJSONObject(edu)));
        }
        for(int exp = 0; exp < experienceArray.length(); exp++) {
            newUser.add(createExperience(experienceArray.getJSONObject(exp)));
        }
        AccountsDatabase.getInstance().addConsumer(
                newUser.getFullName(),
                "password", newUser);
        return newUser;
    }
    private Experience createExperience(JSONObject experienceEntry) {
        Date begin = transformDate(experienceEntry.getString("start_date")),
                end;

        Object obj = experienceEntry.get("end_date");
        if(obj instanceof String)
            end = transformDate((String) obj);
        else
            end = null;
        String position = experienceEntry.getString("position"),
                company = experienceEntry.getString("company");
        if(experienceEntry.has("department")) {
            String department = experienceEntry.getString("department");
            return new Experience(begin, end, position, company, department);
        }

        return new Experience(begin, end, position, company);
    }
    private Education createEducation(JSONObject educationEntry) {
        EducationLevel level = EducationLevel.getLevel(educationEntry.getString("level"));
        String institute = educationEntry.getString("name");
        Float grade = educationEntry.getFloat("grade");
        Date begin = transformDate(educationEntry.getString("start_date")),
                end;

        Object obj = educationEntry.get("end_date");
        if(obj instanceof String)
            end = transformDate((String) obj);
        else
            end = null;

        return new Education(begin, end, institute, level, grade);
    }
    private Information createInformation(JSONObject curentUser) {
        String[] names = separateName(curentUser.getString("name"));
        String phone = curentUser.getString("phone"),
                email = curentUser.getString("email");
        Date birthday = transformDate(curentUser.getString("date_of_birth"));
        Sex sex;
        if(curentUser.getString("genre").equals("male"))
            sex = Sex.Male;
        else
            sex = Sex.Female;

        ArrayList<Language> languages = new ArrayList<>();
        JSONArray languageArray = curentUser.getJSONArray("languages"),
                levelArray = curentUser.getJSONArray("languages_level");
        for(int l = 0; l < languageArray.length(); l++) {
            languages.add(new Language(languageArray.getString(l), levelArray.getString(l)));
        }

        return new Information(names[0], names[1], email, phone, birthday, languages, sex);
    }
    private String[] separateName (String completeName){
        StringTokenizer s = new StringTokenizer(completeName, " ");
        String name = "", surname = "", current = s.nextToken();
        while(s.hasMoreTokens()){
            name += current + " ";
            current = s.nextToken();
        }
        surname = current;
        String[] out = new String[2];
        out[0] = name;
        out[1] = surname;

        return out;
    }
    private Date transformDate(String date) {
        StringTokenizer s = new StringTokenizer(date, ". ");
        return new Date(Integer.parseInt(s.nextToken()),
                Integer.parseInt(s.nextToken()),
                Integer.parseInt(s.nextToken()));
    }

}
