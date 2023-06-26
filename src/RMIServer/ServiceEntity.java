package RMIServer;

import Models.UserInformation;
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
    public void sentMessage(String message) throws RemoteException {
        System.out.println("Broadcasting message: " + message);
        for (IClientRemote client : Usuarios) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void regist(IClientRemote userInformation) {
        Usuarios.add(userInformation);
    }
}
