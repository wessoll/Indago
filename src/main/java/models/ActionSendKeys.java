package models;

/**
 *
 * @author wesley
 */
public class ActionSendKeys extends Action{
    
    private String id;
    private Element element;
    private String value;
    
    public ActionSendKeys(){
        
    }
    
    public ActionSendKeys(ActionTypes actionType, Element element, String value){
        super(actionType);
        this.element = element;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
