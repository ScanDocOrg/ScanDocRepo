package scandoc.Events;

import java.util.EventObject;

public class EleveErasedEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public EleveErasedEvent(Object source, String message) {
		super(source);
        this.message = message;
	}
	
	public String GetMessage() {
		return message;
	}
	
	public void SetMessage(String message) {
		this.message = message;
	}
	
	
	
}
