package exercise1;

/**
 * Alien class extending Player
 * @author Victor Alonso Garrigos
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
	 * Overwritten method HIT for the alien to include
	 * personal characteristics
	 * @param attack (attack points receiving from the other player)
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
	
}
