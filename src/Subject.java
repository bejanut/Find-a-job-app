import javax.management.Notification;
import java.util.ArrayList;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyAllObservers(ObserverNotification notification);
}
