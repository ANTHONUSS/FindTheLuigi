package fr.anthonus.sprites;

import fr.anthonus.Main;
import fr.anthonus.sprites.Sprite;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Wario extends Sprite {

    public Wario() throws IOException {
        super(ImageIO.read(Main.class.getResourceAsStream("/sprites/wario.png")));
    }
}
