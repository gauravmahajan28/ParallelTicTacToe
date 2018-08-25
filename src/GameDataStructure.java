import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDataStructure 
{
	// entry key : gameId, value : state of game 
	public static Map<Integer,String> gameToState = new HashMap<Integer, String>();
		
	// entry key : gameId, value : player1-player2
	public static Map<Integer,String> gameToPlayer = new HashMap<Integer, String>();
	
	// entry key : playerName, value : gameId
	public static Map<String,Integer> playerToGame = new HashMap<String, Integer>();
		
	// list of waiting clients
	public static List<String> waitingPlayers = new ArrayList<String>();
	
	//map from player name to character
	public static Map<String,String> playerToMarker = new HashMap<String, String>();
	
	//map from palyer name to turn 
	public static Map<String, Boolean> playerToTurn = new HashMap<String, Boolean>();
	
	public static int gameIdCounter = 1;
	
	//map from game id to winner name
	public static Map<Integer, String> gameToResult = new HashMap<Integer, String>();
		
	
	
	
	
}
