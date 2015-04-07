package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.Player;

public class PlayerHand extends JPanel {
	
	private ArrayList<Card> personCards = new ArrayList<Card>();
	private ArrayList<Card> roomCards = new ArrayList<Card>();
	private ArrayList<Card> weaponCards = new ArrayList<Card>();
	
	public PlayerHand(Player human){
		ArrayList<Card> hand = new ArrayList<Card>(human.getCards());
		sortHand(hand);
		
		
		JPanel panel = new JPanel(new GridLayout(3,1));
		TitledBorder title;
		title = BorderFactory.createTitledBorder("My Cards");
		panel.setBorder(title);
		
		
		JPanel persons= persons();
		panel.add(persons);
		
		JPanel rooms= rooms();
		panel.add(rooms);
		
		JPanel weapons = weapons();
		panel.add(weapons);
		
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
		JPanel panel;
		if(!personCards.isEmpty()){
			panel = new JPanel(new GridLayout(personCards.size(),1));
			fillPanel(personCards, panel);
		}else{
			panel = new JPanel();
		}
    	TitledBorder title;
		title = BorderFactory.createTitledBorder("People");
		panel.setBorder(title);
		
    	return panel;
	}
	
	private JPanel rooms(){
		JPanel panel;
		if(!roomCards.isEmpty()){
			panel = new JPanel(new GridLayout(roomCards.size(),1));
			fillPanel(roomCards, panel);
		}else{
			panel = new JPanel();
		}
    	TitledBorder title;
		title = BorderFactory.createTitledBorder("Rooms");
		panel.setBorder(title);
    	

    	return panel;
	}
	
	private JPanel weapons(){
		JPanel panel;
		if(!weaponCards.isEmpty()){
			panel = new JPanel(new GridLayout(weaponCards.size(),1));
			fillPanel(weaponCards, panel);
		}else{
			panel = new JPanel();
		}
    	TitledBorder title;
		title = BorderFactory.createTitledBorder("Weapons");
		panel.setBorder(title);
    	

    	return panel;
	}
	
	public void fillPanel(ArrayList<Card> cardList, JPanel panel)
	{
		for(Card x : cardList){
    		JTextField p = new JTextField(x.getName());
    		//p.setPreferredSize(new Dimension(100,120));
    		//p.setEditable(false);
    		panel.add(p);
    	}
	}
}
