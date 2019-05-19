package exercise2;

/**
 * Alien class extending Player
 * @author Victor Alonso Garrigos
 *
 */
public class Alien extends Player{
	
	/**
	 * Constructor method for the class Alien
	 * @param name
	 * @param defense
	 * @param attack
	 * @param life
	 */
	public Alien (String name, int attack, int defense, int life) {
		super (name, defense, attack, life);
		this.name = name;
		this.defensePoints = defense;
		this.attackPoints = attack;
		this.life = life;
	}

	/**
	 * Method to get the attack points of a player as an integer
	 * @return attack points
	 */
	public int getAttackPoints() {
		//if(getLife()>20) attackPoints *= 3;
		//for(int i=0;i<items.size();i++) {
		//	attackPoints = attackPoints + items.get(i).getAttackBonus();
		//}
			for(int i=0;i<items.size();i++) {
				attackPoints = attackPoints + items.get(i).getAttackBonus();
			}
			return this.attackPoints;
		
		//return this.attackPoints;
	}

	/**
	 * Method to get the defense points of the player as an integer
	 * @return defense points
	 */
	public int getDefensePoints() {
		//if(getLife()>20) defensePoints /= 3;
		//for(int i=0;i<items.size();i++) {
		//	defensePoints = defensePoints + items.get(i).getDefenseBonus();
		//}
		for(int i=0;i<items.size();i++) {
			defensePoints = defensePoints + items.get(i).getDefenseBonus();
		}
		return this.defensePoints;
		//return this.defensePoints;
	}
	
	/**
	 * @Overriden method HIT for the alien to include
	 * personal characteristics
	 * @param attack - Attack points of the opponent
	 */
	public void hit (int attack) {		
		//attackPoints /= 3;
		//defensePoints *= 3;
		
		if (getLife()>20) {
			attackPoints = getAttackPoints();
			defensePoints= getDefensePoints();
		}else {
			attackPoints = getAttackPoints()/3;
			defensePoints = getDefensePoints()*3;
		}
		
		int damage = (attack - defensePoints);
		
		if (damage < 0) damage = 0;
		
		life -= damage;
		if (life < 0) life = 0;
	
		System.out.println("\t " + this.name + " hit with " + attack +
				", damage with " + damage);
	}
	
	/**
	 * Overwritten method toString to print in the screen the state 
	 * of the player and his items
	 */
	public String toString() {
		String ret = name + " ";
		
		if (life > 0) {
			ret += getAttackPoints() + "/" + getDefensePoints() + "/" + getLife();
		}
		else {
			ret += "is DEAD";
		}

		ret += " (member of " + getTeams().size() + " teams) items: ";
		for(int i=0; i<items.size();i++) {
			ret += items.get(i).getName() + " " + items.get(i).getAttackBonus()
					+ "/" + items.get(i).getDefenseBonus() + ", ";
		}
		//ret += "\r";
		return ret;
	}
}
