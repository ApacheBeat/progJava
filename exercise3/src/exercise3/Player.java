package exercise3;

import java.util.*;

/**
 * Abstract class representing a player
 */
public abstract class Player {
	
	//Player name
	protected String name;
	
	//Attack, defense and life Points
	protected int attackPoints = 0;
	protected int defensePoints = 0;
	protected int life = 0;
	protected int count = 0;
	
	//Arrays to store the number of teams a player belongs to and
	//the number of items a player can possess
	protected ArrayList<Item> items =new ArrayList<Item>();
	protected ArrayList<Team> teams = new ArrayList<Team>();
	
	/**
	 * Constructor method for a player
	 * @param name - name of the player
	 * @param attack - attackPoints of the player
	 * @param defense - defensePoints of the player
	 * @param life - life of the player
	 */
	public Player (String name, int life, int attack, int defense) {
		this.name = name;
		this.attackPoints = attack;
		this.defensePoints = defense;
		this.life = life;
		this.teams = new ArrayList<Team>();
		this.items = new ArrayList<Item>();
		this.count = 0;
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
		return this.attackPoints;
	}

	/**
	 * Method to get the defense points of the player as an integer
	 * @return defense points
	 */
	public int getDefensePoints() {
		return this.defensePoints;
	}

	/** 
	 * Method to get the life of the player
	 * @return life of the player
	 */
	public int getLife() {
		return this.life;
	}

	/**
	 * Method to make a player hit another player
	 * Damage is the amount of life the player loses 
	 * @param attack points of life to be lost
	 */
	protected void hit (int attack) {
		for(int i = 0; i<items.size();i++) {
			this.attackPoints += items.get(i).getAttackBonus();
			this.defensePoints += items.get(i).getDefenseBonus();
		}
		
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
	public final void attack (Player p) throws FightException{
		if(p == null) {
			throw new FightException (FightException.TARGET_NULL);
		}
		if(p == this) {
			throw new FightException(FightException.SELF_FIGHT);
		}else {
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
		
	}
	
	/**
	 * Method to add a player to a team
	 * @param team to be added to
	 * @return true
	 */
	public boolean add(Team team) {
		teams.add(team);
		return true;
	}
	
	/**
	 * Method to get the teams a player belongs to
	 * @return an ArrayList including these teams
	 */
	public List<Team> getTeams(){
		return teams;
	}
	
	/**
	 * Method to add an item to the player
	 * @param item to be added
	 * @throws RolGameException 
	 */
	public void addItem(Item item) throws RolGameException {
		items.add(item);
	}

	public List<Item> getItems(){
		return items;
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
					+ "/ " + items.get(i).getDefenseBonus() + ", ";
		}
		return ret;
	}

}