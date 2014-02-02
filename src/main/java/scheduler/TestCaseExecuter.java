package scheduler;

import connectivity.Mail;
import daos.ActionClearDAO;
import daos.ActionClickDAO;
import daos.ActionContainsTextDAO;
import daos.ActionDAO;
import daos.ActionGetURLDAO;
import daos.ActionSendKeysDAO;
import daos.SchedulerDAO;
import daos.TestCaseDAO;
import daos.UserDAO;
import java.util.Date;
import java.util.List;
import models.Action;
import models.ActionClear;
import models.ActionClick;
import models.ActionContainsText;
import models.ActionGetURL;
import models.ActionSendKeys;
import models.Element;
import models.TestCase;
import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author wesley
 */
public class TestCaseExecuter implements Job{

    private static WebDriver driver;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        
        // update the status of the scheduler
        SchedulerDAO schedulerDAO = new SchedulerDAO();
        models.Scheduler scheduler = schedulerDAO.find();
        
        if (scheduler == null){ // create new scheduler object
            schedulerDAO.insert(new models.Scheduler(new Date()));
        }
        else{ // update
            scheduler.setLastHeartbeat(new Date());
            schedulerDAO.update(scheduler);
        }

        /**
         * Check for every testcase that is active
         */
        TestCaseDAO testCaseDAO = new TestCaseDAO();
        List<TestCase> testCases = testCaseDAO.findByIsActive();
        
        // filter testcases which need to be tested (according to their timeframe)
        for (int i=0;i<testCases.size();i++){
            Date lastTested = testCases.get(0).getLastTested();
            long timeframe = testCases.get(0).getTimeframe().getMilliseconds();
            Date now = new Date();
            long timeDifference = now.getTime()-lastTested.getTime();
            
            // remove testcase from list if the timeframe is not reached yet
            if (timeDifference > timeframe){
                testCases.remove(testCases.get(i));
            }
            else{
                // get and set all the details of the owner for the testcase (we only have userId)
                UserDAO userDAO = new UserDAO();
                String userId = testCases.get(i).getOwner().getUserId();
                User user = userDAO.findByUserId(userId);
                
                testCases.get(i).setOwner(user);
            }
        }
        
        for (TestCase testCase : testCases) {
            System.out.println("[started testcase, name: " + testCase.getName() + "]");
            // Get the actions related to the testcase
            ActionDAO actionDAO = new ActionDAO();
            List<Action> actions = actionDAO.findByCaseId(testCase.getCase_id());

            boolean assertEquals = true; // becomes false when we encounter an error
            String status = "Case succeeded";

            driver = new HtmlUnitDriver(); // start the driver

            outerloop:
            for (Action action : actions) { // for every action
                switch (action.getActionType()) {
                    case GET_URL:
                        // retrieve action from db
                        ActionGetURLDAO actionGetURLDAO = new ActionGetURLDAO();
                        ActionGetURL actionGetURL = actionGetURLDAO.findByActionId(action.getAction_id()).get(0);

                        // perform action
                        System.out.print("["
                                + "action: " + action.getActionType()
                                + ", url: " + actionGetURL.getUrl()
                                + "]");
                        try {
                            driver.get(actionGetURL.getUrl());
                            System.out.println("[OK]");
                        } catch (Exception e) {
                            System.out.println("[ERROR]");
                            assertEquals = false;
                            status = e.toString();
                            break outerloop;
                        }

                        break;
                    case CLICK:
                        // retrieve action from db
                        ActionClickDAO actionClickDAO = new ActionClickDAO();
                        ActionClick actionClick = actionClickDAO.findByActionId(action.getAction_id()).get(0);

                        // perform action
                        System.out.print("["
                                + "action: " + action.getActionType()
                                + ", elementType: " + actionClick.getElement().getElementType()
                                + ", elementPath: " + actionClick.getElement().getPath()
                                + "]");
                        try {
                            findElement(actionClick.getElement()).click();
                            System.out.println("[OK]");
                        } catch (org.openqa.selenium.NoSuchElementException e) {
                            System.out.println("[ERROR]");
                            assertEquals = false;
                            status = e.toString();
                            break outerloop;
                        }

                        break;
                    case SEND_KEYS:
                        // retrieve action from db
                        ActionSendKeysDAO actionSendKeysDAO = new ActionSendKeysDAO();
                        ActionSendKeys actionSendKeys = actionSendKeysDAO.findByActionId(action.getAction_id()).get(0);

                        // perform action
                        System.out.print("["
                                + "action: " + action.getActionType()
                                + ", elementType: " + actionSendKeys.getElement().getElementType()
                                + ", elementPath: " + actionSendKeys.getElement().getPath()
                                + ", value: " + actionSendKeys.getValue()
                                + "]");
                        try {
                            WebElement element = findElement(actionSendKeys.getElement());

                            element.click();
                            element.clear();
                            element.sendKeys(actionSendKeys.getValue());
                            System.out.println("[OK]");
                        } catch (org.openqa.selenium.NoSuchElementException e) {
                            System.out.println("[ERROR]");
                            assertEquals = false;
                            status = e.toString();
                            break outerloop;
                        }
                        break;
                    case CLEAR:
                        // retrieve action from db
                        ActionClearDAO actionClearDAO = new ActionClearDAO();
                        ActionClear actionClear = actionClearDAO.findByActionId(action.getAction_id()).get(0);

                        // perform action
                        System.out.print("["
                                + "action: " + action.getActionType()
                                + ", elementType: " + actionClear.getElement().getElementType()
                                + ", elementPath: " + actionClear.getElement().getPath()
                                + "]");
                        try {
                            findElement(actionClear.getElement()).clear();
                            System.out.println("[OK]");
                        } catch (org.openqa.selenium.NoSuchElementException e) {
                            System.out.println("[ERROR]");
                            assertEquals = false;
                            status = e.toString();
                            break outerloop;
                        }

                        break;
                    case CONTAINS_TEXT:
                        // retrieve action from db
                        ActionContainsTextDAO actionContainsTextDAO = new ActionContainsTextDAO();
                        ActionContainsText actionContainsText = actionContainsTextDAO.findByActionId(action.getAction_id()).get(0);

                        // perform action
                        System.out.print("["
                                + "action: " + action.getActionType()
                                + ", value: " + actionContainsText.getValue()
                                + ", assertEquals: " + actionContainsText.isAssertEquals()
                                + "]");
                        try {
                            String pageSource = driver.getPageSource();
                            
                            if (actionContainsText.isAssertEquals() && 
                                    !pageSource.contains(actionContainsText.getValue())){
                                throw new Exception("Page does not contains value");
                            }
                            else if (!actionContainsText.isAssertEquals() &&
                                    pageSource.contains(actionContainsText.getValue())){
                                throw new Exception("Page contains value");
                            }
                            else{
                                // ok
                                System.out.println("[OK]");
                            }
                            
                        } catch (Exception e) {
                            System.out.println("[ERROR]");
                            assertEquals = false;
                            status = e.toString();
                            break outerloop;
                        }

                        break;

                    default:
                        System.err.println("No action implemented yet");
                }

            }
            driver.close(); // close the driver

            // update the case
            testCase.setStatus(status);
            testCase.setLastTested(new Date(System.currentTimeMillis()));
            
            // deactivate testcase and inform the owner when the conditions were meet
            if (status.equals("Case succeeded")){
                testCase.setIsActive(false);
                
                // notify the owner of the testcase that it was meet
                Mail mail = new Mail(testCase.getOwner());
                mail.sendMail("Indago | " + testCase.getName(), "" 
                        + "Dear " + testCase.getOwner().getFirstname() + ","
                        + "\n\nThis message was sent to inform you that the following case met the conditions: "
                        + "\n\n"
                        + "TestCase: " + testCase.getName()
                        + "\nConditions met on: " + testCase.getLastTested()
                        + "\n\n"
                        + "This TestCase is now set to inactive. You can reactivate it by logging in again."
                        + "\n\n"
                        + "Thank you for using Indago"
                );
            }
            testCaseDAO.update(testCase);
        }
    }
    
    private static WebElement findElement(Element element){
        switch(element.getElementType()){
            case ID:
                return driver.findElement(By.id(element.getPath()));
            case NAME:
                return driver.findElement(By.name(element.getPath()));
            case LINK_TEXT:
                return driver.findElement(By.linkText(element.getPath()));
            default:
                return null;
        }
    }

}
