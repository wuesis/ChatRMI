package RMIServer;

import java.rmi.Remote;

public interface IComunication extends Remote {

    void sentMessage(String message) throws java.rmi.RemoteException;

//    void getMessages(String currentmessages) throws java.rmi.RemoteException;;
}
