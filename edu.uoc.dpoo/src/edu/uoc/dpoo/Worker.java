package edu.uoc.dpoo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Worker Class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class Worker implements EventListener{

	//Definition of attributes
	private Manager manager;

	//Auxiliary task to store the one get with getTask()
	Task newTask = new Task(null, null, null, null);

	//Constructor method
	public Worker(Manager manager) {
		this.manager = manager;
	}

	//Run method, executes different methods depending on the task
	public void run() {
		Service service = new Service(null, 0, 0, 0, 0);
		newTask = this.manager.getTask();
		if(newTask.getStatus() == TaskStatus.RUNNING) {
			try {
				treatRunning(newTask);	
			}catch (Exception e) {}	
		}else if(newTask.getType() == TaskType.DEPLOY) {
			try {
				treatDeploy(newTask, service);
			}catch (Exception e) {}
		}else if(newTask.getType() == TaskType.UNDEPLOY) {
			try {
				treatUndeploy(newTask, service);
			}catch(Exception e){}
		}else if(newTask.getType() == TaskType.SCALE) {
			try {
				treatScale(newTask, service);
			}catch(Exception e) {}
		}
		newTask.setStatus(TaskStatus.DONE);
		newTask.setUpdatedAt(new java.util.Date());
	}    

	/**
	 * Method to treat the tasks with RUNNING status
	 * @param newTask
	 * @throws TaskException
	 */
	public void treatRunning(Task newTask) throws TaskException {
		newTask.setStatus(TaskStatus.FAILED);
		throw new TaskException(TaskException.DUPLICATED_SERVICE);
	}

	/**
	 * Method to treat the tasks of DEPLOY type
	 * @param newTask
	 * @param service
	 * @throws TaskException
	 */
	public void treatDeploy(Task newTask, Service service) throws TaskException{	
		try {
			int sufi = 0;
			for(int i = 0; i<this.manager.getHosts().size(); i++) {
				if(this.manager.getHosts().get(i).getServices().get(i).getService() == service) {
					throw new TaskException(TaskException.DUPLICATED_SERVICE);
				}
				if (this.manager.getHosts().get(i).getDisk()>service.getMaxDisk() && 
						this.manager.getHosts().get(i).getMemory()>service.getMaxMem() && sufi==0) {
					this.manager.startService(service);
					service.instances++;
					this.manager.getServices().add(service);
					this.manager.getHosts().add(this.manager.getHosts().get(i));
					sufi = 1;
				}else {
					sufi = 0;
				}
			}
			if(sufi == 1) {

			}else if (sufi == 0) {
				newTask.setStatus(TaskStatus.FAILED);
				throw new TaskException(TaskException.INSUFICIENT_RESOURCES);
			}
		}catch (Exception e) {
			if(newTask.getArguments() == null) {
				newTask.setStatus(TaskStatus.FAILED);
				newTask.setUpdatedAt(new java.util.Date());
				manager.sendMessage("ERROR", e.getMessage());
				throw new TaskException(TaskException.INVALID_TASK_ARGUMENTS);	
			}
		}
	}

	/**
	 * Method to treat the tasks of UNDEPLOY type
	 * @param newTask
	 * @param service
	 * @throws TaskException
	 */
	public void treatUndeploy(Task newTask, Service service) throws TaskException{
		for(int i = 0 ; i<this.manager.getHosts().size(); i++) {
			try {
				if(this.manager.getHosts().get(i).getServices().get(i).getService() == service) {
					this.manager.removeService(service);
					newTask.setStatus(TaskStatus.DONE);
					newTask.setUpdatedAt(new java.util.Date());
				}else if(newTask.getArguments() == null) {
					newTask.setStatus(TaskStatus.FAILED);
					newTask.setUpdatedAt(new java.util.Date());
					throw new TaskException(TaskException.INVALID_TASK_ARGUMENTS);
				}else {
					newTask.setStatus(TaskStatus.FAILED);
					newTask.setUpdatedAt(new java.util.Date());
					throw new TaskException(TaskException.INVALID_SERVICE);
				}
			}catch(Exception e) {
				manager.sendMessage("ERROR: ", e.getMessage());
			}
		}
	}
	
	/**
	 * Method to treat the tasks of SCALE type
	 * @param newTask
	 * @param service
	 */
	public void treatScale(Task newTask, Service service) {
		for(int i = 0 ; i<this.manager.getHosts().size(); i++) {
			try {
				if(!(this.manager.getHosts().get(i).getMemory() > service.getMaxMem())) {
					newTask.setStatus(TaskStatus.FAILED);
					newTask.setUpdatedAt(new java.util.Date());
					throw new TaskException(TaskException.INSUFICIENT_RESOURCES);
				}else if(newTask.getArguments() == null) {
					newTask.setStatus(TaskStatus.FAILED);
					newTask.setUpdatedAt(new java.util.Date());
					throw new TaskException(TaskException.INVALID_TASK_ARGUMENTS);
				}else if(!(manager.getServices().contains(service.getHosts().get(i).getService()))){
					newTask.setStatus(TaskStatus.FAILED);
					newTask.setUpdatedAt(new java.util.Date());
					throw new TaskException(TaskException.INVALID_SERVICE);
				}else {
					int instances = service.getInstances();
					manager.scaleService(service, instances);
				}
			}catch(Exception e) {
				manager.sendMessage("ERROR: ", e.getMessage());
			}
		}
	}
	
	//Overridden method to send a message to listeners when a host is created
	public void onNewHost(Host host) {
		System.out.println(host);
	}

	//Overridden method to send a message to listeners when a service is started
	public void onServiceStarted(Service service) {
		System.out.println(service);		
	}

	//Overridden method to send a message to listeners when a message is sent
	public void onNewMessage(Message message) {
		System.out.println(message);
	}

	//Overridden method to send a message to listeners when there is a problem of resources (memory or disk)
	public void onResourcesAlert() {
		System.out.println("Resources problem alert");
	}
}