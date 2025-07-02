package fr.anthonus.sprites;

import fr.anthonus.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Mario extends Sprite {

    public Mario() throws IOException {
        super(ImageIO.read(Main.class.getResourceAsStream("/sprites/mario.png")));


    }
}
