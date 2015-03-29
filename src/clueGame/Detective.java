package clueGame;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.sun.javafx.collections.MappingChange.Map;

public class Detective extends JDialog{

	
	private JTextField guess;
    private JTextField response;
    private JTextField die;
    private JTextField current;
    private ArrayList<Player> gamePlayers;
    private Map<Character, String> roomsMap;
    private ArrayList<String> gameWeapons;
    private ArrayList<String> gameRooms;
    
public Detective(ArrayList<Player> gamePlayers, ArrayList<String> gameRooms, ArrayList<String> gameWeapons)
{
	
	setTitle("Detective Notes");    
	setSize(600,650);
	
	setLayout(new GridLayout(3,2));
	
	this.gamePlayers = new ArrayList<Player>(gamePlayers);
	this.gameRooms = new ArrayList<String>(gameRooms);
	this.gameWeapons = new ArrayList<String>(gameWeapons);
	
	//JPanel panel = characterPanel();
	add(characterPanel());
	add(characterGuessPanel());
	add(roomPanel());
	add(roomGuessPanel());
	add(weaponPanel());
	add(weaponGuessPanel());

	
	
}

public JPanel characterPanel()
{
	JPanel panel = new JPanel();
	JLabel characters = new JLabel("Characters");
	panel.setLayout(new GridLayout(3,2));
	for (Player p : gamePlayers) panel.add(new Checkbox(p.getName()));
	
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Characters"));
	return panel;
	
}

public JPanel roomPanel()
{
	//rooms are: Maelstrom, Innistrad, Zendikar, Ravnica, Alara, Mirrodin, Phyrexia, Dominaria, Kamigawa, Shandalar
	JLabel rooms = new JLabel("Rooms");
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(5,2));
	for (String r : gameRooms) panel.add(new Checkbox(r));

	panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
	return panel;
}

public JPanel weaponPanel()
{
	//weapons are: Sword, Pen, Mace, Laughing Gas, Endless Breadsticks, Heartbreak.
	JLabel weapons = new JLabel("Weapons");
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(3,2));
	for (String w : gameWeapons) panel.add(new Checkbox(w));

	panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	return panel;
}

public JPanel characterGuessPanel()
{
	JLabel characterGuess = new JLabel("Character Guess");
	JPanel panel = new JPanel();
	String[] characters =  new String[gamePlayers.size()];
	for (int i = 0; i < gamePlayers.size(); i++)
	{
		characters[i] = gamePlayers.get(i).getName();
	}
	JComboBox combox = new JComboBox(characters);
	panel.add(combox);
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Character Guess"));
	return panel;
}

public JPanel roomGuessPanel()
{
	JLabel roomGuess = new JLabel("Room Guess");
	JPanel panel = new JPanel();
	String[] rooms =  new String[gameRooms.size()];
	for (int i = 0; i < gameRooms.size(); i++)
	{
		rooms[i] = gameRooms.get(i);
	}	
	
	JComboBox combox = new JComboBox(rooms);
	panel.add(combox);
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
	return panel;
}

public JPanel weaponGuessPanel()
{
	JLabel weaponGuess = new JLabel("Weapon Guess");
	JPanel panel = new JPanel();
	String[] weapons =  new String[gameWeapons.size()];
	for (int i = 0; i < gameWeapons.size(); i++)
	{
		weapons[i] = gameWeapons.get(i);
	}
	JComboBox combox = new JComboBox(weapons);
	panel.add(combox);
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
	return panel;
}

}
