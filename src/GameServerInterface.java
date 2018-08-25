import java.rmi.Remote; 
import java.rmi.RemoteException;  

// Creating Remote interface for our application 
public interface GameServerInterface extends Remote 
{  
   boolean addToWaitingList(String playerName) throws RemoteException;  
   
   boolean checkIfGameAlloted(String playerName) throws RemoteException;  
   
   int getGameId(String playerName) throws RemoteException;  
   
   void reInitializeGame(int gameId) throws RemoteException;
   
   boolean hasGameFinished(int gameId)throws RemoteException;
   
   void makeMove(int gameId, String gameStatus) throws RemoteException;
   
   String getGameState(int gameId) throws RemoteException;
   
   void changeTurn(int gameId, String currentPlayerName) throws RemoteException;
   
   String getOpponentName(int gameId, String currentPlayerName) throws RemoteException;
   
   boolean isPlayerTurn(String playerName) throws RemoteException;
   
   String getPlayerMarker(String playerName) throws RemoteException; 
   
   void finishGame(int gameId, String opponent) throws RemoteException; 
   
   String getGameWinner(int gameId) throws RemoteException; 
   
} 