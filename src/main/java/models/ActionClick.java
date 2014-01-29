package models;

/**
 *
 * @author wesley
 */
public class ActionClick extends Action{
    
    private String id;
    private Element element;
    
    public ActionClick(){
        
    }
    
    public ActionClick(ActionTypes actionType, Element element){
        super(actionType);
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