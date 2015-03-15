package clueGame;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BadConfigFormatException extends Exception 
{
	private static String returnMessage = "Config file not formatted correctly";
	private static String filename = "ErrorLog.txt";
	private String detail;
	
	BadConfigFormatException() 
	{
		super(returnMessage);
		detail = "";
		// logError();
	}
	
//	BadConfigFormatException(String logFileName)
//	{
//		super(returnMessage);
//		filename = logFileName;
//		logError();
//	}
	
	BadConfigFormatException(String detail) 
	{
		super(returnMessage + detail);
		this.detail = detail;
		// logError();
	}
	
	public void logError() 
	{
		PrintWriter out;
		
		try 
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			out.println("\nError log for run attempt at: " + dateFormat.format(date));
			out.println(toString());
			//out.write();
			out.close();
		} 
		
		catch (FileNotFoundException e) 
		{
			System.out.println("No log file available. Error not recorded");
		} 
		
		catch (IOException e) {
			System.out.println("Some kind of IO Exception");
			
		}
	}
	
	@Override
	public String toString() { return returnMessage + detail; }
	
}
