package exercise3;


public class Alien extends Player{
	
	/**
	 * Constructor method for the class Allien
	 * @param name
	 * @param defense
	 * @param attack
	 * @param life
	 */
	public Alien (String name, int defense, int attack, int life) {
		super (name, defense, attack, Math.min(life, 100));
		this.name = name;
		if (this.getLife()<20) {
			this.attackPoints = this.getAttackPoints() * 3;
			this.defensePoints= this.getDefensePoints() / 3;
		}else {
		}
		this.life = life;
	}
	
	public void addItem(Item item) throws RolGameException {
		
			if(items.size()<2) {
				items.add(item);
			
			}else {
				throw new RolGameException(RolGameException.TOOMUCHITEMS);
		
			}
		
		
	}

	private void RolGameException(String toomuchitems) {
		// TODO Auto-generated method stub
		
	}

}
