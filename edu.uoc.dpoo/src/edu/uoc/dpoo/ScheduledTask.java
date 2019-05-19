package edu.uoc.dpoo;

import java.util.Date;

/**
 * ScheduledTask Class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class ScheduledTask extends Task {
	
	//Definition of attributes
    private int period;
    private Date endDate;
    
    //Constructor method
    public ScheduledTask(Manager manager, TaskType type, TaskStatus status, String arguments, int period, Date endDate) {
        super(manager, type, status, arguments);
        this.period = period;
        this.endDate = endDate;
    }

    /**
     * Methods SET and GET for the attributes period and end date in a public way
     */
    
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }    
}