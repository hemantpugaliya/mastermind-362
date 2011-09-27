import java.util.ArrayList;


public class MastermindPlayerGroup {

	private ArrayList<MastermindPlayer> playerGroup = new ArrayList<MastermindPlayer>();
	public MastermindPlayerGroup()
	{
		playerGroup = new ArrayList<MastermindPlayer>();
		
	}
	public void addPlayer(MastermindPlayer player){
		playerGroup.add(player);
	}
	public void removePlayer(MastermindPlayer player){
		for(int i = 0; i < playerGroup.size(); i++){
			if(playerGroup.get(i).equals(player))
				playerGroup.remove(i);
		}
	}
}
