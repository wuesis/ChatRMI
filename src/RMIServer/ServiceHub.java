package RMIServer;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class ServiceHub {

    public static void main(String[] args) {

        Runnable initialFrame = new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        };

        try {
            SwingUtilities.invokeAndWait(initialFrame);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
