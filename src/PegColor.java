/**
 * An enum representing the different colored pegs
 * 
 * @author Gabbie Burns
 *
 */
public enum PegColor {
	
		RED("R"), BLUE("B"), GREEN("G"), YELLOW("Y"), GWHITE("W"), GBLACK("K"), FWHITE("W"), FBLACK("K"), BLANK("0");
		
		private String name;
		
		/**
		 * Store a "name" for each color to be used in the toString
		 * 
		 * @param n  the color's representative name
		 */
		private PegColor( String n )
		{
			name = n;
		}
		
		/**
		 * Return a text presentation of the color
		 * 
		 * @return   the color's representative name
		 */
		public String toString()
		{
			return name;
		}
	}
