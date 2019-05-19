package prueba;


/**
 * Generic Player representation 
 *
 */
public abstract class Player {
	
	/** player name */
	protected String name;
	
	/** attack, defense and hit points */
	protected int attackPoints = 0;
	protected int defensePoints = 0;
	protected int life = 0;
	
	/**
	 * Constructor
	 */
	public Player (String name, int attack, int defense, int life) {
		this.name = name;
		this.attackPoints = attack;
		this.defensePoints = defense;
		this.life = life;
	}
	
	/**
	 * get AttackPoints
	 * 
	 * @return attack points
	 */
	public int getAttackPoints() {
		return attackPoints;
	}

	/**
	 * get DefensePoints
	 * 
	 * @return defense points
	 */
	public int getDefensePoints() {
		return defensePoints;
	}

	/** get Life
	 * 
	 * @return life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * The player was damaged with X points. Usually he will lose life
	 * 
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
	 * Player attacks another player. If player is dead, he/she cannot attack
	 * 
	 * @param p player attacked by this
	 */
	public final void attack (Player p) {
		// Check if dead
		if (this.getLife() > 0) {
			System.out.println(this + " attacks " + p);
			int histAttack = p.getAttackPoints();
			int myAttack = getAttackPoints();
			
			hit(histAttack);
			p.hit(myAttack);
			System.out.println("Attack result: " + this + ", " + p);
		}
		else {
			System.out.println(this + " was DEAD. Cannot attack");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
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
