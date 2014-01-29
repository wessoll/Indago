package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.List;
import models.ActionClick;
import models.Element;
import org.bson.types.ObjectId;

public class ActionClickDAO extends MongoManager {

    public ActionClickDAO() {

    }

    public void insert(ActionClick actionClick) {
        DBCollection table = db.getCollection("action_click");
        BasicDBObject document = new BasicDBObject();
        
        document.put("action_id", new ObjectId(actionClick.getAction_id()));
        document.put("elementType", actionClick.getElement().getElementType().getType());
        document.put("elementPath", actionClick.getElement().getPath());
        
        table.insert(document);
    }
    
    public List<ActionClick> findByActionId(String actionId){
        List<ActionClick> actions = new ArrayList<ActionClick>();
        
        DBCollection table = db.getCollection("action_click");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(actionId));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    ActionClick actionClick = new ActionClick();
            actionClick.setElement(new Element(
                    Element.ElementTypes.valueOf(dbObject.get("elementType").toString()),
                    dbObject.get("elementPath").toString())
            );
            actions.add(actionClick);
	}
        return actions;
    }
    
    public void deleteByActionId(String action_id){
        DBCollection table = db.getCollection("action_click");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("action_id", new ObjectId(action_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    table.remove(dbObject);
	}
    }
}