import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Logfile
 * Encapsulates the operation of writing log messages out to file
 * 
 * @author Gabbie Burns
 *
 */
public class Logfile {

	/**
	 * A self-instance used as part of the Singleton pattern
	 */
	private static Logfile myInstance = null;
	
	/**
	 * Writer used to push messages out to the actual log file
	 */
	BufferedWriter myWriter = null;
	
	/**
	 * Keep count of the current line number to label the file
	 */
	private static int lineNumber;
	
	/**
	 * Private constructor for compliance with Singleton pattern.
	 */
	private Logfile()
	{
	}
	
	/**
	 * Provide an instance of yourself
	 * 
	 * @return Singleton instance for the class
	 */
	public static Logfile getInstance()
	{
		if ( myInstance == null)
		{
			myInstance = new Logfile();
		}
		
		return myInstance;
	}
	
	/**
	 * Open a file for writing the log of the current game
	 * 
	 * @param filename
	 */
	public void openLog( String filename )
	{
		try
		{
			myWriter = new BufferedWriter( new FileWriter( filename ));
		}
		catch( IOException e )
		{
			// handle the exception
		}
		
		lineNumber = 0;
	}
	
	/**
	 * Write the given message out to the log
	 * 
	 * @param logMsg
	 */
	public void writeLog( String logMsg )
	{
		if( lineNumber > 0)
		{
			logMsg = lineNumber + ". " + logMsg;
		}
		
		try
		{
			myWriter.write( logMsg );
			myWriter.newLine();
			myWriter.flush();
		}
		catch( IOException e)
		{
			// handle the exception
		}
		
		lineNumber++;
	}
	
	/**
	 * Close the file when logging is turned off
	 */
	public void closeLog()
	{
		try
		{
			myWriter.close();
		}
		catch (IOException e)
		{
			// handle the exception
		}
		
		// Set the self-instance to null so that it can be created again with the next logging session
		myInstance = null;
		
	}
	
	/**
	 * Return the number for the next line of the file
	 * 
	 * @return lineNumber
	 */
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	
}
