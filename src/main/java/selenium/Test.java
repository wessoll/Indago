package selenium;

import daos.ActionClearDAO;
import daos.ActionClickDAO;
import daos.ActionDAO;
import daos.ActionGetURLDAO;
import daos.ActionSendKeysDAO;
import java.util.List;
import models.Action;
import models.ActionClear;
import models.ActionClick;
import models.ActionGetURL;
import models.ActionSendKeys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author wesley
 */
public class Test {

    private static WebDriver driver;

    public static void main(String[] args) {

        /**
         * Get the actions related to the case
         */
        ActionDAO actionDAO = new ActionDAO();
        List<Action> actions = actionDAO.findAll();

        boolean assertEquals = true; // becomes false when we encounter an error
        String status = "Case succeeded";
        
        driver = new HtmlUnitDriver(); // start the driver
        
        outerloop:
        for (Action action : actions) { // for every action
//            switch (action.getActionType()) {
//                case GET_URL:
//                    // retrieve action from db
//                    ActionGetURLDAO actionGetURLDAO = new ActionGetURLDAO();
//                    ActionGetURL actionGetURL = actionGetURLDAO.find(action.getAction_id());
//                    
//                    // perform action
//                    System.out.println("[geturl] " + actionGetURL.getValue());
//                    try{
//                        driver.get(actionGetURL.getValue());
//                    }
//                    catch(Exception e){
//                        assertEquals = false;
//                        System.out.println("[geturl] " + actionGetURL.getValue());
//                        break outerloop;
//                    }
//                    
//                    break;
//                case CLICK:
//                    // retrieve action from db
//                    ActionClickDAO actionClickDAO = new ActionClickDAO();
//                    ActionClick actionClick = actionClickDAO.find(action.getAction_id());
//                    
//                    // perform action
//                    System.out.println("[click] " + actionClick.getElementId());
//                    try{
//                        driver.findElement(By.id(actionClick.getElementId())).click();
//                    }
//                    catch (org.openqa.selenium.NoSuchElementException e){ // element not found
//                        try{
//                            // try locating by name instead of id
//                            driver.findElement(By.name(actionClick.getElementId())).click();
//                        }
//                        catch(Exception f){
//                            assertEquals = false;
//                            status = "[click:" + actionClick.getElementId() + "] - " + f.toString();
//                            break outerloop;
//                        }
//                    }
//
//                    break;
//                case SEND_KEYS:
//                    // retrieve action from db
//                    ActionSendKeysDAO actionSendKeysDAO = new ActionSendKeysDAO();
//                    ActionSendKeys actionSendKeys = actionSendKeysDAO.find(action.getAction_id());
//
//                    // perform action
//                    System.out.println("[sendkeys] " + actionSendKeys.getElementId() + ":" + actionSendKeys.getValue());
//                    try{
//                        WebElement element = driver.findElement(By.id(actionSendKeys.getElementId()));
//                        element.click();
//                        element.sendKeys(actionSendKeys.getValue());
//                    }
//                    catch (org.openqa.selenium.NoSuchElementException e){ // element not found
//                        try{
//                            // try locating by name instead of id
//                            WebElement element = driver.findElement(By.name(actionSendKeys.getElementId()));
//                            element.click();
//                            element.sendKeys(actionSendKeys.getValue());
//                        }
//                        catch(Exception f){
//                            assertEquals = false;
//                            status = "[sendkeys:" + actionSendKeys.getElementId() + ":" + actionSendKeys.getValue() + "] - " + f.toString();
//                            break outerloop;
//                        }
//                    }
//                    break;
//                case CLEAR:
//                    // retrieve action from db
//                    ActionClearDAO actionClearDAO = new ActionClearDAO();
//                    ActionClear actionClear = actionClearDAO.find(action.getAction_id());
//                    
//                    // perform action
//                    System.out.println("[clear] " + actionClear.getElementId());
//                    try{
//                        driver.findElement(By.id(actionClear.getElementId())).click();
//                    }
//                    catch (org.openqa.selenium.NoSuchElementException e){ // element not found
//                        try{
//                            // try locating by name instead of id
//                            driver.findElement(By.name(actionClear.getElementId())).click();
//                        }
//                        catch(Exception f){
//                            assertEquals = false;
//                            status = "[clear:" + actionClear.getElementId() + "] - " + f.toString();
//                            break outerloop;
//                        }
//                    }
//
//                    break;
//                default:
//                    System.err.println("No action implemented yet");
//            }
//
//        }
//        driver.close(); // close the driver
//        
//        // update the case
//        
        }
    }

}
