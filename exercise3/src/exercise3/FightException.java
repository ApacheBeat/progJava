package exercise3;

/**
 * FightException class extending Exception class, adding my own
 * exceptions for some conditions produces during the battle
 * @author Victor Alonso Garrigos
 */
public class FightException extends RolGameException {

	private static final long serialVersionUID = 1L;
	
	//Causes why the execution was produced
	public static final String SELF_FIGHT = "Cannot fight against myself";
	public static final String TARGET_NULL = "Target player is null";
	
	/**
	 * Constructor for this Fight Exception
	 * @param cause why the exception was thrown
	 */
	public FightException(String cause) {
		super(cause);
		
	}
}
