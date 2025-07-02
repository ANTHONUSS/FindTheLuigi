package fr.anthonus;

import fr.anthonus.window.GameCanvas;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Find Luigi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameCanvas gameCanvas = new GameCanvas();
        frame.setContentPane(gameCanvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);

        frame.setVisible(true);
    }
}