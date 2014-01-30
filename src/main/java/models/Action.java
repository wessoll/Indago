package models;

/**
 *
 * @author wesley
 */
public class Action extends TestCase {
    
    public enum ActionTypes{
        GET_URL,
        CLICK,
        SEND_KEYS,
        CLEAR,
        MATCH_TEXT
    }
    
    private String action_id;
    private ActionTypes actionType;
    
    public Action(){
        
    }
    
    public Action(ActionTypes actionType){
        this.actionType = actionType;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public ActionTypes getActionType() {
        return actionType;
    }

    public void setActionType(ActionTypes actionType) {
        this.actionType = actionType;
    }
}