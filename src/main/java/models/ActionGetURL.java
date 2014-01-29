package models;

/**
 *
 * @author wesley
 */
public class ActionGetURL extends Action{
    
    private String id;
    private String url;
    
    public ActionGetURL(){
        
    }
    
    public ActionGetURL(ActionTypes actionType, String url){
        super(actionType);
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}