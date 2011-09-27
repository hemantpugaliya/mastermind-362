
public enum PegColor {
	
		RED("R"), BLUE("B"), GREEN("G"), YELLOW("Y"), GWHITE("W"), GBLACK("K"), FWHITE("W"), FBLACK("K"), BLANK("0");
		
		private String name;
		
		private PegColor( String n )
		{
			name = n;
		}
		
		public String toString()
		{
			return name;
		}
	}
