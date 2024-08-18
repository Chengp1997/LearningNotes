
public class FromClient {

	private String message;
	private String destination;
	private String type;
	
	public void setDestination(String destination){
		this.destination=destination;
	}
	public void setMessage(String message){
		this.message=message;
	}
	public String getDestination(){
		return destination;
	}
	
	public String getMessage(){
		return message;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
}
