package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class Service {
	
	//Definition of attributes	 
    private String name;
    private int minMem;
    private int maxMem;
    private int minDisk;
    private int maxDisk;
    public int instances;

    //List of running services
    public static List<RunningService> hosts;

    //Constructor method
    public Service(String name, int minMem, int maxMem, int minDisk, int maxDisk) {
        this.name = name;
        this.minMem = minMem;
        this.maxMem = maxMem;
        this.minDisk = minDisk;
        this.maxDisk = maxDisk;
        this.hosts = new ArrayList<RunningService>();
        this.instances = 0;
    }    

    /**
     * Methods SET and GET to access to the attributes name, minMem, maxMem, minDisk,
     * maxDisk and hosts in a public way
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinMem() {
        return minMem;
    }

    public void setMinMem(int minMem) {
        this.minMem = minMem;
    }

    public int getMaxMem() {
        return maxMem;
    }

    public void setMaxMem(int maxMem) {
        this.maxMem = maxMem;
    }

    public int getMinDisk() {
        return minDisk;
    }

    public void setMinDisk(int minDisk) {
        this.minDisk = minDisk;
    }

    public int getMaxDisk() {
        return maxDisk;
    }

    public void setMaxDisk(int maxDisk) {
        this.maxDisk = maxDisk;
    }

    public List<RunningService> getHosts() {
        return hosts;
    }

    public void setHosts(List<RunningService> hosts) {
        this.hosts = hosts;
    }    

    /**
     * Method to get the number of instances. This is the amount of times
     * a service is being run on a host
     * @return
     */
    public int getInstances() {
    	for(int i=0;i<getHosts().size();i++) {
    		if(getHosts().get(i).getService() == this) {
    			this.instances++;
    		}else {
    			this.instances = 0;
    		}
    	}
        return this.instances;
    }
}