import java.util.ArrayList;

enum Sex{
    Male,
    Female
}
public class Information implements Comparable{
    private String name, surname, email, phone;
    private Date birthday;
    private ArrayList<Language> languages;
    private Sex sex;

    public Information(String name, String surname, String email, String phone, Date birthday,
                       ArrayList<Language> languages, Sex sex) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.languages = languages;
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int compareTo(Object o) {
        int out = 1;
        Information aux = (Information) o;
        if(name.equals(aux.name) && surname.equals(aux.surname)
                && sex == aux.sex && email.equals(aux.email) && phone.equals(aux.phone))
            out = 0;
        return out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void addLanguage(Language newLanguage) {
        this.languages.add(newLanguage);
    }

    public void removeLanguage(Language removedLanguage) {
        this.languages.remove(removedLanguage);
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String toString() {
        return "Name:" + name +
                " Surname:" + surname +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", languages:" + languages +
                ", sex:" + sex + "\n";
    }
}

class Language {
    String languageName, languageLevel;

    public Language(String languageName, String languageLevel) {
        this.languageName = languageName;
        this.languageLevel = languageLevel;
    }

    public Language() {
        this("romana", "Advanced");
    }

    public String toString() {
        return " " + languageName + "-" + languageLevel + " ";
    }
}
