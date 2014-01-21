package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author wesley
 */
public class ActionController extends HttpServlet {

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
        if (requestAction.equals("addAction")){
            String actionsToDecode = request.getParameter("actions"); // in json format
            
            // decode json string and create objects from it
            JSONArray arrayActions = (JSONArray)JSONValue.parse(actionsToDecode);
            
            for (int i=0;i<arrayActions.size();i++){ // for every action
                JSONObject action = (JSONObject)arrayActions.get(0);
                
                System.out.println(action.get("action"));
                
            }
            
        }
    }
}
