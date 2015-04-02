package gui;

import javax.swing.JOptionPane;

import clueGame.ClueGame;


public class SplashScreen extends JOptionPane {
	
	public SplashScreen(ClueGame game)
	{
		Splash(game);
	}
	
	
	public static void Splash(ClueGame game)
	{

		String message = "Welcome to Clue. Press next player to start.";
		String title = "Welcome to Clue";
		JOptionPane.showMessageDialog(game, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
