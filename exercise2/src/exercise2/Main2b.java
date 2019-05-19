package exercise2;

/**
 * Class to test the exercise 2.b
 * @author Victor Alonso Garrigos
 */
public class Main2b {

	public static void main (String [] args) {

		//Creation of 3 players
		System.out.println ("** Creating a Human, an Alien and a Warrior");
		Player human = new Human("John Smith", 13, 8, 39);
		Player alien = new Alien("Plk Dfw", 31, -3, 32);
		Player warrior = new Warrior("Geronimo", 9, 4, 100);

		//Creation of 5 items 
		System.out.println ("** Creating Items");
		Item item1 = new Item ("Sunglasses", -1, -1);
		Item item2 = new Item ("False Nails", 5, 2);
		Item item3 = new Item ("AK-47", 20, -7);
		Item item4 = new Item ("Ironed Umbrella", -7, 10);
		Item item5 = new Item ("Frodo's Ring", 2, 7);

		//Addition of items to the players
		System.out.println ("** Asociate Players with Items");
		human.addItem(item1);
		human.addItem(item2);
		human.addItem(item4);
		alien.addItem(item5);
		warrior.addItem(item3);

		System.out.println();
		System.out.println("** Show players:");
		System.out.println(human);
		System.out.println(alien);
		System.out.println(warrior);

		//Attacks between the players
		System.out.println();
		warrior.attack(alien);

		System.out.println();
		warrior.attack(alien);

		System.out.println();
		human.attack(warrior);
	}
}
