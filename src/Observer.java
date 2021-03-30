import java.util.ArrayList;

public interface Observer {

    void update(ObserverNotification observerNotification);

    void cancelAllSubscriptions();

    void cancelSubscription(Subject subject);

    void addSubscription(Subject subject);
}
