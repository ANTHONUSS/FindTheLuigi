package fr.anthonus.sprites;

import fr.anthonus.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class Luigi extends Sprite {

    public Luigi() throws IOException {
        super(loadImage("/sprites/luigi.png", Luigi.class));
    }
}
