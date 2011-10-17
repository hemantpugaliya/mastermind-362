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
	 * Writer used to push messages out to the actual log file
	 */
	BufferedWriter myWriter = null;
	
	/**
	 * Keep count of the current line number to label the file
	 */
	private int lineNumber;
	
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
