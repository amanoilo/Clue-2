package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import clueGame.Player;
import javax.swing.JDialog;

public class HumanAccusation extends JDialog {

	
	private JComboBox name;
	private JComboBox room;
	private JComboBox weapon;
	private JButton submitButton;
	private JButton cancelButton;
    private ArrayList<String> gameWeapons;
    private ArrayList<Player> gamePlayers;
    private ArrayList<String> gameRooms;

	public HumanAccusation(ArrayList<Player> gamePlayers, ArrayList<String> gameRooms, ArrayList<String> gameWeapons)	{
		setTitle("Make a Suggestion");
		setSize(300,300);
		//JPanel suggestions = new JPanel();
		setLayout (new GridLayout(4,2));
		this.gameWeapons = gameWeapons;
		this.gamePlayers = gamePlayers;
		this.gameRooms = gameRooms;
	
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
		
		String[] rooms =  new String[gameRooms.size()];
		for (int i = 0; i < gameRooms.size(); i++)
		{
			rooms[i] = gameRooms.get(i);
		}
		room = new JComboBox(rooms);
		
		JLabel roomLabel = new JLabel("Your room");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel nameLabel = new JLabel("Person");
		
		this.submitButton = new JButton("Submit");
		this.cancelButton = new JButton("Cancel");

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
	
	public JComboBox get_Name() {
		return name;
	}
	public JComboBox getRoom() {
		return room;
	}
	public JComboBox getWeapon() {
		return weapon;
	}
	public JButton getSubmitButton(){
		return this.submitButton;
	}
	
	
}
