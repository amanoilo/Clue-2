package clueGame;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Detective extends JDialog{

	
	private JTextField guess;
    private JTextField response;
    private JTextField die;
    private JTextField current;
    
public Detective()
{
	setTitle("Detective Notes");    
	setSize(600,650);
	
	setLayout(new GridLayout(3,2));
	
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
	panel.add(new Checkbox("Alabama"));
	panel.add(new Checkbox("Bjorn Bjornson"));
	panel.add(new Checkbox("Carl"));
	panel.add(new Checkbox("Chet"));
	panel.add(new Checkbox("Jon"));
	panel.add(new Checkbox("Mary"));
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Characters"));
	return panel;
}

public JPanel roomPanel()
{
	//rooms are: Maelstrom, Innistrad, Zendikar, Ravnica, Alara, Mirrodin, Phyrexia, Dominaria, Kamigawa, Shandalar
	JLabel rooms = new JLabel("Rooms");
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(5,2));
	panel.add(new Checkbox("Alara"));
	panel.add(new Checkbox("Dominaria"));
	panel.add(new Checkbox("Innistrad"));
	panel.add(new Checkbox("Kamigawa"));
	panel.add(new Checkbox("Mirrodin"));
	panel.add(new Checkbox("Phyrexia"));
	panel.add(new Checkbox("Ravnica"));
	panel.add(new Checkbox("Shandalar"));
	panel.add(new Checkbox("Zendikar"));

	panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
	return panel;
}

public JPanel weaponPanel()
{
	//weapons are: Sword, Pen, Mace, Laughing Gas, Endless Breadsticks, Heartbreak.
	JLabel weapons = new JLabel("Weapons");
	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(3,2));
	panel.add(new Checkbox("Endless Breadsticks"));
	panel.add(new Checkbox("Heartbreak"));
	panel.add(new Checkbox("Laughing Gas"));
	panel.add(new Checkbox("Mace"));
	panel.add(new Checkbox("Pen"));
	panel.add(new Checkbox("Sword"));
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	return panel;
}

public JPanel characterGuessPanel()
{
	JLabel characterGuess = new JLabel("Character Guess");
	JPanel panel = new JPanel();
	String[] characters = {"Alabama", "Bjorn Bjornson", "Carl", "Chet", "Jon", "Mary"};
	JComboBox combox = new JComboBox(characters);
	panel.add(combox);
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Character Guess"));
	return panel;
}

public JPanel roomGuessPanel()
{
	JLabel roomGuess = new JLabel("Room Guess");
	JPanel panel = new JPanel();
	String[] rooms = {"Alara", "Dominaria", "Innistrad", "Kamigawa", "Mirrodin", "Phyrexia", "Ravnica", "Shandalar", "Zendikar"};
	JComboBox combox = new JComboBox(rooms);
	panel.add(combox);
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
	return panel;
}

public JPanel weaponGuessPanel()
{
	JLabel weaponGuess = new JLabel("Weapon Guess");
	JPanel panel = new JPanel();
	String[] weapons = {"Endless Breadsticks", "Heartbreak", "Laughing Gas", "Mace", "Pen", "Sword"};
	JComboBox combox = new JComboBox(weapons);
	panel.add(combox);
	panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
	return panel;
}

}
