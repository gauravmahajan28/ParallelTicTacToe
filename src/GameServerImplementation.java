import java.rmi.RemoteException;

// Implementing the remote interface 
public class GameServerImplementation implements GameServerInterface 
{
	@Override
	public synchronized boolean addToWaitingList(String playerName) throws RemoteException {
		// TODO Auto-generated method stub
		if(GameDataStructure.playerToGame.containsKey(playerName) || GameDataStructure.waitingPlayers.contains(playerName))
			return false;
		else
		{
			GameDataStructure.waitingPlayers.add(playerName);
			if(GameDataStructure.waitingPlayers.size() == 2)
			{
				String player1 = GameDataStructure.waitingPlayers.get(0);
				String player2 = GameDataStructure.waitingPlayers.get(1);
				
				GameDataStructure.waitingPlayers.remove(0);
				GameDataStructure.waitingPlayers.remove(0);
				
				GameDataStructure.gameToPlayer.put(GameDataStructure.gameIdCounter, player1+"-"+player2);
				GameDataStructure.gameToState.put(GameDataStructure.gameIdCounter, "123456789");
				GameDataStructure.playerToGame.put(player1, GameDataStructure.gameIdCounter);
				GameDataStructure.playerToGame.put(player2, GameDataStructure.gameIdCounter);
				GameDataStructure.playerToMarker.put(player1, "*");
				GameDataStructure.playerToMarker.put(player2, "-");
				GameDataStructure.playerToTurn.put(player1, true);
				GameDataStructure.playerToTurn.put(player2, false);
				GameDataStructure.gameIdCounter += 1;
			}
			return true;
		}
	}

	@Override
	public boolean checkIfGameAlloted(String playerName) throws RemoteException 
	{
		if(GameDataStructure.playerToGame.containsKey(playerName))
			return true;
		return false;
	}

	@Override
	public int getGameId(String playerName) throws RemoteException {
		// TODO Auto-generated method stub
		return GameDataStructure.playerToGame.get(playerName);
	}

	@Override
	public void reInitializeGame(int gameId) throws RemoteException 
	{
		String player1 = GameDataStructure.gameToPlayer.get(gameId).split("-")[0];
		String player2 = GameDataStructure.gameToPlayer.get(gameId).split("-")[1];
		GameDataStructure.gameToState.put(gameId, "123456789");
		GameDataStructure.playerToTurn.put(player1, true);
		GameDataStructure.playerToTurn.put(player2, false);
		GameDataStructure.gameToResult.remove(gameId);
	}

	@Override
	public boolean hasGameFinished(int gameId) throws RemoteException 
	{
		// TODO Auto-generated method stub
		
		if(GameDataStructure.gameToResult.containsKey(gameId))
			return true;
	
		String gameStatus = GameDataStructure.gameToState.get(gameId);
		Character ch0 = gameStatus.charAt(0);
		Character ch1 = gameStatus.charAt(1);
		Character ch2 = gameStatus.charAt(2);
		Character ch3 = gameStatus.charAt(3);
		Character ch4 = gameStatus.charAt(4);
		Character ch5 = gameStatus.charAt(5);
		Character ch6 = gameStatus.charAt(6);
		Character ch7 = gameStatus.charAt(7);
		Character ch8 = gameStatus.charAt(8);
		
		System.out.println("char0 " + ch0 + " char1 " + ch1+ " char2 " + ch2+ " char3 " + ch3+ " char4 " + ch4+ " char5 " + ch5+ " char6 " + ch6 + " char7 " + ch7 + " char8 " + ch8 );
		Character same = ')';
		boolean wonFlag = false;
		if(ch0.equals(ch1) && ch0.equals(ch2))
		{
			wonFlag = true;		
			same = ch0;
		}
		else if(ch3.equals(ch4) && ch3.equals(ch5))
		{
			wonFlag = true;	
			same = ch3;
		}
		else if(ch6.equals(ch7) && ch6.equals(ch8))
		{
			wonFlag = true;
			same = ch6;
		}
		else if(ch0.equals(ch3) && ch0.equals(ch6))
		{
			wonFlag = true;
			same = ch0;
		}
		else if(ch1.equals(ch4) && ch1.equals(ch7))
		{
			wonFlag = true;
			same = ch1;
		}
		else if(ch2.equals(ch5) && ch2.equals(ch8))
		{
			wonFlag = true;
			same = ch2;
		}
		else if(ch0.equals(ch4) && ch0.equals(ch8))
		{
			wonFlag = true;
			same = ch0;
		}
		else if(ch2.equals(ch4) && ch2.equals(ch6))
		{
			wonFlag = true;
			same = ch2;
		}
		if(wonFlag)
		{
			String player1 = GameDataStructure.gameToPlayer.get(gameId).split("-")[0];
			String player2 = GameDataStructure.gameToPlayer.get(gameId).split("-")[1];
			if(GameDataStructure.playerToMarker.get(player1).equals(same.toString()))
				GameDataStructure.gameToResult.put(gameId, player1);
			else
				GameDataStructure.gameToResult.put(gameId, player2);
			return true;
		}
		else
			return false;
	}

	@Override
	public void makeMove(int gameId, String gameStatus) throws RemoteException {
		
		GameDataStructure.gameToState.put(gameId, gameStatus);
		
	}

	@Override
	public String getGameState(int gameId) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return GameDataStructure.gameToState.get(gameId);
	}

	@Override
	public void changeTurn(int gameId, String currentPlayerName) 
	{
		// TODO Auto-generated method stub
		String player1 = GameDataStructure.gameToPlayer.get(gameId).split("-")[0];
		String player2 = GameDataStructure.gameToPlayer.get(gameId).split("-")[1];
	
		if(currentPlayerName.equals(player1))
		{
			GameDataStructure.playerToTurn.put(player1, false);
			GameDataStructure.playerToTurn.put(player2, true);
		}
		else
		{
			GameDataStructure.playerToTurn.put(player2, false);
			GameDataStructure.playerToTurn.put(player1, true);
		}
	}

	@Override
	public String getOpponentName(int gameId, String currentPlayerName) throws RemoteException {
		// TODO Auto-generated method stub
		String player1 = GameDataStructure.gameToPlayer.get(gameId).split("-")[0];
		String player2 = GameDataStructure.gameToPlayer.get(gameId).split("-")[1];
		if(currentPlayerName.equals(player1))
			return player2;
		return player1;
	}

	@Override
	public boolean isPlayerTurn(String playerName) throws RemoteException {
		// TODO Auto-generated method stub
		return GameDataStructure.playerToTurn.get(playerName);
	}

	@Override
	public String getPlayerMarker(String playerName) throws RemoteException {
		// TODO Auto-generated method stub
		return GameDataStructure.playerToMarker.get(playerName);
	}

	@Override
	public void finishGame(int gameId, String opponent) throws RemoteException {
		
		GameDataStructure.gameToState.put(gameId, "123456789");
		GameDataStructure.gameToResult.put(gameId, opponent);
		
	}

	@Override
	public String getGameWinner(int gameId) throws RemoteException {
		// TODO Auto-generated method stub
		return GameDataStructure.gameToResult.get(gameId);
	}

} 