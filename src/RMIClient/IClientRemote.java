package RMIClient;

import Models.MessageInformation;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientRemote extends Remote {

    void receiveMessage(MessageInformation message) throws RemoteException;

}
