package exercise3;

public class Main3 {

	/**
	 * Main application
	 */
	public static void main (String [] args) throws FightException {

		System.out.println ("** Creating a Human, an Alien and a Warrior");
		Player human = new Human("John Smith", 13, 8, 39);
		Player alien = null;
		Player warrior = new Warrior("Geronimo", 9, 4, 100);


		Player target = null;
		for (int i = 0; i < 3; i++)  {
			System.out.println();
			try {
				warrior.attack(target);
			}catch (FightException e) {
				System.out.println("ERROR. " + e.getMessage());
			}
			if (target == null) {
				target = warrior;
			}
			else {
				target = human;
			}


			
		}
	}

	}
