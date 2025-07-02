package fr.anthonus.sprites;

import java.io.IOException;

public class Mario extends Sprite {

    public Mario() throws IOException {
        super(loadImage("/sprites/mario.png", Mario.class));


    }
}
