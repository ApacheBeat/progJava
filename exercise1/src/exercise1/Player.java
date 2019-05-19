package exercise1;

/**
 * Abstract class Player, some method will be overwritten to
 * adapt to the personal characteristics of each player
 */
public abstract class Player {

	/** player name */
	protected String name;
	
	/** attack, defense and hit points */
	protected int attackPoints = 0;
	protected int defensePoints = 0;
	protected int life = 0;
	
	/**
	 * Constructor method for the player
	 * 
	 */
	public Player (String name, int life, int attack, int defense) {
		this.name = name;
		this.attackPoints = attack;
		this.defensePoints = defense;
		this.life = life;
	}
	
	/**
	 * Method to get the name of the player
	 * @return the name of the player as a string
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the attack points of a player as an integer
	 * @return attack points
	 */
	public int getAttackPoints() {
		return attackPoints;
	}

	/**
	 * Method to get the defense points of the player as an integer
	 * @return defense points
	 */
	public int getDefensePoints() {
		return defensePoints;
	}

	/** 
	 * Method to get the life of the player
	 * @return life of the player
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Method to make a player hit another player
	 * Damage is the amount of life the player loses 
	 * @param attack points of life to be lost
	 */
	protected void hit (int attack) {
		int damage = (attack - getDefensePoints());
		
		if (damage < 0) damage = 0;
		
		life -= damage;
		if (life < 0) life = 0;
		
		System.out.println("\t " + this.name + " hit with " + attack +
				", damage with " + damage);
	}
	
	/**
	 * Method to create an attack, including both players hitting
	 * If player is dead, he/she cannot attack
	 * @param player p attacked by this
	 */
	public final void attack (Player p) {

		// Check if dead
		if (this.getLife() > 0) {
			System.out.println(this + " attacks " + p);
			int histAttack = p.getAttackPoints();
			int myAttack = getAttackPoints();
			
			hit(histAttack);
			p.hit(myAttack);
			System.out.println("Attack result: " + this + ", " + p + "\n");
		}
		else {
			System.out.println(this + " was DEAD. Cannot attack");
			System.out.println(" ");
		}
	}
	
	/**
	 * Overwritten method toString to print in the screen the state of the player
	 */
	public String toString() {
		String ret = name + " ";
		if (life > 0) {
			ret += getAttackPoints() + "/" + getDefensePoints() + "/" + getLife();
		}
		else {
			ret += "is DEAD";
		}
		
		return ret;  
	}
}
