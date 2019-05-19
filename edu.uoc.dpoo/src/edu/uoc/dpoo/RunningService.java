package edu.uoc.dpoo;

/**
 * RunningService Class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class RunningService {
	
	//Definition of attributes
    private Service service;
    private Host host;
    private int mem;
    private int disk;

    //Constructor method
    public RunningService(Service service, Host host, int mem, int disk) {
        this.service = service;
        this.host = host;
        this.mem = mem;
        this.disk = disk;
    }

    // Methods SET and GET to access to the attributes service, host, mem and disk in a public way
    
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public int getMem() {
        return mem;
    }

    public void setMem(int mem) {
        this.mem = mem;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }
}