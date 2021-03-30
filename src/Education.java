enum EducationLevel{
    highschool,
    college,
    masters,
    doctorate;

    public static EducationLevel getLevel(String level) {
        return switch (level) {
            case "highschool" -> highschool;
            case "college" -> college;
            case "masters" -> masters;
            case "doctorate" -> doctorate;
            default -> null;
        };
    }
}
public class Education implements Comparable{
    Date begin, end;
    String educationInstitute;
    EducationLevel educationLevel;
    Float finalGrade;

    public Education(Date begin, Date end, String institute, EducationLevel level, Float finalGrade) {
        this.begin = begin;
        this.end = end;
        educationInstitute = institute;
        educationLevel = level;
        this.finalGrade = finalGrade;
        if(end != null && begin.compareTo(end) > 0) {
            throw new InvalidDatesException("Dates " + begin + " and " + end +" are invalid!");
        }
    }

    public int compareTo(Object o) {
        if(o instanceof Education) {
            Education aux = (Education) o;
            int out;
            if(aux.end != null && end != null) {
                out = -end.compareTo(aux.end);
                if(out == 0) {
                    float fOut = aux.finalGrade - finalGrade;
                    if(fOut == 0)
                        return 0;

                    if(fOut > 0)
                        out = 1;
                    else
                        out = -1;
                }
            } else {
                if(aux.end == null){
                    if(end == null)
                        out = begin.compareTo(aux.begin);
                    else
                        //the second education is not finished, but the first is
                        return 1;
                } else
                    //the first education is not finished, but the second is
                    return -1;

            }
            //if education seems identical, the names of the institutes are compared
            if(out == 0)
                return educationInstitute.compareTo(aux.educationInstitute);
            else
                return out;
        } else
        throw new ClassCastException("Wrong object");
    }

    public String toString() {
        return "{educationInstitute='" + educationInstitute +
                ", educationLevel=" + educationLevel +
                ", begin=" + begin +
                ", end=" + end +
                ", finalGrade=" + finalGrade +
                "}";
    }
}
