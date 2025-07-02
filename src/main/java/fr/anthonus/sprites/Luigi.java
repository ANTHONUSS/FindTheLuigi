package fr.anthonus.sprites;

import fr.anthonus.Main;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Luigi extends Sprite {

    public Luigi() throws IOException {
        super(ImageIO.read(Main.class.getResourceAsStream("/sprites/luigi.png")));
    }
}
