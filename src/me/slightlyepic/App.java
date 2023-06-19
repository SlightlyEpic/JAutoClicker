package me.slightlyepic;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class App {
    private Listener listener;
    private int currentInputCps;

    private JPanel panelMain;
    private JButton applyButton;
    private JTextField cpsTextField;
    private JLabel cpsLabel;
    private JButton toggleButton;
    private JLabel messageLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public App() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook: " + ex.getMessage());
            System.exit(1);
        }

        currentInputCps = 20;

        listener = new Listener(currentInputCps);
        listener.addPropertyChangeListener(e -> {
            if(e.getPropertyName().equals("isActive")) {
                if ((boolean) e.getNewValue()) {
                    toggleButton.setText("Disable");
                    toggleButton.setBackground(new Color(190, 55, 54));
                    // toggleButton.setBackground(Color.BLACK);
                } else {
                    toggleButton.setText("Enable");
                    toggleButton.setBackground(new Color(104, 190, 83));
                    // toggleButton.setBackground(Color.BLACK);
                    listener.setIsActive(false);
                }
            }
        });

        GlobalScreen.addNativeKeyListener(listener);
        GlobalScreen.addNativeMouseListener(listener);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int inputCps = Integer.parseInt(cpsTextField.getText());
                if(inputCps <= 50) {
                    int actualCps = inputCps + (int) (inputCps / 5);
                    listener.setCps(actualCps);
                    currentInputCps = inputCps;
                    messageLabel.setForeground(new Color(26, 211, 82));
                    messageLabel.setText("Set CPS to " + inputCps);
                } else {
                    messageLabel.setForeground(new Color(229, 32, 32));
                    messageLabel.setText("<html><body>CPS cannot be<br>greater than 50</body></html>");
                }
            }
        });
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(toggleButton.getText().equals("Enable")) {
                    cpsTextField.setText(String.valueOf(currentInputCps));
                    messageLabel.setText("");
                    listener.setIsActive(true);
                } else {
                    listener.setIsActive(false);
                }
            }
        });
    }
}
