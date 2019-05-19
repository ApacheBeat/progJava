package prueba;



public class Warrior extends Player implements IHuman{
	
	private Human human;
	private int wound;

	public Warrior (String name, int defense, int attack, int life) {
		super (name, defense, attack, Math.min(life, 100));
		this.human = new Human(name, defense, attack, life);
		this.wound = 0;
	}
	
	public Warrior(String name, int defense, int attack, int life, int wound) {
		super (name, defense, attack, Math.min(life, 100));
		this.wound = attack-defense;
		}
}
