package clueGame;

import java.util.ArrayList;

public class Solution{
	@Override
	public String toString() {
		if(person!="" && weapon!="" && room!= ""){
					return person + " with " + weapon + " in " + room;

		}else{
			return "";
		}
	}

	private String person;
	private String weapon;
	private String room;
	private ArrayList<Card> cards;
	
	public Solution(String person, String weapon, String room)
	{
		this.person = person;
		this.weapon = weapon;
		this.room = room;
		
		cards = new ArrayList<Card>();
		cards.add(new Card(Card.CardType.PERSON, person));
		cards.add(new Card(Card.CardType.WEAPON, weapon));
		cards.add(new Card(Card.CardType.ROOM, room));
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

	public boolean equals(Solution s) 
	{
		return (person.equals(s.getPerson()) && weapon.equals(s.getWeapon()) && room.equals(s.getRoom())) ? true : false;
	}
	
	public boolean contains(Card card)
	{
		return (card.getName() == person || card.getName() == weapon || card.getName() == room);
	}
	
	public ArrayList<Card> getCards()
	{
		return cards;
	}
}
