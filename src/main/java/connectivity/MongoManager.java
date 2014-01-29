package connectivity;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import daos.TestCaseDAO;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wesley
 */
public class MongoManager {
    
    private static final String URL = "localhost";
    private static final String DB_NAME = "indago";
    
    
    private MongoClient mongoClient;
    protected DB db;
    
    /**
     * Initialize connection and set database
     */
    public MongoManager(){
        try {
            mongoClient = new MongoClient(URL);
            
            db = mongoClient.getDB(DB_NAME);
        } 
        catch (UnknownHostException ex) {
            Logger.getLogger(MongoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}