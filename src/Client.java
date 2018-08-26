import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  

public class Client {  
   private Client() {}  
   
   public static void printGateState(String state)
   {
	   int index = 0;
	   for(int i = 0; i < 3; i++)
	   {
		   for(int j = 0; j < 3; j++)
		   {
			   System.out.print("  " + state.charAt(index) + " ");
			   index++;
		   }
		   System.out.println();
	   }
   }
   
   public static void main(String[] args) {  
      try {  
         
   
    	  // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 
    
         
  
         
         // Looking up the registry for the remote object 
         GameServerInterface stub = (GameServerInterface) registry.lookup("TicTacToeServer"); 
    
       
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
         
         
         System.out.println("Welcome to Tic Tac Toe !");
    	 
    	 System.out.print("Enter player name to register\n, q to quit");   
    	 
    	 
    	 String playerName = bufferedReader.readLine();
    	 
    	 
    	 if(playerName.equals("q"))
    		 return;
    	 
    	 while(stub.addToWaitingList(playerName) != true)
    	 {
    		 System.out.println("player name already exists ! Please enter another name\n");
    		 playerName = bufferedReader.readLine();
    	 }
    	 
    	 
     	 System.out.print("Successfully registered ! Waiting for another player to join in\n");   
         
    	 
         
         while(true)
         {
         	 while(stub.checkIfGameAlloted(playerName) == false)
         	 {
         		 System.out.println("Still waiting for player ! \n");
         		 Thread.sleep(10000);
         	 }
         	 
         	 System.out.println("You Are All SET ! ");
         	 
         	 int gameId = stub.getGameId(playerName);
         	 String opponentName = stub.getOpponentName(gameId, playerName);
         	 String myMarker = stub.getPlayerMarker(playerName);
         	 
         	 
         	 while(stub.hasGameFinished(gameId) != true)
         	 {
         		 
         		 System.out.println("Game State Is \n");
         		 String state = stub.getGameState(gameId);
         		 printGateState(state);
         		 if(stub.isPlayerTurn(playerName))
         		 {
         			 System.out.println("Its your turn \n");
         			 
         			 while(true)
         			 {
         				System.out.println("Enter valid position number\n");
         				int x = 10; // wait 2 seconds at most

         				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
         				long startTime = System.currentTimeMillis();
         				while ((System.currentTimeMillis() - startTime) < x * 1000
         				        && !in.ready()) {
         				}
         				int pos = -1;

         				if (in.ready()) 
         				{
         					try
         					{
         						pos = (Integer.parseInt(in.readLine()));	
         					}
         					catch(Exception e)
         					{
         						pos = -1;
         					}
         				    System.out.println("You entered: " + pos);
         				} 
         				else 
         				{
         				    System.out.println("You did not enter data..you lose game !!");
         				    stub.finishGame(gameId, opponentName);
         				    break;
         				}
         				if(pos < 1 || pos > 9)
         					continue;
         				else if(state.charAt(pos-1) == '*' || state.charAt(pos-1) == '-')
							continue;
						else
						{
							StringBuilder tempState = new StringBuilder(state);
							tempState.setCharAt(pos-1, myMarker.charAt(0));
							state = tempState.toString();
							stub.makeMove(gameId, state);
							stub.changeTurn(gameId, playerName);
							break;
						}	
         			 } //while true
         		 } // if my turn
         		 else
         		 {
         			 System.out.println("Waiting for opponent to play !\n");
         			 Thread.sleep(20000);
         			 if(stub.isPlayerTurn(playerName))
         				 continue;
         			 else if(stub.hasGameFinished(gameId))
         				 continue;
         			 else
         			 {
         				 System.out.println("Opponent Seems to be disconnected ! Finishing Game\n");
         				 stub.finishGame(gameId, playerName);
         			 }
      
         		 }
         	 } // while game has finished
 
         	 System.out.println(" GAME OVER ! .. Winner IS : " + stub.getGameWinner(gameId));
         	 
         	 System.out.println(" Want to play another game ? press 1 for yes, 0 for no ");
         	 int choice = (Integer.parseInt(bufferedReader.readLine()));
         	 if(choice == 1)
         	 {
         		stub.addToWaitingList(playerName);
         	 }
         	 else
         	 {
         		 break;
         	 }
         } // while true
         System.out.println("Game Finished !");
         System.out.println("We will miss you for Tic Tac Toe !");
        
         // System.out.println("Remote method invoked"); 
      } catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
}