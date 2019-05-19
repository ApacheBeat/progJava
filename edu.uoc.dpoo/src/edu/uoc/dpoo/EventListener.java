package edu.uoc.dpoo;

/**
 * EventListener Interface
 * @author Victor Alonso Garrigos
 * DPOO
 */
public interface EventListener {
    public void onNewHost(Host host);
    public void onServiceStarted(Service service);
    public void onNewMessage(Message message);
    public void onResourcesAlert();
}