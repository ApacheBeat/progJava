package prueba;


public class Allien extends Player{
	
	/**
	 * Constructor method for the class Allien
	 * @param name
	 * @param defense
	 * @param attack
	 * @param life
	 */
	public Allien (String name, int defense, int attack, int life) {
		super (name, defense, attack, Math.min(life, 100));
		this.name = name;
		if (this.getLife()<20) {
			this.attackPoints = this.getAttackPoints() * 3;
			this.defensePoints= this.getDefensePoints() / 3;
		}else {
		}
		this.life = life;
	}

}
