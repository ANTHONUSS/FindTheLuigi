package fr.anthonus.sprites;

import java.io.IOException;

public class Wario extends Sprite {

    public Wario() throws IOException {
        super(loadImage("/sprites/wario.png", Wario.class));
    }
}
