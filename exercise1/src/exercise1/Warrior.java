package exercise1;

/**
 * Warrior class extending Player
 * @author Victor
 */
public class Warrior extends Player{

	/**
	 * Constructor method for a warrior
	 * @param name - Name of the warrior
	 * @param attack - Attack points of the warrior
	 * @param defense - Defense points of the warrior
	 * @param life - Life of the warrior
	 */
	public Warrior (String name, int attack, int defense, int life) {
		super (name, defense, attack, life);
		this.name = name;
		this.defensePoints = defense;
		this.attackPoints = attack;
		this.life = life;
	}
	
	/**
	 * Overwritten method to hit, including personal characteristics 
	 * of the warrior
	 */
	protected void hit (int attack) {
		int damage;
		int wound = attack - this.defensePoints;
		if (wound < 5) {
			damage = 0;
		}else {
			damage =(attack - getDefensePoints());
		}
		if (damage < 0) damage = 0;
		
		life -= damage;
		if (life < 0) life = 0;
		
		System.out.println("\t " + this.name + " hit with " + attack +
				", damage with " + damage);
	}
}
