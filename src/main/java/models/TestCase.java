package models;

import java.sql.Date;

/**
 *
 * @author wesley
 */
public class TestCase {
    
    private String case_id;
    private String name;
    private boolean isActive, assertEquals;
    private String status;
    private Date lastTested;
    
    public TestCase(){
        
    }
    
    public TestCase(String case_id, String name, boolean isActive){
        this.case_id = case_id;
        this.name = name;
        this.isActive = isActive;
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
     * Date on when Selenium performed the last test
     * @return 
     */
    public Date getLastTested() {
        return lastTested;
    }

    public void setLastTested(Date lastTested) {
        this.lastTested = lastTested;
    }
}