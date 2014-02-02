package controllers;

import daos.ActionClearDAO;
import daos.ActionClickDAO;
import daos.ActionContainsTextDAO;
import daos.ActionDAO;
import daos.ActionGetURLDAO;
import daos.ActionSendKeysDAO;
import daos.SchedulerDAO;
import daos.TestCaseDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Action;
import models.ActionClear;
import models.ActionClick;
import models.ActionContainsText;
import models.ActionGetURL;
import models.ActionSendKeys;
import models.TestCase;
import models.Element;
import models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author wesley
 */
public class TestCaseController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the action
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        
        // list test cases
        if (action.equals("listTestCases")){
            setTestCasesOnRequest(request);
            setMetaDataOnRequest(request);
            redirect(request, response, "testcases.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the action
        String uri = request.getRequestURI();
        String requestAction = uri.substring(uri.lastIndexOf("/") + 1);
        
        // process the json string out of the request to create and add a new action
        if (requestAction.equals("loadTestCase")){
            String case_id = request.getParameter("case_id");
            
            // load the testcase details
            TestCaseDAO testCaseDAO = new TestCaseDAO();
            TestCase testCase = testCaseDAO.findById(case_id).get(0);
            
            // load the actions that are linked to the testcase
            ActionDAO actionDAO = new ActionDAO();
            List<Action> actions = actionDAO.findByCaseId(testCase.getCase_id());

            // action dao's
            ActionGetURLDAO actionGetURLDAO = new ActionGetURLDAO();
            ActionClickDAO actionClickDAO = new ActionClickDAO();
            ActionSendKeysDAO actionSendKeysDAO = new ActionSendKeysDAO();
            ActionClearDAO actionClearDAO = new ActionClearDAO();
            ActionContainsTextDAO actionContainsTextDAO = new ActionContainsTextDAO();

            JSONObject jsonAction = null; // this holds our action
            JSONArray jsonArrayActions = new JSONArray(); // this holds all our actions
            
            // encode actions into json format
            for (Action action : actions) {
                switch (action.getActionType()) {
                    case GET_URL:
                        ActionGetURL actionGetURL = actionGetURLDAO.findByActionId(action.getAction_id()).get(0);
                        
                        // set json object
                        jsonAction = new JSONObject();
                        jsonAction.put("action", action.getActionType().toString());
                        jsonAction.put("url", actionGetURL.getUrl());
                        break;
                    case CLICK:
                        ActionClick actionClick = actionClickDAO.findByActionId(action.getAction_id()).get(0);
                        
                        // set json object
                        jsonAction = new JSONObject();
                        jsonAction.put("action", action.getActionType().toString());
                        jsonAction.put("elementType", actionClick.getElement().getElementType().toString());
                        jsonAction.put("path", actionClick.getElement().getPath());
                        break;
                    case SEND_KEYS:
                        ActionSendKeys actionSendKeys = actionSendKeysDAO.findByActionId(action.getAction_id()).get(0);
                        
                        // set json object
                        jsonAction = new JSONObject();
                        jsonAction.put("action", action.getActionType().toString());
                        jsonAction.put("elementType", actionSendKeys.getElement().getElementType().toString());
                        jsonAction.put("path", actionSendKeys.getElement().getPath());
                        jsonAction.put("value", actionSendKeys.getValue());
                        break;
                    case CLEAR:
                        ActionClear actionClear = actionClearDAO.findByActionId(action.getAction_id()).get(0);
                        
                        // set json object
                        jsonAction = new JSONObject();
                        jsonAction.put("action", action.getActionType().toString());
                        jsonAction.put("elementType", actionClear.getElement().getElementType().toString());
                        jsonAction.put("path", actionClear.getElement().getPath());
                        break;
                    case CONTAINS_TEXT:
                        ActionContainsText actionContainsText = actionContainsTextDAO.findByActionId(action.getAction_id()).get(0);
                        
                        // set json object
                        jsonAction = new JSONObject();
                        jsonAction.put("action", action.getActionType().toString());
                        jsonAction.put("value", actionContainsText.getValue());
                        jsonAction.put("assert", actionContainsText.isAssertEquals());
                        break;
                }
                // add the json action to the array of json actions
                jsonArrayActions.add(jsonAction);
            }
            
            request.setAttribute("testCase", testCase); // testcase details like name and isActive etc.
            request.setAttribute("actions", jsonArrayActions); // actions in json format
            redirect(request, response, "/edit_testcase.jsp");
        }
        
        else if (requestAction.equals("newTestCase")){
            redirect(request, response, "/edit_testcase.jsp");
        }
        
        else if (requestAction.equals("editTestCase")){
            
            boolean isUpdated = false;
            boolean isCreated = false;
            
            String case_id = request.getParameter("case_id");
            boolean isUpdate = (case_id == "") ? false : true;
            String caseName = request.getParameter("caseName");
            boolean isActive = (request.getParameter("isActive") == null) ? false : true;
            TestCase.Timeframes timeframe = TestCase.Timeframes.valueOf(request.getParameter("selectTimeframe"));
            
            TestCase testCase = null;
            TestCaseDAO testCaseDAO = testCaseDAO = new TestCaseDAO();
            /**
             * Update existing case when editing
             */
            if (isUpdate){
                testCase = testCaseDAO.findById(case_id).get(0);
                
                testCase.setName(caseName);
                testCase.setIsActive(isActive);
                testCase.setTimeframe(timeframe);
                
                testCaseDAO.update(testCase);
                
                /**
                 * It's easier to delete all related actions to this testcase,
                 * and to add them again after while.
                 */
                ActionDAO actionDAO = new ActionDAO();
                List<Action> actionsToDelete = actionDAO.findByCaseId(testCase.getCase_id());
                // delete actions per actiontype
                for (Action action : actionsToDelete){
                    switch(action.getActionType()){
                    case GET_URL:
                        ActionGetURLDAO actionGetURLDAO = new ActionGetURLDAO();
                        actionGetURLDAO.deleteByActionId(action.getAction_id());
                        break;
                    case CLICK:
                        ActionClickDAO actionClickDAO = new ActionClickDAO();
                        actionClickDAO.deleteByActionId(action.getAction_id());
                        break;
                    case SEND_KEYS:
                        ActionSendKeysDAO actionSendKeysDAO = new ActionSendKeysDAO();
                        actionSendKeysDAO.deleteByActionId(action.getAction_id());
                        break;
                    case CLEAR:
                        ActionClearDAO actionClearDAO = new ActionClearDAO();
                        actionClearDAO.deleteByActionId(action.getAction_id());
                        break;
                    case CONTAINS_TEXT:
                        ActionContainsTextDAO actionContainsTextDAO = new ActionContainsTextDAO();
                        actionContainsTextDAO.deleteByActionId(action.getAction_id());
                        break;
                    }
                    // delete "parent" action
                    actionDAO.deleteByCaseId(case_id);
                }
            }
            /**
             * Create new case when there is no id
             */
            else{
                testCase = new TestCase();
                testCase.setName(caseName);
                testCase.setIsActive(isActive);
                testCase.setStatus("N/A");
                testCase.setLastTested(new Date(0)); // this tells us to test it immediately as soon as it becomes active
                testCase.setTimeframe(timeframe);
                // get the user from the session and set it as the owner
                User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
                testCase.setOwner(loggedInUser);
                
                case_id = testCaseDAO.insert(testCase);
            }
            
            String actionsToDecode = request.getParameter("actions"); // in json format
            
            // decode json string and create objects from it
            JSONArray arrayActions = (JSONArray)JSONValue.parse(actionsToDecode);
            
            for (int i=0;i<arrayActions.size();i++){ // for every action
                JSONObject subactions = (JSONObject)arrayActions.get(i);
                
                Action action = new Action(Action.ActionTypes.valueOf(subactions.get("action").toString()));
                action.setCase_id(case_id);
                ActionDAO actionDAO = new ActionDAO();
                
                String action_id = actionDAO.insert(action); // this id will be used for all child actions
                action.setAction_id(action_id);
                
                switch (action.getActionType()) {
                    case GET_URL:
                        ActionGetURL actionGetUrl = new ActionGetURL(action.getActionType(),
                                subactions.get("url").toString());
                        actionGetUrl.setAction_id(action.getAction_id());
                        
                        ActionGetURLDAO actionGetURLDAO = new ActionGetURLDAO();
                        actionGetURLDAO.insert(actionGetUrl);
                        break;
                    case CLICK:
                        ActionClick actionClick = new ActionClick(action.getActionType(),
                                new Element(
                                        Element.ElementTypes.valueOf(subactions.get("elementType").toString()),
                                        subactions.get("path").toString()
                                ));
                        actionClick.setAction_id(action.getAction_id());
                        
                        ActionClickDAO actionClickDAO = new ActionClickDAO();
                        actionClickDAO.insert(actionClick);
                        break;
                    case SEND_KEYS:
                        ActionSendKeys actionSendKeys = new ActionSendKeys(action.getActionType(),
                                new Element(
                                        Element.ElementTypes.valueOf(subactions.get("elementType").toString()),
                                        subactions.get("path").toString()
                                ),
                        subactions.get("value").toString()
                        );
                        actionSendKeys.setAction_id(action.getAction_id());
                        
                        ActionSendKeysDAO actionSendKeysDAO = new ActionSendKeysDAO();
                        actionSendKeysDAO.insert(actionSendKeys);
                        break;
                    case CLEAR:
                        ActionClear actionClear = new ActionClear(action.getActionType(),
                                new Element(
                                        Element.ElementTypes.valueOf(subactions.get("elementType").toString()),
                                        subactions.get("path").toString()
                                ));
                        actionClear.setAction_id(action.getAction_id());

                        ActionClearDAO actionClearDAO = new ActionClearDAO();
                        actionClearDAO.insert(actionClear);
                        break;
                    case CONTAINS_TEXT:
                        System.out.println("FDSJKFLDS: " + subactions.get("assert"));
                        ActionContainsText actionContainsText = new ActionContainsText(action.getActionType(),
                                subactions.get("value").toString(),
                                (subactions.get("assert").equals("true"))?true:false);
                        actionContainsText.setAction_id(action.getAction_id());

                        ActionContainsTextDAO actionContainsTextDAO = new ActionContainsTextDAO();
                        actionContainsTextDAO.insert(actionContainsText);
                        break;
                }
            }
            if (isUpdate){
                isUpdated = true;
            }
            else{
                isCreated = true;
            }
            request.setAttribute("isUpdated", isUpdated);
            request.setAttribute("isCreated", isCreated);
            setTestCasesOnRequest(request);
            setMetaDataOnRequest(request);
            redirect(request, response, "testcases.jsp");
        }
    }
    /**
     * Redirect to .jsp file
     * @param request
     * @param response
     * @param address
     * @throws ServletException
     * @throws IOException 
     */
    private void redirect(HttpServletRequest request, HttpServletResponse response, String address)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
    
    private void setTestCasesOnRequest(HttpServletRequest request)
            throws ServletException, IOException{
        TestCaseDAO testCaseDAO = new TestCaseDAO();
        List<TestCase> testCases = testCaseDAO.findAll();
        
        // filter testcases on the user that is logged in
        User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
        
        for (int i=0;i<testCases.size();i++){
            if (!testCases.get(i).getOwner().getUserId().equals(loggedInUser.getUserId())){
                testCases.remove(testCases.get(i));
            }
        }
            
        request.setAttribute("testCases", testCases);
    }
    
    /**
     * Data like the schedulers last heart beat
     * @param request 
     */
    private void setMetaDataOnRequest(HttpServletRequest request){
        SchedulerDAO schedulerDAO = new SchedulerDAO();
        models.Scheduler scheduler = schedulerDAO.find();
        
        request.setAttribute("scheduler", scheduler);
    }
}
