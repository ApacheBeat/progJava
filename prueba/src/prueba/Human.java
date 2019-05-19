package prueba;


/**
 * Human Player representation 
 *
 */
public class Human extends Player {
	
	final int life;
	/**
	 * Constructor
	 */
	public Human (String name, int defense, int attack, int life) {
		super (name, defense, attack, Math.min(life, 100));
		this.life = 100;
	}
	
	
}
