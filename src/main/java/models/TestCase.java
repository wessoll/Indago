package models;

import java.util.Date;

/**
 *
 * @author wesley
 */
public class TestCase {
    
    public enum Timeframes{
        EVERY_HOUR(60000),
        EVERY_12_HOURS(720000),
        EVERY_24_HOURS(1440000);
        
        private long milliseconds;
        
        Timeframes(long milliseconds){
            this.milliseconds = milliseconds;
        }
        
        public long getMilliseconds(){
            return milliseconds;
        }
    }
    
    private String case_id;
    private String name;
    private boolean isActive, assertEquals;
    private String status;
    private Date lastTested;
    private Timeframes timeframe;
    private User owner;
    
    public TestCase(){
        
    }
    
    public TestCase(String case_id, String name, boolean isActive, Timeframes timeframe, User owner){
        this.case_id = case_id;
        this.name = name;
        this.isActive = isActive;
        this.timeframe = timeframe;
        this.owner = owner;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Whether or not this case should be executed by Selenium
     * @return 
     */
    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Whether or not the last test was successfull
     * @return 
     */
    public boolean isAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(boolean assertEquals) {
        this.assertEquals = assertEquals;
    }

    /**
     * Status message like succeeded or an exception when the test didn't succeeded
     * @return 
     */
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Date on which Selenium performed the last test
     * @return 
     */
    public Date getLastTested() {
        return lastTested;
    }

    public void setLastTested(Date lastTested) {
        this.lastTested = lastTested;
    }

    /**
     * Timeframe in which the scheduler needs to be active
     * @return 
     */
    public Timeframes getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(Timeframes timeframe) {
        this.timeframe = timeframe;
    }

    /**
     * The User that created the TestCase
     * @return 
     */
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}