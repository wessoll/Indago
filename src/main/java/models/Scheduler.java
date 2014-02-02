package models;

import java.util.Date;

/**
 *
 * @author wesley
 * 
 * Simple class for the Scheduler to log if it still active and running. Should be only 1 of.
 */
public class Scheduler {
    
    private String scheduler_id;
    private Date lastHeartbeat;
    
    public Scheduler(){
        
    }
    
    public Scheduler(Date lastHeartBeat){
        this.lastHeartbeat = lastHeartBeat;
    }

    public Date getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(Date lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public String getScheduler_id() {
        return scheduler_id;
    }

    public void setScheduler_id(String scheduler_id) {
        this.scheduler_id = scheduler_id;
    }
    
}