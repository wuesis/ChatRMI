package RMIClient;

import Models.messageInformation;
import RMIServer.IComunication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GUI extends JFrame implements IClientRemote {

    private int serverPort = 0;
    private String serverIP = "", nickName = "";

    JPanel panelIzquierdo, panelDerecho;
    public GUI() {
        super("ClienteRMI");
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Crear componentes
        JLabel Configuracion = new JLabel("Formulario");
        JTextField serverIPField = new JTextField(20);
        JTextField serverPortField = new JTextField(20);
        JTextField nickNameField = new JTextField(20);
        JButton Entrar = new JButton("Enviar");

        // Crear contenedor principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear panel para las parejas de campos de texto
        JPanel formPanel = new JPanel(new GridLayout(6, 1));
        formPanel.add(new JLabel("Ip del servidor:"));
        formPanel.add(serverIPField);
        formPanel.add(new JLabel("Puerto del servidor:"));
        formPanel.add(serverPortField);
        formPanel.add(new JLabel("Nickname:"));
        formPanel.add(nickNameField);


        // Crear panel para el botón
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Entrar);

        // Agregar componentes al contenedor principal
        mainPanel.add(Configuracion, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar contenedor principal al JFrame
        add(mainPanel);

        // Agregar evento onClick al botón
        Entrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!serverIPField.getText().equals("") && !serverPortField.getText().equals("") && !nickNameField.getText().equals("")) {
                    serverPort = Integer.parseInt(serverPortField.getText());
                    serverIP = serverIPField.getText();
                    nickName = nickNameField.getText();
                    setChatLayout();
                } else {
                    JOptionPane.showMessageDialog(null, "¡Rellena todos los campos!", "Alerta", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        // Mostrar la ventana
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


        textField.addActionListener((actionEvent) -> {
            if (textField.getText() != "") {


                try {
                    IComunication mir = (IComunication) java.rmi.Naming.lookup("//" + serverIP + ":" + serverPort + "//RMIServer");
                    mir.sentMessage(new messageInformation(nickName,serverIP,textField.getText()));
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

                JLabel label = new JLabel(nickName + ": " + textField.getText());
                panelDerecho.add(label);
                textField.setText("");
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        JLabel label = new JLabel(nickName + ": " + message);
        panelDerecho.add(label);
        revalidate();
        repaint();
    }
}
