package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.List;
import models.ActionContainsText;
import models.ActionSendKeys;
import models.Element;
import org.bson.types.ObjectId;

public class ActionContainsTextDAO extends MongoManager {

    public ActionContainsTextDAO() {

    }

    public void insert(ActionContainsText actionContainsText) {
        DBCollection table = db.getCollection("action_contains_text");
        BasicDBObject document = new BasicDBObject();
        
        document.put("action_id", new ObjectId(actionContainsText.getAction_id()));
        document.put("value", actionContainsText.getValue());
        document.put("assertEquals", actionContainsText.isAssertEquals());
        
        table.insert(document);
    }
    
    public List<ActionContainsText> findByActionId(String actionId){
        List<ActionContainsText> actions = new ArrayList<ActionContainsText>();
        
        DBCollection table = db.getCollection("action_contains_text");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(actionId));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    ActionContainsText actionContainsText = new ActionContainsText();
            actionContainsText.setValue(dbObject.get("value").toString());
            actionContainsText.setAssertEquals((Boolean)dbObject.get("assertEquals")?true:false);
            actions.add(actionContainsText);
	}
        return actions;
    }
    
    public void deleteByActionId(String action_id){
        DBCollection table = db.getCollection("action_contains_text");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(action_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    table.remove(dbObject);
	}
    }
}