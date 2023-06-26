package RMIServer;

import Models.MessageInformation;
import RMIClient.IClientRemote;
import java.rmi.Remote;

public interface IComunication extends Remote {

    void sentMessage(MessageInformation message) throws java.rmi.RemoteException;

    void regist(IClientRemote userInformation) throws java.rmi.RemoteException;

}
