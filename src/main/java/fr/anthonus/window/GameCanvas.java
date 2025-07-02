package fr.anthonus.window;

import fr.anthonus.sprites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameCanvas extends JPanel {
    List<Sprite> sprites = new ArrayList<>();
    public static final int windowWidth = 1000;
    public static final int windowHeight = 800;

    public GameCanvas() {

        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        try {
            sprites.add(new Luigi());

            for (int i = 0; i < 100; i++) {
                sprites.add(new Mario());
                sprites.add(new Wario());
                sprites.add(new Yoshi());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Sprite sprite : sprites) {
                    if (sprite.getBounds().contains(e.getPoint())) {
                        switch (sprite) {
                            case Luigi luigi -> {
                                System.out.println("Luigi clicked!");
                            }
                            default -> {}
                        }
                    }
                }
            }
        });

        int fps = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate(); // get les fps de l'écran
        if (fps <= 0) fps = 10; // Si le taux de rafraîchissement n'est pas défini, on utilise une valeur par défaut

        new Timer(1000 / fps, e -> {
            for (Sprite sprite : sprites) {
                sprite.moveSprite();
            }

            repaint();
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Désactive TOUT anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // Désactive l'interpolation (empêche le flou lors du scaling)
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Évite tout adoucissement d'image
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        for (Sprite sprite : sprites) {
            sprite.draw(g2);
        }
    }
}
