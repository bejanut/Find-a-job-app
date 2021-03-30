import java.time.LocalDate;

public class Date implements Comparable{
    int day, month, year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        if(day < 1 || day > 31)
            throw new RuntimeException(""+ day + " is not a valid day");
        if(month < 1 || month > 12)
            throw new RuntimeException(""+ month + " is not a valid month");
    }
    //constructorul fara parametru returneaza data curentă
    public Date() {
        this(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),LocalDate.now().getYear());
    }

    public int compareTo(Object o) {
        if(o == null)
            throw new NullPointerException("Object is null");
        if(o instanceof Date) {
            Date date = (Date) o;
            int out = year - date.year;
            if(out == 0)
                out = month - date.month;
            if(out == 0)
                out = day - date.day;
            return out;
        }
        throw new ClassCastException("Wrong object");
    }
    public int numberOfDaysUntilDate(Date date){
        if(date == null)
            date = new Date();
        int first, second;
        first = day + month * 31 + year * 372;
        second = date.day + date.month * 31 + date.year * 372;

        return second - first;
    }
    public int yearsBetween(Date date) {
        int years = numberOfDaysUntilDate(date)/372;
        if(numberOfDaysUntilDate(date) % 372 != 0)
            years ++;
        return years;
    }
    public String toString() {
        return "" + day + "/" + month + "/" + year;
    }

    public static Date curentDate() {
        return new Date();
    }
}
