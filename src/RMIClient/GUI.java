package RMIClient;

import Models.MessageInformation;
import RMIServer.IComunication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GUI extends JFrame implements Runnable {

    private int serverPort = 0;
    private String serverIP = "", nickName = "";

    private static JPanel panelIzquierdo, panelDerecho;

    IComunication rmi;

    public GUI() {
        super("ClienteRMI");
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel Configuracion = new JLabel("Formulario");
        JTextField serverIPField = new JTextField(20);
        JTextField serverPortField = new JTextField(20);
        JTextField nickNameField = new JTextField(20);
        JButton Entrar = new JButton("Enviar");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1));
        formPanel.add(new JLabel("Ip del servidor:"));
        formPanel.add(serverIPField);
        formPanel.add(new JLabel("Puerto del servidor:"));
        formPanel.add(serverPortField);
        formPanel.add(new JLabel("Nickname:"));
        formPanel.add(nickNameField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Entrar);

        mainPanel.add(Configuracion, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        Entrar.addActionListener(e -> {
            if (!serverIPField.getText().equals("") && !serverPortField.getText().equals("") && !nickNameField.getText().equals("")) {
                serverPort = Integer.parseInt(serverPortField.getText());
                serverIP = serverIPField.getText();
                nickName = nickNameField.getText();
                setChatLayout();
            } else {
                JOptionPane.showMessageDialog(null, "¡Rellena todos los campos!", "Alerta", JOptionPane.WARNING_MESSAGE);
            }

        });

        Thread thread = new Thread(this::run);
        thread.start();

        pack();
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

        // Panel derecho
        panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(0xCEC78B));
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneDerecho = new JScrollPane(panelDerecho);
        scrollPaneDerecho.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        splitPane.setBottomComponent(scrollPaneDerecho);

        // Panel inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(Color.GREEN);
        add(panelInferior, BorderLayout.SOUTH);

        // Textfield en el panel inferior
        JTextField textField = new JTextField();
        panelInferior.add(textField, BorderLayout.CENTER);

        try {
            rmi = (IComunication) java.rmi.Naming.lookup("//" + serverIP + ":" + serverPort + "//RMIServer");
            new ClientEntity(rmi);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        textField.addActionListener((actionEvent) -> {
            if (textField.getText() != "") {
                try {
                    rmi.sentMessage(new MessageInformation(nickName, serverIP, textField.getText()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                textField.setText("");
            }
        });
    }

    public static void addLog(MessageInformation messageInformation) {
        JLabel label = new JLabel(messageInformation.userNickName + " - " + messageInformation.messageDate + ": " + messageInformation.message);
        panelDerecho.add(label);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                revalidate();
                repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
