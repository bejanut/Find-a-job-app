public class ObserverNotification {
    private String subject, message;
    private Object carry;

    public ObserverNotification(String subject, String message, Object carry) {
        this.subject = subject;
        this.message = message;
        this.carry = carry;
    }

    public ObserverNotification(String subject, String message) {
        this(subject, message, null);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCarry() {
        return carry;
    }

    public void setCarry(Object carry) {
        this.carry = carry;
    }
}
