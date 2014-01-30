package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.List;
import models.Action;
import models.ActionClear;
import models.Element;
import org.bson.types.ObjectId;

public class ActionClearDAO extends MongoManager {

    public ActionClearDAO() {

    }

    public void insert(ActionClear actionClear) {
        DBCollection table = db.getCollection("action_clear");
        BasicDBObject document = new BasicDBObject();
        
        document.put("action_id", new ObjectId(actionClear.getAction_id()));
        document.put("elementType", actionClear.getElement().getElementType().toString());
        document.put("elementPath", actionClear.getElement().getPath());
        
        table.insert(document);
    }
    
    public List<ActionClear> findByActionId(String actionId){
        List<ActionClear> actions = new ArrayList<ActionClear>();
        
        DBCollection table = db.getCollection("action_clear");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(actionId));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    ActionClear actionClear = new ActionClear();
            actionClear.setElement(new Element(
                    Element.ElementTypes.valueOf(dbObject.get("elementType").toString()),
                    dbObject.get("elementPath").toString())
            );
            actions.add(actionClear);
	}
        return actions;
    }
    
    public void deleteByActionId(String action_id){
        DBCollection table = db.getCollection("action_clear");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(action_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    table.remove(dbObject);
	}
    }

}