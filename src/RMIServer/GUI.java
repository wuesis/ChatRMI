package RMIServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GUI extends JFrame {

    private int serverPort=0;
    private  String serverIp="";
    public GUI() {
        super("Servidor chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);
        setResizable(false);

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

    public void setChatLayout() {
        this.getContentPane().removeAll();
        setSize(1000, 500);
        setLayout(new BorderLayout());
        //Split panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);

        // Panel izquierdo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(0xCEC78B));
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneIzquierdo = new JScrollPane(panelIzquierdo);
        scrollPaneIzquierdo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        splitPane.setTopComponent(scrollPaneIzquierdo);
        panelIzquierdo.add(new JLabel("La direccion del servidor es: "+ serverIp +":"+serverPort));
        // Panel derecho
        JPanel panelDerecho = new JPanel();
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
                JLabel label = new JLabel(textField.getText());
                panelDerecho.add(label);
                textField.setText("");
                revalidate();
                repaint();
            }
        });
    }

}