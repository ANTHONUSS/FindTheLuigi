package fr.anthonus.window;

import fr.anthonus.Main;
import fr.anthonus.sprites.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class GameCanvas extends JPanel {
    private static final List<Sprite> sprites = new ArrayList<>();
    public static final int windowWidth = 1000;
    public static final int windowHeight = 800;

    private static boolean luigiFound = false;

    private static Clip clip;
    private static final URL gameBG = Main.class.getResource("/sounds/game-bg.wav");
    private static final URL luigiSounds[] = {
            Main.class.getResource("/sounds/luigi-1.wav"),
            Main.class.getResource("/sounds/luigi-2.wav"),
            Main.class.getResource("/sounds/luigi-3.wav"),
            Main.class.getResource("/sounds/luigi-4.wav")
    };

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
                                stop();
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
                if (!luigiFound) sprite.moveSprite();
            }

            repaint();
        }).start();

        // Démarre la musique de fond
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(gameBG);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-20.0f); // Réduit le volume à -20 dB
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }


    }

    private void stop(){
        luigiFound = true;

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        // clear toute la liste sauf le 1er
        Luigi luigi = (Luigi) sprites.get(0);
        sprites.clear();
        sprites.add(luigi);
        setBackground(Color.YELLOW);
        //repaint();

        RandomGenerator random = RandomGenerator.getDefault();
        int rand = random.nextInt(4);
        URL selectedSound = luigiSounds[rand];

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(selectedSound);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    SwingUtilities.invokeLater(() -> {
                        clip.close();
                        System.exit(0);
                    });
                }
            });

            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f); // Réduit le volume à -20 dB
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
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
