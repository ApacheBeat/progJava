package exercise1;

/**
 * Main class for the project exercise 1
 * @author Victor Alonso Garrigos
 * Some players will be created and then fight against others
 */
public class Main1{

	public static void main(String[] args){ 

		//Declaration and creation of players
		System.out.println ("** Creating a Human, an Alien and a Warrior");
		System.out.println(" ");
		Human human = new Human("John Smith", 13, 8, 39);
		Alien alien = new Alien("Plk Dfw", 27, 2, 32);
		Warrior warrior = new Warrior("Geronimo", 9, 4, 100);
		System.out.println ("** Show players: ");
		System.out.println (human.getName() + " "+ human.getAttackPoints() + "/" + human.getDefensePoints() + "/" + human.getLife());
		System.out.println (alien.getName() + " "+ alien.getAttackPoints() + "/" + alien.getDefensePoints() + "/" + alien.getLife());
		System.out.println (warrior.getName() + " "+ warrior.getAttackPoints() + "/" + warrior.getDefensePoints() + "/" + warrior.getLife());
		System.out.println(" ");
	
		//Battle of the players
		human.attack(alien);
		human.attack(alien);
		human.attack(alien);
		human.attack(alien);
		human.attack(alien);
		alien.attack(warrior);
		warrior.attack(alien);

	}

}