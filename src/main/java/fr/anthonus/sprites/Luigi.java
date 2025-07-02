package fr.anthonus.sprites;

import fr.anthonus.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class Luigi extends Sprite {

    public Luigi() throws IOException {
        super(ImageIO.read(Main.class.getResourceAsStream("/sprites/luigi.png")));
    }
}
