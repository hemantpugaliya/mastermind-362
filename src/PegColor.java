
public enum PegColor {
	
		BLANK("0"), BLACK("K"), WHITE("W"), RED("R"), YELLOW("Y"), GREEN("G"), BLUE("B");
		
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
