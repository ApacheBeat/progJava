package exercise3;

/**
 * Class Item to define an item a player can possess to modify
 * his personal skills (attackPoints and defensePoints)
 * @author Victor Alonso Garrigos
 */
public class Item {
	
	//Private attributes of the Item
	private int attackBonus;
	private int defenseBonus;
	private String name;
	
	/**
	 * Constructor method of the Item class
	 * @param Name - name of the item
	 * @param AttackBonus - Increment o decrement for the attackPoints
	 * @param DefenseBonus - Increment o decrement for the defensePoints
	 */
	public Item(String Name, int AttackBonus, int DefenseBonus) {
		this.name = Name;
		this.attackBonus = AttackBonus;
		this.defenseBonus = DefenseBonus;
	}
	
	/**
	 * Method to get the name of the item
	 * @return the name of the item as a String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the attack bonus of the item
	 * @return the attack bonus as an integer
	 */
	public int getAttackBonus() {
		return attackBonus;
	}
	
	/**
	 * Method to get the defense bonus of the item
	 * @return the defense bonus as an integer
	 */
	public int getDefenseBonus() {
		return defenseBonus;
	}
	
}