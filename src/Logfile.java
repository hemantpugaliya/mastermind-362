import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logfile {

	private static Logfile myInstance = null;
	BufferedWriter myWriter = null;
	private static int lineNumber;
	
	private Logfile()
	{
	}
	
	public static Logfile getInstance()
	{
		if ( myInstance == null)
		{
			myInstance = new Logfile();
		}
		
		return myInstance;
	}
	
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
	
	public void writeLog( String logMsg )
	{
		logMsg = lineNumber + " " + logMsg;
		
		try
		{
			myWriter.write( logMsg );
		}
		catch( IOException e)
		{
			// handle the exception
		}
		
		lineNumber++;
	}
	
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
	
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	
}
