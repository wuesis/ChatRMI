package RMIServer;

import Models.messageInformation;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class GUI extends JFrame implements Runnable {

    private int serverPort = 0;
    private String serverIp = "";

    public static JPanel panelDerecho, panelIzquierdo;

    public GUI() {
        super("Servidor chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);
        setResizable(false);

        Thread refresh = new Thread(this::run);
        refresh.start();

        JLabel Configuracion = new JLabel("Confiuracion");
        JTextField serverPortField = new JTextField(20);
        JButton Entrar = new JButton("Iniciar Serviodor");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 1));
        formPanel.add(new JLabel("Puerto del servidor:"));
        formPanel.add(serverPortField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Entrar);

        mainPanel.add(Configuracion, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        Entrar.addActionListener(e -> {

            if (!serverPortField.getText().equals("")) {
                serverPort = Integer.parseInt(serverPortField.getText());
                try {
                    ServiceEntity serviceEntity = new ServiceEntity();
                    serverIp = InetAddress.getLocalHost().getHostAddress();
                    LocateRegistry.createRegistry(serverPort);

                    System.setProperty("java.rmi.server.hostname", serverIp);

                    Naming.rebind("//" + serverIp + ":" + serverPort + "//RMIServer", serviceEntity);
                    System.out.println("Servidor en linea");
                    setChatLayout();
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            } else {
                JOptionPane.showMessageDialog(null, "¡Rellena todos los campos!", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        });
        pack();
    }

    public static void addLog(messageInformation messageInformation) {
        JLabel label = new JLabel(messageInformation.userNickName + " - " + messageInformation.messageDate + ": " + messageInformation.message);
        panelIzquierdo.add(label);
    }

    public void setChatLayout() {
        this.getContentPane().removeAll();
        setSize(1000, 500);
        setLayout(new BorderLayout());
        //Split panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);

        // Panel izquierdo
        panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(0xCEC78B));
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneIzquierdo = new JScrollPane(panelIzquierdo);
        scrollPaneIzquierdo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        splitPane.setTopComponent(scrollPaneIzquierdo);
        panelIzquierdo.add(new JLabel("La direccion del servidor es: " + serverIp + ":" + serverPort));
        // Panel derecho
        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(0xCEC78B));
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneDerecho = new JScrollPane(panelDerecho);
        scrollPaneDerecho.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        splitPane.setBottomComponent(scrollPaneDerecho);


    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(100);
                revalidate();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}