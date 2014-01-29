package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.List;
import models.ActionClick;
import models.ActionGetURL;
import models.ActionSendKeys;
import models.Element;
import org.bson.types.ObjectId;

public class ActionSendKeysDAO extends MongoManager {

    public ActionSendKeysDAO() {

    }

    public void insert(ActionSendKeys actionSendKeys) {
        DBCollection table = db.getCollection("action_send_keys");
        BasicDBObject document = new BasicDBObject();
        
        document.put("action_id", new ObjectId(actionSendKeys.getAction_id()));
        document.put("elementType", actionSendKeys.getElement().getElementType().getType());
        document.put("elementPath", actionSendKeys.getElement().getPath());
        document.put("value", actionSendKeys.getValue());
        
        table.insert(document);
    }
    
    public List<ActionSendKeys> findByActionId(String actionId){
        List<ActionSendKeys> actions = new ArrayList<ActionSendKeys>();
        
        DBCollection table = db.getCollection("action_send_keys");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(actionId));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    ActionSendKeys actionSendKeys = new ActionSendKeys();
            actionSendKeys.setElement(new Element(
                    Element.ElementTypes.valueOf(dbObject.get("elementType").toString()),
                    dbObject.get("elementPath").toString())
            );
            actionSendKeys.setValue(dbObject.get("value").toString());
            actions.add(actionSendKeys);
	}
        return actions;
    }
    
    public void deleteByActionId(String action_id){
        DBCollection table = db.getCollection("action_send_keys");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(action_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    table.remove(dbObject);
	}
    }
}