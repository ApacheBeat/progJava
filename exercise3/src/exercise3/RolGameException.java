package exercise3;

public class RolGameException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public static final String TOOMUCHITEMS = "Player cannot carry more items";
	
	public RolGameException(String message) {
		super(message);
	}

}
