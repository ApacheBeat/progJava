package exercise3;

/**
 * Human class extending Player
 * @author Victor Alonso Garrigos
 */
public class Human extends Player {

	/**
	 * Constructor method for a Human, specifying that 
	 * the life can't be over 100	 * 
	 * @param name - Name of the human
	 * @param attack - Attack points of a human
	 * @param life - Life of a human
	 * @param defense - Defense points of a human
	 */
	public Human (String name, int attack, int life, int defense) {
		super (name, defense, attack, Math.min(life, 100));
	}


}