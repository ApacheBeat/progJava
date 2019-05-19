package prueba;

public class Main {

	
	public void run() {
		
		Human human = new Human("John Smith", 13, 8, 39);
		Allien alien = new Allien("Plk Dfw", 27, 2, 32);
		Warrior warrior = new Warrior("Geronimo", 9, 4, 100);
		
		human.attack(alien);
		human.attack(alien);
		human.attack(alien);
		
		alien.attack(warrior);
		warrior.attack(alien);
	}

	
	
}
