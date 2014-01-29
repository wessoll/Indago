package models;

/**
 *
 * @author wesley
 */
public class Element {
    
    public enum ElementTypes{
        ID("ID"),
        NAME("NAME"),
        LINK_TEXT("LINK_TEXT");
        
        public String type;
        
        ElementTypes(String type){
           this.type = type;
        }
        
        public String getType(){
            return type;
        }
    }
    
    private ElementTypes elementType;
    private String path;
    
    public Element(){
        
    }
    
    public Element(ElementTypes elementType, String path){
        this.elementType = elementType;
        this.path = path;
    }

    public ElementTypes getElementType() {
        return elementType;
    }

    public void setElementType(ElementTypes elementType) {
        this.elementType = elementType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
