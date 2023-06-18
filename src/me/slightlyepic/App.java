package me.slightlyepic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class App {
    private JPanel panelMain;
    private JButton applyButton;
    private JTextField cpsTextField;
    private JLabel cpsLabel;
    private JButton toggleButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public App() {
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, cpsTextField.getText());
            }
        });
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(toggleButton.getText().equals("Enable")) {
                    toggleButton.setText("Disable");
                    toggleButton.setBackground(new Color(190, 55, 54));
                    // toggleButton.setBackground(Color.BLACK);
                } else {
                    toggleButton.setText("Enable");
                    toggleButton.setBackground(new Color(104, 190, 83));
                    // toggleButton.setBackground(Color.BLACK);
                }
            }
        });
    }
}
