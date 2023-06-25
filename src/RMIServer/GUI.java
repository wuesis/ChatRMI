package RMIServer;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class GUI extends JFrame {

    public GUI(int port) {
        super("Servidor chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);
        setResizable(false);
        try {
            ServiceEntity serviceEntity = new ServiceEntity();
            String ip = InetAddress.getLocalHost().getHostAddress();
            LocateRegistry.createRegistry(port);
            System.setProperty("java.rmi.server.hostname",ip);
            Naming.rebind("//"+ip + ":" + port + "//RMIServer", serviceEntity);
            System.out.println("Servidor en linea");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}