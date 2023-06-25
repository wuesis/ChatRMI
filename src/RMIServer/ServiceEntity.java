package RMIServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceEntity extends UnicastRemoteObject implements IComunication{

    protected ServiceEntity() throws RemoteException {
        super();
    }

    @Override
    public void sentMessage(String message) throws RemoteException {

    }

}
