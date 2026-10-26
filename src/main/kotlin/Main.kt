package fr.anthonus

import fr.anthonus.panel.GamePanel
import javax.swing.JFrame

fun main() {
    val frame = JFrame("Find Luigi")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    val gamePanel = GamePanel()
    frame.contentPane = gamePanel
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.isResizable = false

    frame.isVisible = true
}