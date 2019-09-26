package textExcel5;

import java.util.Scanner;

/* TextExcel is the main entry point for a console-based spreadsheet
 * program. It supports commands to create, display, modify, and delete 
 * various cells from the spreadsheet. 
 */
public class TextExcel 
{
	public static void main(String[] args)
	{
		// create the spreadsheet and scanner objects we'll use throughout
		Spreadsheet sheet = new Spreadsheet();
		Scanner console = new Scanner(System.in);
		
		System.out.println("Welcome to Text Excel!");
		
		String command = getCommand(console);
		while (!command.equals("quit"))
		{
			try
			{
				// process this command
				handleCommand(command, sheet);
			}
			catch (Exception ex)
			{
				// if anything goes wrong anywhere in the handling of this command,
				// the code there can throw an exception. we'll catch it here and display
				// an error message.
				System.out.println("Invalid command: " + command);
				ex.printStackTrace();
			}
			
			command = getCommand(console);
		}
		System.out.println("Farewell!");
	}
	
	// prompt the user for a command and return whatever she enters
	private static String getCommand(Scanner s)
	{
		System.out.print("Enter a command: ");
		return s.nextLine();
	}
	
	// parse the specified command and tell the spreadsheet object what to do
	// with it. any failures should result in an exception being thrown.
	private static void handleCommand(String command, Spreadsheet sheet)
	{
		// ignore empty input
		if (command == null || command.isEmpty())
			return;
		
		if (command.equals("print"))
		{
			sheet.print();
			return;
		}
		
		if (command.equals("clear"))
		{
			sheet.clear();
			return;
		}
		
		
		// for some commands, we need to know the first part of the line (like
		// 'A1' in 'A1 = 3.14'), so pull that out into a firstPart variable.
		int space = command.indexOf(" ");
		
		String firstPart = (space == -1) ? command : command.substring(0, space);
		
		if (sheet.isCellReference(firstPart))
		{
			// the whole command is just a cell reference, so display it.
			if (firstPart.equals(command))
			{
				sheet.displayCell(command);
				return;
			}

			// the command is a cell reference followed by ' = ' and something
			// else. Pass the 'something else' to the cell factory to create a 
			// new cell, and tell the spreadsheet to put the new cell at the 
			// location in firstPart.
			if (command.substring(space).startsWith(" = "))
			{
				sheet.setCell(firstPart, CellFactory.create(command.substring(space + 3), sheet));
				return;
			}
		}
		
		if (command.contains("clear "))
		{
			sheet.clear(command.substring(space + 1));
			return;
		}
		
		if (command.contains("sort"))
		{
			if (command.charAt(4) == 'a')
			{
				sheet.sort(command.substring(6), true);
				return;
			}
			if (command.charAt(4) == 'd')
			{
				sheet.sort(command.substring(6), false);
				return;
			}
		}
		throw new IllegalArgumentException(command + " is not recognized as a valid command.");
	}
}
