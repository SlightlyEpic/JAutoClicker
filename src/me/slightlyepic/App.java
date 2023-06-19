package me.slightlyepic;

import com.formdev.flatlaf.FlatDarkLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.text.NumberFormat;

public class App {
    private Listener listener;
    private int currentInputCps;

    private JPanel panelMain;
    private JButton applyButton;
    private JLabel cpsLabel;
    private JButton toggleButton;
    private JLabel messageLabel;
    private JFormattedTextField cpsFormTextField;

    public static void main(String[] args) {
        FlatDarkLaf.setup();

        App app = new App();
        JFrame frame = new JFrame("Epic Autoclicker");
        frame.setContentPane(app.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public App() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook: " + ex.getMessage());
            System.exit(1);
        }

        /*
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);

        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(99);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        cpsFormTextField.setFormatterFactory(factory);
        cpsFormTextField.setText("20");
         */

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
                try {
                    int inputCps = Integer.parseInt(cpsFormTextField.getText());
                    if (inputCps <= 50) {
                        int actualCps = inputCps + (int) (inputCps / 5);
                        listener.setCps(actualCps);
                        currentInputCps = inputCps;
                        messageLabel.setForeground(new Color(26, 211, 82));
                        messageLabel.setText("Set CPS to " + inputCps);
                    } else {
                        messageLabel.setForeground(new Color(229, 32, 32));
                        messageLabel.setText("<html><body>CPS cannot be<br>greater than 50</body></html>");
                    }
                } catch(NumberFormatException ex) {
                    messageLabel.setForeground(new Color(229, 32, 32));
                    messageLabel.setText("Invalid CPS");
                }
            }
        });
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(toggleButton.getText().equals("Enable")) {
                    cpsFormTextField.setText(String.valueOf(currentInputCps));
                    messageLabel.setText("");
                    listener.setIsActive(true);
                } else {
                    listener.setIsActive(false);
                }
            }
        });
    }
}
