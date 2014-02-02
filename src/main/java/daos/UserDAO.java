package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.TestCase;
import models.User;
import org.bson.types.ObjectId;

public class UserDAO extends MongoManager {

    public UserDAO() {

    }

    public void insert(User user) {
        DBCollection table = db.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        
        document.put("user_id", new ObjectId());
        document.put("firstname", user.getFirstname());
        document.put("lastname", user.getLastname());
        document.put("username", user.getUsername());
        document.put("password", user.getPassword());
        document.put("emailAddress", user.getEmailAddress());
        
        table.insert(document);
    }
    
    
    public List<User> findAll(){
        List<User> users = new ArrayList<User>();
        
        DBCollection table = db.getCollection("user");
 
	DBCursor cursor = table.find();
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    User user = new User();
            
            user.setUserId(dbObject.get("user_id").toString());
            user.setFirstname(dbObject.get("firstname").toString());
            user.setLastname(dbObject.get("lastname").toString());
            user.setUsername(dbObject.get("username").toString());
            user.setPassword(dbObject.get("password").toString());
            user.setEmailAddress(dbObject.get("emailAddress").toString());
            
            users.add(user);
	}
        return users;
    }
    
    public User findByUserId(String userId){
        User user = null;
        DBCollection table = db.getCollection("user");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("user_id", new ObjectId(userId));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            user = new User();
            System.out.println("SERDAO: " + dbObject.get("user_id"));
            user.setUserId(dbObject.get("user_id").toString());
            user.setFirstname(dbObject.get("firstname").toString());
            user.setLastname(dbObject.get("lastname").toString());
            user.setUsername(dbObject.get("username").toString());
            user.setPassword(dbObject.get("password").toString());
            user.setEmailAddress(dbObject.get("emailAddress").toString());
	}
        return user;
    }
        
    public User findByUsername(String username){
        User user = null;
        DBCollection table = db.getCollection("user");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("username", username);
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            user = new User();
            user.setUserId(dbObject.get("user_id").toString());
            user.setFirstname(dbObject.get("firstname").toString());
            user.setLastname(dbObject.get("lastname").toString());
            user.setUsername(dbObject.get("username").toString());
            user.setPassword(dbObject.get("password").toString());
            user.setEmailAddress(dbObject.get("emailAddress").toString());
	}
        return user;
    }
    
    public User findByEmailAddress(String emailAddress){
        User user = null;
        DBCollection table = db.getCollection("user");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("emailAddress", emailAddress);
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            user = new User();
            user.setUserId(dbObject.get("user_id").toString());
            user.setFirstname(dbObject.get("firstname").toString());
            user.setLastname(dbObject.get("lastname").toString());
            user.setUsername(dbObject.get("username").toString());
            user.setPassword(dbObject.get("password").toString());
            user.setEmailAddress(dbObject.get("emailAddress").toString());
	}
        return user;
    }
}