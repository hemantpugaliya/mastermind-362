import java.util.ArrayList;


public class MasterMindPlayerGroup implements PlayerGroup {

	private ArrayList<Player> playerGroup = new ArrayList<Player>();
	public MasterMindPlayerGroup()
	{
		playerGroup = new ArrayList();
		
	}
	public void addPlayer(Player player){
		playerGroup.add(player);
	}
	public void removePlayer(Player player){
		for(int i = 0; i < playerGroup.size(); i++){
			if(playerGroup.get(i).equals(player))
				playerGroup.remove(i);
		}
	}
}
