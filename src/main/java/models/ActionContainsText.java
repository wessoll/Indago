package models;

/**
 *
 * @author wesley
 */
public class ActionContainsText extends Action{
    
    private String id;
    private String value;
    private boolean assertEquals;
    
    public ActionContainsText(){
        
    }
    
    public ActionContainsText(ActionTypes actionType, String value, boolean assertEquals){
        super(actionType);
        this.value = value;
        this.assertEquals = assertEquals;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public boolean isAssertEquals(){
        return assertEquals;
    }
    
    public void setAssertEquals(boolean assertEquals){
        this.assertEquals = assertEquals;
    }
}
