package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.List;
import models.Action;
import org.bson.types.ObjectId;

public class ActionDAO extends MongoManager {

    public ActionDAO() {

    }

    public String insert(Action action) {
        DBCollection table = db.getCollection("action");
        BasicDBObject document = new BasicDBObject();
        
        ObjectId id = new ObjectId();
        document.put("_id", id);
        document.put("case_id", new ObjectId(action.getCase_id()));
        document.put("actionType", action.getActionType().toString());
        
        table.insert(document);
        
        return id.toString();
    }
    
    public void update(Action action) {
        DBCollection table = db.getCollection("action");
        BasicDBObject query = new BasicDBObject();
        
	query.put("_id", new ObjectId(action.getAction_id()));
        
        BasicDBObject newDocument = new BasicDBObject();
	newDocument.put("actionType", action.getActionType());
 
	BasicDBObject updateObj = new BasicDBObject();
	updateObj.put("$set", newDocument);

	table.update(query, updateObj);
    }
    
    public List<Action> findAll(){
        List<Action> actions = new ArrayList<Action>();
        
        DBCollection table = db.getCollection("action");
 
	DBCursor cursor = table.find();
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    Action action = new Action();
            
            action.setAction_id(dbObject.get("_id").toString());
            action.setCase_id(dbObject.get("case_id").toString());
            action.setActionType(Action.ActionTypes.valueOf(dbObject.get("actionType").toString()));
            
            actions.add(action);
	}
        return actions;
    }
    
    public List<Action> findByCaseId(String case_id){
        List<Action> actions = new ArrayList<Action>();
        
        DBCollection table = db.getCollection("action");
 
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("case_id", new ObjectId(case_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
	    Action action = new Action();
            action.setAction_id(dbObject.get("_id").toString());
            action.setCase_id(dbObject.get("case_id").toString());
            action.setActionType(Action.ActionTypes.valueOf(dbObject.get("actionType").toString()));
            actions.add(action);
	}
        return actions;
    }
    
    public void deleteByCaseId(String case_id){
        DBCollection table = db.getCollection("action");
 System.out.println("delething");
        BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("case_id", new ObjectId(case_id));
        
	DBCursor cursor = table.find(whereQuery);
	while(cursor.hasNext()) {
            System.out.println("deleting by case_id: " + case_id);
            DBObject dbObject = cursor.next();
	    table.remove(dbObject);
	}
    }
}