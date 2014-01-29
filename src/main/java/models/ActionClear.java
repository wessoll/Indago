package models;

/**
 *
 * @author wesley
 */
public class ActionClear extends Action{
    
    private String id;
    private Element element;
    
    public ActionClear(){
        
    }
    
    public ActionClear(ActionTypes actionType, Element element){
        super(ActionTypes.CLEAR);
        this.element = element;
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
    
}
