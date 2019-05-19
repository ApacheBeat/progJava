package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.List;

/**
 * Host class 
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class Host {

	//Definition of attributes
    private String domain;
    private int memory;
    private int disk;

    //List to store the running services
    public static List<RunningService> services;
   
    //Constructor method 
    public Host(String domain, int memory, int disk) {
        this.domain = domain;
        this.memory = memory;
        this.disk = disk;        
        services = new ArrayList<RunningService>();
    }
    
    /**
     * Methods SET and GET to access to the attributes domain, memory,
     * disk and services in a public way 
     */
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }
        
    
    //Methods to get the amounts of free and used memory and disk
    public int getUsedMemory() {
    	int totalUsedMemory = 0;
    	for (int i = 0; i<services.size();i++) {
    		totalUsedMemory += services.get(i).getMem();
    	}
        return totalUsedMemory;
    }

    public int getUsedDisk() {
    	int totalUsedDisk = 0;
    	for(int i = 0; i<services.size(); i++) {
    		totalUsedDisk += services.get(i).getDisk();
    	}
        return totalUsedDisk;
    }

    public int getFreeMemory() {
    	int totalFreeMemory = memory;
    	totalFreeMemory -= getUsedMemory();
    	return totalFreeMemory;
    }

    public int getFreeDisk() {
    	int totalFreeDisk = disk;
    	totalFreeDisk -= getUsedDisk();
    	return totalFreeDisk;
    }
    
    /**
     * Method to get the list of running services in this host
     * @return the list of running services
     */
    public List<RunningService> getServices() {
        return services;
    }

    /**
     * Method to set the list of the running services in this host
     * @param services - list with the running services
     */
    public void setServices(List<RunningService> services) {
        this.services = services;
    } 
}