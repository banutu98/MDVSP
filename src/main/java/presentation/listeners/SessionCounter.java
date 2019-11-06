package presentation.listeners;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounter implements HttpSessionListener {

    public static int activeSessions;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        activeSessions++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        activeSessions--;
    }
}
