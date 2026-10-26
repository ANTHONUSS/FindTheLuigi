package fr.anthonus.panel

import fr.anthonus.sprites.*
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.IOException
import java.net.URL
import java.util.random.RandomGenerator
import javax.sound.sampled.*
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.Timer
import kotlin.system.exitProcess

class GamePanel : JPanel() {
    companion object {
        val sprites: MutableList<Sprite> = ArrayList()
        val windowHeight: Int = 800
        val windowWidth: Int = 1000

        var luigiFound: Boolean = false

        lateinit var clip: Clip
        val gameSoundBG: URL? = GamePanel::class.java.getResource("/sounds/game-bg.wav")
        val luigiSounds: Array<URL?> = arrayOf<URL?>(
            GamePanel::class.java.getResource("/sounds/luigi-1.wav"),
            GamePanel::class.java.getResource("/sounds/luigi-2.wav"),
            GamePanel::class.java.getResource("/sounds/luigi-3.wav"),
            GamePanel::class.java.getResource("/sounds/luigi-4.wav")
        )
    }

    init {
        preferredSize = Dimension(windowWidth, windowHeight)
        background = Color.black
        isFocusable = true

        try {

            sprites.add(Luigi())

            for (i in 1..100) {
                sprites.add(Mario())
                sprites.add(Wario())
                sprites.add(Yoshi())
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                for (sprite in sprites) {
                    if (sprite is Luigi && sprite.getBounds().contains(e!!.point) && !luigiFound) {
                        stop()
                        break
                    }
                }
            }
        })

        Timer(16) {
            for (sprite in sprites)
                if (!luigiFound) sprite.move()

            repaint()
        }.start()

        try {
            val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(gameSoundBG)
            clip = AudioSystem.getClip()
            clip.open(audioInputStream)
            val volumeControl: FloatControl = clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            volumeControl.value = -20.0f
            clip.start()
            clip.loop(Clip.LOOP_CONTINUOUSLY)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun stop() {
        luigiFound = true

        if (clip.isRunning) clip.stop()

        val luigi: Luigi = sprites[0] as Luigi
        sprites.clear()
        sprites.add(luigi)
        background = Color.yellow

        //repaint();
        val random = RandomGenerator.getDefault()
        val rand = random.nextInt(4)
        val selectedSound: URL? = luigiSounds[rand]

        try {
            val audioInputStream = AudioSystem.getAudioInputStream(selectedSound)
            clip = AudioSystem.getClip()
            clip.open(audioInputStream)

            clip.addLineListener { event: LineEvent? ->
                if (event!!.type === LineEvent.Type.STOP) {
                    SwingUtilities.invokeLater(Runnable {
                        clip.close()
                        exitProcess(0)
                    })
                }
            }

            val volumeControl =
                clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            volumeControl.setValue(-10.0f) // Réduit le volume à -20 dB
            clip.start()
        } catch (e: UnsupportedAudioFileException) {
            throw java.lang.RuntimeException(e)
        } catch (e: IOException) {
            throw java.lang.RuntimeException(e)
        } catch (e: LineUnavailableException) {
            throw java.lang.RuntimeException(e)
        }
    }



    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        // Désactive TOUT anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)

        // Désactive l'interpolation (empêche le flou lors du scaling)
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR)

        // Évite tout adoucissement d'image
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED)

        for (sprite in sprites) {
            sprite.draw(g2)
        }
    }

}