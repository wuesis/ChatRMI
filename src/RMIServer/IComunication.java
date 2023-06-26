package RMIServer;

import RMIClient.IClientRemote;
import java.rmi.Remote;

public interface IComunication extends Remote {

    void sentMessage(String message) throws java.rmi.RemoteException;

    void regist(IClientRemote userInformation) throws java.rmi.RemoteException;

}
