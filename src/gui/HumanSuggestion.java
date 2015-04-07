package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.Player;

public class HumanSuggestion extends JDialog 
{

	private JComboBox name;
	private JComboBox room;
	private JComboBox weapon;
	private Board board;
    private ArrayList<String> gameWeapons;
    private ArrayList<Player> gamePlayers;
    private String[] theRoom = new String[1];

	public HumanSuggestion(ArrayList<Player> gamePlayers, String aroom, ArrayList<String> gameWeapons)	{
		setTitle("Make a Suggestion");
		setSize(300,300);
		//JPanel suggestions = new JPanel();
		setLayout (new GridLayout(4,2));
		this.gameWeapons = gameWeapons;
		this.gamePlayers = gamePlayers;
		theRoom[0] = aroom;
	
		String[] characters =  new String[gamePlayers.size()];
		for (int i = 0; i < gamePlayers.size(); i++)
		{
			characters[i] = gamePlayers.get(i).getName();
		}
		name = new JComboBox(characters);
	
		String[] weapons =  new String[gameWeapons.size()];
		for (int i = 0; i < gameWeapons.size(); i++)
		{
			weapons[i] = gameWeapons.get(i);
		}
		weapon = new JComboBox(weapons);
		
		room = new JComboBox(theRoom);
		room.setEditable(false);
		
		JLabel roomLabel = new JLabel("Your room");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel nameLabel = new JLabel("Person");
		
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
			
		});
		add(roomLabel);
		add(room);
		add(weaponLabel);
		add(weapon);
		add(nameLabel);
		add(name);
		add(submitButton);
		add(cancelButton);
		
	
	
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	
}
