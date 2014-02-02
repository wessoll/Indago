package daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import connectivity.MongoManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Scheduler;
import models.TestCase;
import models.User;
import org.bson.types.ObjectId;

public class SchedulerDAO extends MongoManager {

    public SchedulerDAO() {

    }

    public void insert(Scheduler scheduler) {
        DBCollection table = db.getCollection("scheduler");
        BasicDBObject document = new BasicDBObject();
        
        document.put("scheduler_id", new ObjectId());
        document.put("lastHeartbeat", scheduler.getLastHeartbeat());
        
        table.insert(document);
    }
    
    public void update(Scheduler scheduler) {
        DBCollection table = db.getCollection("scheduler");
        BasicDBObject query = new BasicDBObject();
        
	query.put("scheduler_id", new ObjectId(scheduler.getScheduler_id()));
        
        BasicDBObject newDocument = new BasicDBObject();
	newDocument.put("lastHeartbeat", scheduler.getLastHeartbeat());
 
	BasicDBObject updateObj = new BasicDBObject();
	updateObj.put("$set", newDocument);
 
	table.update(query, updateObj);
    }
        
    public Scheduler find(){
        Scheduler scheduler = null;
        
        DBCollection table = db.getCollection("scheduler");
 
	DBCursor cursor = table.find();
	while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            scheduler = new Scheduler();
            
            scheduler.setScheduler_id(dbObject.get("scheduler_id").toString());
            scheduler.setLastHeartbeat((Date)dbObject.get("lastHeartbeat"));
	}
        return scheduler;
    }
    
}