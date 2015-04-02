package gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Card;
import clueGame.Player;

public class PlayerHand extends JPanel {
	
	private ArrayList<Card> personCards = new ArrayList<Card>();
	private ArrayList<Card> roomCards = new ArrayList<Card>();
	private ArrayList<Card> weaponCards = new ArrayList<Card>();
	
	public PlayerHand(Player human){
		ArrayList<Card> hand = new ArrayList<Card>(human.getCards());
		sortHand(hand);
		
		setSize(200, 500);
		
		JPanel panel = new JPanel();
		setLayout(new GridLayout(1,3));
		
		panel = persons();
		add(panel);
		
		panel = rooms();
		add(panel);
		
		panel = weapons();
		add(panel);
		
	}
	
	public void sortHand(ArrayList<Card> hand){

		for(Card x : hand){
			switch(x.getType())	{
			case PERSON: 
				personCards.add(x);
				break;
			case ROOM: 
				roomCards.add(x);
				break;
			case WEAPON:
				weaponCards.add(x);
				break;
			}
			
		}
	}
	
	
	private JPanel persons(){
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(1,personCards.size()));
    	JLabel label = new JLabel("here");
    	panel.add(label); 
    	fillPanel(personCards, panel);
    	
    	return panel;
	}
	
	private JPanel rooms(){
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(1,roomCards.size()));
    	fillPanel(roomCards, panel);

    	return panel;
	}
	
	private JPanel weapons(){
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(1,weaponCards.size()));
    	fillPanel(weaponCards, panel);

    	return panel;
	}
	
	public void fillPanel(ArrayList<Card> cardList, JPanel panel)
	{
		for(Card x : cardList){
    		JTextField p = new JTextField(x.getName());
    		p.setEditable(false);
    		panel.add(p);
    	}
	}
}
