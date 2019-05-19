package exercise3;

public class Main3b {

	/**
	 * Main application
	 */
	public static void main (String [] args) {
		
		System.out.println ("** Creating a Human, an Alien and a Warrior");
		Player human = new Human("John Smith", 13, 8, 39);
		Player alien = new Alien("Plk Dfw", 9, 7, 32);
		Player alien2 = new Alien("Plk Dfw", 9, 7, 32);
		Player warrior = new Warrior("Geronimo", 9, 4, 100);

		System.out.println ("** Creating Items");
		Item item1 = new Item ("Sunglasses", -1, -1);
		Item item2 = new Item ("False Nails", 5, 2);
		Item item3 = new Item ("AK-47", 20, -7);
		Item item4 = new Item ("Ironed Umbrella", -7, 10);
		Item item5 = new Item ("Frodo's Ring", 2, 7);
		Item item6 = new Item ("Girlfriend Photograph", 0, 2);
		Item item7 = new Item ("Samurai Sword", 10, 2);
		
		System.out.println ("** Asociate Players with Items");
		try {
			human.addItem(item1);
		}
		catch(RolGameException e) {
			System.out.println ("Game ERROR: " + e.getMessage());
		}

		try {
			warrior.addItem(item2);
		}
		catch(RolGameException e) {
			System.out.println ("Game ERROR: " + e.getMessage());
		}

		// Alien get more than 2 objects!
		try {
			alien.addItem(item3);
			alien.addItem(item4);
			alien.addItem(item5);
			alien.addItem(item6);
		}
		catch(RolGameException e) {
			System.out.println ("Game ERROR: " + e.getMessage());
		}

		// Alien get more than 2 objects!
		try {
			alien2.addItem(item5);
			alien2.addItem(item6);
			alien2.addItem(item7);
		}
		catch(RolGameException e) {
			System.out.println ("Game ERROR: " + e.getMessage());
		}

		// Fight tests
		Player target = alien2;
		for (int i = 0; i < 3; i++)  {
			System.out.println();
			try {
				warrior.attack(target);
			}
			catch (FightException e) {
				System.out.println("Fight ERROR. " + e.getMessage());
			}
			
			if (target == null) {
				target = warrior;
			}
			else {
				target = alien;
			}
		}

		// Alien attacks none - check hierarchy1
		System.out.println();
		try {
			alien.attack(null);
		}
		catch(RolGameException e) {
			System.out.println ("Game ERROR: " + e.getMessage());
		}
		
		// Alien attacks itself - check hierarchy2
		System.out.println();
		try {
			alien.attack(alien);
		}
		catch(FightException e) {
			System.out.println ("Fight ERROR: " + e.getMessage());
		}		

	}
}
