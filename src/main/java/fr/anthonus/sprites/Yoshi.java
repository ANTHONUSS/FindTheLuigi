package fr.anthonus.sprites;

import fr.anthonus.Main;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Yoshi extends Sprite {

    public Yoshi() throws IOException {
        super(ImageIO.read(Main.class.getResourceAsStream("/sprites/yoshi.png")));
    }
}
