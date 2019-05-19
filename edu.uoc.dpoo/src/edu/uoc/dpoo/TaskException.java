package edu.uoc.dpoo;

public class TaskException extends Exception {
    // Predefined errors
    // Without parameters
    public static final String NO_HOST_DEFINED = "There are no hosts defined in the manager";
    public static final String INVALID_INSTANCES = "Instances value must be a positive";
    public static final String INVALID_TASK_ARGUMENTS = "Provided task arguments are null or invalid";
        
    // With parameters
    public static final String INVALID_HOST = "Host with domain %s does not exist in the manager";
    public static final String INVALID_SERVICE = "Service with name %s does not exist in the manager";
    public static final String DUPLICATED_HOST = "Host with domain %s already exists in the manager";
    public static final String DUPLICATED_SERVICE = "Service with name %s already exists in the manager";
    public static final String INVALID_SERVICE_ARGS = "Invalid arguments for service with name %s";
    public static final String INSUFICIENT_RESOURCES = "Insuficient resources to deploy service with name %s";
    public static final String SERVICE_NOT_RUNNING = "The service with name %s is not running on host with domain %s";
 
    /**
     * Constructor method for a task exception
     * @param message
     */
    public TaskException (String message){
        super(message);
    }
}
