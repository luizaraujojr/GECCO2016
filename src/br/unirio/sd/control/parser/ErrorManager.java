package br.unirio.sd.control.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton that manages errors from parsing and lexical equations
 * 
 * @author Marcio Barros
 */
public class ErrorManager
{
	/**
	 * Global instance for the error manager
	 */
	private static ErrorManager instance = null;
	
	/**
	 * Indicates whether messages will be presented in the console
	 */
	private boolean verbose = true;

	/**
	 * Messages registered in the error manager
	 */
	private List<String> messages;
	
	/**
	 * Initializes the error manager
	 */
	private ErrorManager()
	{
		this.messages = new ArrayList<String>();
	}
	
	/**
	 * Puts the error manager in verbose mode
	 */
	public void setVerbose()
	{
		verbose = true;
	}
	
	/**
	 * Puts the error manager in discrete mode
	 */
	public void setDiscrete()
	{
		verbose = false;
	}
	
	/**
	 * Returns the unique instance of the error manager
	 */
	public static ErrorManager getInstance()
	{
		if (instance == null)
			instance = new ErrorManager();
		
		return instance;
	}
	
	/**
	 * Adds an error message
	 */
	public void add(String message)
	{
		if (verbose)
			System.out.println(message);
		
		this.messages.add(message);
	}
	
	/**
	 * Returns the error messages
	 */
	public Iterable<String> getMessages()
	{
		return messages;
	}
}