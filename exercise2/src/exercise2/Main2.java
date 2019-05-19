package exercise2;

public class Main2 {

	/**
	 * Main application
	 */
	public static void main (String [] args) {
		
		System.out.println ("** Creating a Human, an Alien and a Warrior");
		Player human = new Human("John Smith", 13, 8, 39);
		Player alien = new Alien("Plk Dfw", 27, 2, 32);
		Player warrior = new Warrior("Geronimo", 9, 4, 100);

		System.out.println ("** Creating Teams");
		Team t1 = new Team("Human's");
		Team t2 = new Team("Aliens's");
		Team t3 = new Team("Good people");
		
		System.out.println ("** Asociate Players with teams");
		t1.add(human);
		t1.add(warrior);
		t2.add(alien);
		t3.add(human);
		
		System.out.println();
		System.out.println("** Show players:");
		System.out.println(human);
		System.out.println(alien);
		System.out.println(warrior);

		System.out.println();
		System.out.println("** Show teams:");
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t3);
		
		alien.getTeams();
	}
}
