package controllers;

import daos.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;

/**
 *
 * @author wesley
 */
public class UserController extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        if (requestAction.equals("login")){
            boolean loginSuccess = false;
            
            // get parameters from request
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // look up username
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByUsername(username);
            
            if (user != null){
                // user exists: match passwords from request and the database
                String hashedPassword = md5(password);
                
                if (user.getPassword().equals(hashedPassword)){
                    // login successfull, set user on session
                    loginSuccess = true;
                    request.getSession().setAttribute("loggedInUser", user);
                }   
            }
            
            // redirect user
            if (loginSuccess){
                redirect(request, response, "/homepage.html");
            }
            else{
                request.setAttribute("loginSuccess", loginSuccess);
                redirect(request, response, "/index.jsp");
            }
        }
        else if (requestAction.equals("logout")){
            // clear the session and redirect back to the loginpage
            request.getSession().setAttribute("loggedInUser", null);
            redirect(request, response, "/index.jsp");
        }
        else if (requestAction.equals("register")){
            boolean registerSuccess = false;
            boolean usernameTaken = true;
            boolean emailAddressTaken = true;
            
            // get parameters from request
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String emailAddress = request.getParameter("emailAddress");
            String username = request.getParameter("username");
            String password = md5(request.getParameter("password"));
            
            // verify that the username and emailaddress are unique
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByUsername(username);
            
            if (user == null){ // username is unique
                usernameTaken = false;
                user = userDAO.findByEmailAddress(emailAddress);
                if (user == null){ // username and email address are unique
                    emailAddressTaken = false;
                    // create user
                    user = new User();
                    user.setFirstname(firstname);
                    user.setLastname(lastname);
                    user.setEmailAddress(emailAddress);
                    user.setUsername(username);
                    user.setPassword(password);
                    
                    userDAO.insert(user);
                    
                    registerSuccess = true;
                }
            }
            // redirect user
            request.setAttribute("usernameTaken", usernameTaken);
            request.setAttribute("emailAddressTaken", emailAddressTaken);
            request.setAttribute("registerSuccess", registerSuccess);
            redirect(request, response, "/index.jsp");
            
        }
    }
    
    /**
     * Creates md5 hash from given String
     *
     * @param password      String that needs to be converted to a md5-hash
     * hash
     * @return MD5-hash in String format
     */
    public String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
}