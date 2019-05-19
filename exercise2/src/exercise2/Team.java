package exercise2;

import java.util.*;

/**
 * Class Team
 * @author Victor Alonso Garrigos
 */
public class Team {

	//Name of the team and ArrayList with the players of this team
	private String name;
	public ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * Constructor method of the class Team
	 * @param Name - name of the team
	 */
	public Team(String Name) {
		this.name = Name;
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * Method to get the name of the team as a string
	 * @return the name of the team
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to add a player to the team
	 * @param player - Player to be added to the team
	 * @return true
	 */
	public boolean add(Player player) {
		player.add(this);
		players.add(player);
		return true;
	}
	
	/**
	 * Method to remove a player from the team
	 * @param player - player to be removed from the team
	 */
	public boolean remove(Player player) {
		players.remove(player);
		return true;
	}
	
	/**
	 * Method to get the players belonging to a team
	 * @return the ArrayList with the players
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	/**
	 * Overwritten method toString to print in the screen the teams
	 */
	public String toString() {
		String ret = "Team " + getName() + "(";
		for (int i=0;i<players.size(); i++) {
			ret += players.get(i).toString();
		}
		ret += ")";
		return ret;  
	}
}
