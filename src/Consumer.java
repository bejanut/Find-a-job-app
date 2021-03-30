import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class Consumer implements Comparable{
    Resume resume;
    ArrayList<Consumer> socialGroup;

    public void add(Education education) { resume.education.add(education); }
    public void add(Experience experience) {
        resume.experience.add(experience);
    }
    public void add(Consumer consumer) {
        socialGroup.add(consumer);
    }
    public int getDegreeInFriendship(Consumer consumer){
        int degree = 1;
        HashMap<Consumer,Integer> map = new HashMap<>();
        ArrayList<Consumer> first = new ArrayList<>(socialGroup), second = new ArrayList<>();
        while(!first.isEmpty()) {
            for(Consumer c:first) {
                if(c.compareTo(consumer) == 0)
                    return degree;
                if(map.get(c) == null) {
                    map.put(c, 1);
                    second.addAll(c.socialGroup);
                }
            }
            degree++;
            first = second;
            second = new ArrayList<>();
        }
        return 0;
    }
    public void remove(Consumer consumer){
        socialGroup.remove(consumer);
    }
    public Integer getGraduationYear(){
        for(Education e: resume.education) {
            if(e.educationLevel == EducationLevel.college) {
                if(e.end == null)
                    return null;
                else
                    return e.end.year;
            }
        }
        return null;
    }
    public Double meanGPA(){
        double gpa = 0;
        for(Education e: resume.education) {
            gpa += e.finalGrade;
        }
        return gpa/resume.education.size();
    }
    public int compareTo(Object o){
        if(o instanceof Consumer) {
            Consumer aux = (Consumer) o;
            Information info1 = resume.information, info2 = aux.resume.information;
            return info1.compareTo(info2);
        } else
            throw new ClassCastException("Wrong object!");
    }

    public String toString() {
        String output = "" + resume + "\nSocial Group:";
        for(Consumer c:socialGroup) {
            output += c.resume.information.getName() + c.resume.information.getSurname() + "  ";
        }
        output += "\n";
        return output;
    }
    public String getFullName() {
        return resume.information.getName() + resume.information.getSurname();
    }
}
