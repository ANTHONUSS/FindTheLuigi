package fr.anthonus.sprites

import fr.anthonus.panel.GamePanel
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.util.random.RandomGenerator
import javax.imageio.ImageIO
import kotlin.math.sqrt

abstract class Sprite(
    protected var image: BufferedImage
) {
    protected var x: Float = 0f
    protected var y: Float = 0f
    protected var dx: Float = 0f
    protected var dy: Float = 0f
    protected var speed: Int = 0

    companion object {
        fun loadImage(path: String, contextClass: Class<*>): BufferedImage {
            val inputStream = contextClass.getResourceAsStream(path)
                ?: throw IllegalArgumentException("Image not found: $path")
            return ImageIO.read(inputStream)
        }
    }

    init {
        image = resizeImage(image, 2)

        val random: RandomGenerator = RandomGenerator.getDefault()
        x = random.nextFloat((GamePanel.Companion.windowWidth - image.width).toFloat())
        y = random.nextFloat((GamePanel.Companion.windowHeight - image.height).toFloat())

        dx = random.nextFloat(-5f, 5f)
        dy = random.nextFloat(-5f, 5f)
        val norm: Float = sqrt((dx * dx + dy * dy))
        dx /= norm
        dy /= norm

        speed = random.nextInt(2, 4)
    }

    fun draw(g: Graphics2D) { g.drawImage(image, x.toInt(), y.toInt(), null) }

    fun getBounds(): Rectangle { return Rectangle(x.toInt(), y.toInt(), image.width, image.height)
    }

    fun move() {
        x += dx * speed
        y += dy * speed

        if (x < 0 || x + image.width > GamePanel.Companion.windowWidth) dx = -dx
        if (y < 0 || y + image.height > GamePanel.Companion.windowHeight) dy = -dy
    }

    fun resizeImage(originalImage: BufferedImage, scale: Int): BufferedImage {
        val width = originalImage.width * scale;
        val height = originalImage.height * scale;
        val resizedImage: BufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g2d: Graphics2D = resizedImage.createGraphics()
        g2d.drawImage(originalImage, 0, 0, width, height, null)
        g2d.dispose()
        return resizedImage
    }


}