package fr.anthonus.sprites;

import java.io.IOException;

public class Yoshi extends Sprite {

    public Yoshi() throws IOException {
        super(loadImage("/sprites/yoshi.png", Yoshi.class));
    }
}
