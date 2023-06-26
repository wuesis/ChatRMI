package RMIClient;

import Models.MessageInformation;
import RMIServer.IComunication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientEntity extends UnicastRemoteObject implements IClientRemote {


    protected ClientEntity() throws RemoteException {

    }

    @Override
    public void receiveMessage(MessageInformation messageInformation) throws RemoteException {
        GUI.addLog(messageInformation);
    }
}
