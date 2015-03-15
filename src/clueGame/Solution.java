package clueGame;

public class Solution implements Comparable<Solution>{
	private String person;
	private String weapon;
	private String room;
	
	public Solution(String person, String weapon, String room)
	{
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public String getPerson() 
	{
		return person;
	}

	public String getWeapon() 
	{
		return weapon;
	}

	public String getRoom() 
	{
		return room;
	}

	@Override
	public int compareTo(Solution s) 
	{
		return (person == s.getPerson() && weapon == s.getWeapon() && room == s.getRoom()) ? 1 : 0;
	}
	
	
}
