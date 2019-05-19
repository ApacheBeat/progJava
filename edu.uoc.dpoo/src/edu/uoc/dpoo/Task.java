package edu.uoc.dpoo;

import java.util.Date;

/**
 * Task class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class Task {
	
	//Definition of attributes
    public Manager manager;
    private TaskType type;
    private Date createdAt;
    private Date updatedAt;
    private TaskStatus status;
    private String arguments;

    //Constructor method
    public Task(Manager manager, TaskType type, TaskStatus status, String arguments) {
        this.manager = manager;
        this.type = type;
        this.status = status;
        this.arguments = arguments;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
        
    /**
     * Methods SET and GET to access to the attributes Manager, Type, Status, arguments
     * and dates of creation and update in a public way
     */
    
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }    
    
    //Two unimplemented methods
    public void complete() {
    }

    public Boolean requiresExecution() {
        return null;
    }
}