import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 

public class Server extends GameServerImplementation { 
   public Server() {} 
   public static void main(String args[]) { 
      try { 
         // Instantiating the implementation class 
         GameServerImplementation obj = new GameServerImplementation(); 
    
         // Exporting the object of implementation class  
         // (here we are exporting the remote object to the stub) 
         GameServerInterface stub = (GameServerInterface) UnicastRemoteObject.exportObject(obj, 0);  
         
         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
         
         registry.bind("TicTacToeServer", stub);  
         System.err.println("Server Running"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 