package presentation.backingBeans;

import presentation.listeners.SessionCounter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "footerBean")
@ViewScoped
public class FooterBean {

    public int extractSessionCount() {
        return SessionCounter.activeSessions;
    }
}
