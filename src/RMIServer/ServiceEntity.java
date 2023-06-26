package RMIServer;

import Models.messageInformation;
import RMIClient.IClientRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServiceEntity extends UnicastRemoteObject implements IComunication{

    ArrayList<IClientRemote> Usuarios;
    protected ServiceEntity() throws RemoteException {
        Usuarios = new  ArrayList<IClientRemote>();
    }

    @Override
    public void sentMessage(messageInformation messageInformation) throws RemoteException {
        GUI.addLog(messageInformation);
        for (IClientRemote client : Usuarios) {
            client.receiveMessage(messageInformation.message);
        }
    }

    @Override
    public void regist(IClientRemote userInformation) {
        Usuarios.add(userInformation);
    }
}
