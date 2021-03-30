public class Experience implements Comparable{
    Date begin, end;
    String position;
    String company;
    String department;

    public Experience(Date begin, Date end, String position, String company) {
        this(begin, end, position, company, null);
    }

    public Experience(Date begin, Date end, String position, String company, String department) {
        this.begin = begin;
        this.end = end;
        this.position = position;
        this.company = company;
        this.department = department;

        if(end != null && begin.compareTo(end) > 0)
            throw new InvalidDatesException("Dates " + begin + " and " + end +" are invalid!");
    }

    public int compareTo(Object o) {
        if(o instanceof Experience) {
            Experience aux = (Experience) o;
            int out;
            if(aux.end != null && end != null) {
                out = -end.compareTo(aux.end);
                if(out == 0) {
                    out = company.compareTo(aux.company);
                    if(out == 0)
                        out = begin.compareTo(aux.begin);
                }
            } else {
                if(aux.end == null){
                    if(end == null)
                        out = company.compareTo(aux.company);
                    else
                        //the second job is not finished, but the first is
                        return 1;
                } else
                    //the first job is not finished, but the second is
                    return -1;
            }
            return out;
        } else
            throw new ClassCastException("Wrong object");
    }

    public String toString() {
        return "{Position " +  position + " company: " + company +
                " begin " + begin + " end " + end + '}';
    }
}
