import ducksim.DuckPond
import ducksim.DuckSimController
import ducksim.DuckSimView
import java.awt.EventQueue
import javax.swing.JComponent
import javax.swing.JFrame

private fun createAndShowGUI() {
    val model = DuckPond()
    val view = DuckSimView(model)
    val controller = DuckSimController(view, model)
    view.setEscapeAction(controller.escapeAction)
    view.addMouseListener(controller)
    view.addMouseListener(controller.popupListener)
    val frame = JFrame("DuckSim")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    val content: JComponent = view
    content.isOpaque = true
    frame.contentPane = content
    frame.setSize(800, 600)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}
