package fr.anthonus;

import fr.anthonus.window.GameCanvas;

import javax.swing.*;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mario Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameCanvas gameCanvas = new GameCanvas();
        frame.setContentPane(gameCanvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}