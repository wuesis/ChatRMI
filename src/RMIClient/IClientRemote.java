package RMIClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientRemote extends Remote {

    void receiveMessage(String message) throws RemoteException;

}
