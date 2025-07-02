package fr.anthonus.sprites;

import fr.anthonus.window.GameCanvas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.random.RandomGenerator;

public abstract class Sprite {
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected static final int speed = 2;
    protected final BufferedImage image;

    public Sprite(BufferedImage image) {
        this.image = resizeImage(image, 2);

        RandomGenerator random = RandomGenerator.getDefault();
        this.x = random.nextFloat(GameCanvas.windowWidth - this.image.getWidth());
        this.y = random.nextFloat(GameCanvas.windowHeight - this.image.getHeight());

        this.dx = random.nextFloat(-5, 5);
        this.dy = random.nextFloat(-5, 5);
        float norm = (float) Math.sqrt(dx * dx + dy * dy);
        this.dx = dx / norm;
        this.dy = dy / norm;
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, image.getWidth(), image.getHeight());
    }

    public void moveSprite() {
        x += dx * speed;
        y += dy * speed;

        if (x < 0 || x + image.getWidth() > GameCanvas.windowWidth) dx = -dx;
        if (y < 0 || y + image.getHeight() > GameCanvas.windowHeight) dy = -dy;
    }



    public static BufferedImage resizeImage(BufferedImage originalImage, int scale) {
        int width = originalImage.getWidth() * scale;
        int height = originalImage.getHeight() * scale;
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
