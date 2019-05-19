package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Manager Class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class Manager {
   
	//Definition of attributes
    private List<Service> services = new ArrayList<Service>();
    private List<Host> hosts = new ArrayList<Host>();    
    private List<Task> tasks = new ArrayList<Task>();    
    private List<Message> messages = new ArrayList<Message>();
    private static List<EventListener> listeners = new ArrayList<EventListener>();
    
    //Constructor method
    public void Manager() {
       
    }
    
    /**
     * Method to get the list of Services
     * @return the list of services
     */
    public List<Service> getServices() {
        return services;
    }
    
    /**
     * Method to set a list of services
     * @param services - list of services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }
    
    /**
     * Method to get the list of hosts
     * @return the list of hosts
     */
    public List<Host> getHosts() {        
        return hosts;
    }
    
    /**
     * Method to set a list of hosts
     * @param hosts - list of hosts
     */
    public void setHosts(List<Host> hosts) {        
        this.hosts = hosts;
    }

    /**
     * Method to get a list of tasks
     * @return the list of tasks
     */
    public List<Task> getTasks() { 
        return tasks;
    }

    /**
     * Method to set the list of tasks
     * @param tasks - list of tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Method to get the list of messages
     * @return the list of messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Method to set a list of messages
     * @param messages - list of messages
     */
    public void setMessages(List<Message> messages) {
    	this.messages = messages;
    }

    /**
     * Method to add a new host to the list and inform the listeners
     * @param domain 
     * @param mem
     * @param disk
     * @throws TaskException
     */
    public void addHost(String domain, int mem, int disk) throws TaskException{
    	for(int i=0; i<hosts.size();i++) {
    		if (hosts.get(i).getDomain() == domain) {
    			throw new TaskException(TaskException.DUPLICATED_HOST);
    		}
    	} 	
    	if(getHost(domain)==null) {
    		Host newHost = new Host(domain, mem, disk);
    		this.hosts.add(newHost);
    	}else {

    	}
    	Host newHost = new Host(domain, mem, disk);
    	this.hosts.add(newHost);
    	for(EventListener l1 : listeners) {
    		l1.onNewHost(newHost);
    	}	
    }

    /**
     * Method to get a host given a certain domain
     * @param domain
     * @return the host in that domain
     */
    public Host getHost(String domain) {
    	Host h = new Host(null, 0, 0);
        for(int i = 0; i<this.hosts.size();i++) {
        	h = this.hosts.get(i);
            if(h.getDomain().equals(domain)) {
                return h;
            }else {}
        }
        return h;
    }
    
    /**
     * Method to remove a host from the list
     * @param host
     * @throws TaskException
     */
    public void removeHost(Host host) throws TaskException {
    	if (!hosts.contains(host)) {
    		throw new TaskException(TaskException.INVALID_HOST);
    	}else {
    		this.hosts.remove(host);
    	}	
    }

    /**
     * Method to add a service to the list
     * @param name
     * @param minMem
     * @param maxMem
     * @param minDisk
     * @param maxDisk
     * @throws TaskException
     */
    public void addService(String name, int minMem, int maxMem, int minDisk, int maxDisk) throws TaskException{
    	if(!(services.contains(getService(name)))) {
    		if(minMem>maxMem || minDisk>maxDisk) {
    			throw new TaskException(TaskException.INVALID_SERVICE_ARGS);
    		}else {
    			Service newService = new Service(name, minMem, maxMem, minDisk, maxDisk);
        		services.add(newService);
    		}
    	}else {
    		throw new TaskException(TaskException.DUPLICATED_SERVICE);
    	}
    }
    
    /**
     * Method to get a service given a name
     * @param name
     * @return the service with that name
     */
    public Service getService(String name) {
        for(Service s : services) {
            if(s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Method to remove a service from the list
     * @param service
     * @throws TaskException
     */
    public void removeService(Service service) throws TaskException{
        if(!services.contains(service)) {
        	throw new TaskException(TaskException.INVALID_SERVICE);
        }else {
            services.remove(service);
        }    
    }    
    
    /**
     * Method to send a message, to add it to the list and inform the listeners
     * @param subject
     * @param content
     */
    public void sendMessage(String subject, String content) {
    	Message message = new Message(subject, content);
        this.messages.add(message);
        for(EventListener l1 : listeners) {
        	l1.onNewMessage(message);
        }
    }

    /**
     * Method to get a message
     * @return
     */
    public Message getMessage() {
        return null;
    }
    
    /**
     * Method to add a task to the list
     * @param task
     */
    public void addTask(Task task) {
    	task.setStatus(TaskStatus.PENDING);
    	this.tasks.add(task);
    }

    /**
     * Method to return the oldest task and change his status to RUNNING
     * @return the task to be executed
     */
    public Task getTask() {
    	Task oldestTask = new Task(this, null, TaskStatus.FAILED, null);
    	oldestTask.setCreatedAt(new java.util.Date());
    	if(tasks.isEmpty()) {
    		oldestTask = null;
    	}else {
    		for(int i=0; i<tasks.size(); i++) {
    			if (tasks.get(i).getCreatedAt().before(oldestTask.getCreatedAt()) && 
    					tasks.get(i).getStatus() == TaskStatus.PENDING) {
    				oldestTask = tasks.get(i);
    			}else {
    			}
    		}
    		oldestTask.setStatus(TaskStatus.RUNNING);
    		oldestTask.setUpdatedAt(new java.util.Date());
    	}	
    	return oldestTask;
    }

    /**
     * Method to store the state of host in the relation between Host and Service 
     * @param host
     * @param service
     * @param mem
     * @param disk
     * @throws TaskException
     */
    public void storeStats(Host host, Service service, int mem, int disk) throws TaskException{
    	if(!services.contains(service)) {
    		throw new TaskException(TaskException.INVALID_SERVICE);
    	}else if (!hosts.contains(host)){
    		throw new TaskException(TaskException.INVALID_HOST);    		
    	}else{
    		
    		for(int i=0;i<Host.services.size();i++) {
        		if(Host.services.get(i).getHost() == host && Service.hosts.get(i).getService() == service) {
        			Host.services.get(i).setDisk(disk);
            		Host.services.get(i).setMem(mem); 
            		Service.hosts.get(i).setDisk(disk);
            		Service.hosts.get(i).setMem(mem);
        		} else {}	
        	}
    		
    		for(EventListener l1 : listeners) {
        		l1.onResourcesAlert();
        	}
    		System.out.println("Final3");
        	if(host.getUsedMemory()/(host.getFreeMemory()+host.getUsedMemory()) > 0.9) {
        		sendMessage("MEM ALERT", "Server " + host.getDomain() + " is using more than 90% of the memory");
        	}else if(host.getUsedDisk()/(host.getFreeDisk()+host.getUsedDisk()) > 0.9) {
        		sendMessage("DISK ALERT", "Server " + host.getDomain() + " is using more than 90% of the disk");
        	}
    	}
    }

    /**
     * Method to start a service 
     * @param service to be started
     * @throws TaskException
     */
    public void startService(Service service) throws TaskException{
    	if (!services.contains(service)) {
    		throw new TaskException(TaskException.INVALID_SERVICE);
    	}else if(hosts.size() == 0){
    		throw new TaskException(TaskException.NO_HOST_DEFINED);
    	}else {
    		Task newTask = new Task(this, TaskType.DEPLOY, TaskStatus.PENDING, "serviceName=" + service.getName());
    		service.instances ++;
    		addTask(newTask);
    	}
    }

    /**
     * Method to stop a service
     * @param service to be stopped
     * @throws TaskException
     */
    public void stopService(Service service) throws TaskException{
    	if(service.getHosts()==null) {
    		throw new TaskException(TaskException.NO_HOST_DEFINED);
    	}else if (!services.contains(service)) {
    		throw new TaskException(TaskException.INVALID_SERVICE);
    	}else {
    		Task newTask = new Task(this, TaskType.UNDEPLOY, TaskStatus.PENDING, "serviceName=" + service.getName());
    		addTask(newTask);
    	}
    }

    /**
     * Method to get the services running on a host
     * @param host where to look
     * @return the list of running services in that host
     */
    public List<RunningService> getHostServices(Host host) {
        return host.getServices();
    }

    /**
     * Method to get the running services
     * @return the running services
     */
    public List<RunningService> getRunningServices() {
    	List<RunningService> runningServices = new ArrayList<RunningService>();
    	for(int i = 0; i<hosts.size(); i++) {
    		runningServices = hosts.get(i).getServices();
    	}
    	return runningServices;
    }

    /**
     * Method to scale a service instance times
     * @param service to be scaled
     * @param instances 
     * @throws TaskException
     */
    public void scaleService(Service service, int instances) throws TaskException {
    	if(instances<0 || instances == 0) {
    		throw new TaskException(TaskException.INVALID_INSTANCES);
    	}else {
    		if(!services.contains(service)) {
    			throw new TaskException(TaskException.INVALID_SERVICE);
    		}else {  		
    			for(int i=0; i<(instances-1); i++) {
    				String arguments = "serviceName=" + service.getName() + ";instances=" + instances;
    				Task newTask = new Task(this, TaskType.SCALE, TaskStatus.PENDING, arguments);
    				addTask(newTask);
    			}
    		}
    	}
    } 
    
    /**
     * Method to add a listener to the list of listeners/observers
     * @param listener to be added
     */
    public void addListener(EventListener listener) {
    	listeners.add(listener);
    }

}