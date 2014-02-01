package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.ActionClear;
import models.ActionClick;
import models.ActionGetURL;
import models.Element;
import org.bson.types.ObjectId;

public class ActionGetURLDAO extends MongoManager {

    public ActionGetURLDAO() {

    }

    public void insert(ActionGetURL actionGetUrl) {
        DBCollection table = db.getCollection("action_get_url");
        BasicDBObject document = new BasicDBObject();
        
        document.put("action_id", new ObjectId(actionGetUrl.getAction_id()));
        document.put("url", actionGetUrl.getUrl());
        
        table.insert(document);
    }
    
    public List<ActionGetURL> findByActionId(String actionId){
        List<ActionGetURL> actions = new ArrayList<ActionGetURL>();
        
        DBCollection table = db.getCollection("action_get_url");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(actionId));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    ActionGetURL actionGetUrl = new ActionGetURL();
            actionGetUrl.setUrl(dbObject.get("url").toString());
            actions.add(actionGetUrl);
	}
        return actions;
    }
    
    public void deleteByActionId(String action_id){
        DBCollection table = db.getCollection("action_get_url");
 
        BasicDBObject whereQuery = new BasicDBObject();
        System.out.println("action_idmnnnbmabdsmandbasnm: " + action_id);
	whereQuery.put("action_id", new ObjectId(action_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            System.out.println("removing object");
            DBObject dbObject = cursor.next();
	    table.remove(dbObject);
	}
    }
}