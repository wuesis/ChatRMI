package RMIClient;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public GUI(int port, String nickName, String ipAddress) {
        super("ClienteRMI");
        setSize(500, 300);
        setVisible(true);
        setResizable(false);

        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        // Panel izquierdo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.YELLOW);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

        JScrollPane scrollPaneIzquierdo = new JScrollPane(panelIzquierdo);
        scrollPaneIzquierdo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        splitPane.setTopComponent(scrollPaneIzquierdo);

        // Panel derecho
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.BLUE);
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
            if (textField.getText() != ""){
                JLabel label = new JLabel(textField.getText());
                panelDerecho.add(label);
                textField.setText("");
                revalidate();
                repaint();
            }
        });
    }


}
