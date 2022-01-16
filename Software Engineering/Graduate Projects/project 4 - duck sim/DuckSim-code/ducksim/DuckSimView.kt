package ducksim

import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.*

class DuckSimView(private val duckPond: DuckPond) : JPanel() {

    // Field for escape key action
    private var escapeAction: Action? = null

    // Fields for popup menu
    var popup = JPopupMenu()

    // Fields for flying animation
    private var animationX = -50
    val flyTimer = Timer(25) {
        animationX += 5
        repaint()
    }

    // Fields for quacking animation
    val quackTimer: Timer = Timer(100) { repaint() }
    private var quackCounter = 0

    init {
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "doEscapeAction")
        actionMap.put("doEscapeAction", escapeAction)
    }

    fun setEscapeAction(action: Action?) {
        escapeAction = action
    }

    fun initializePopup() {
        popup = JPopupMenu()
    }

    val newDuckButtonRectangle = Rectangle(30, 500, 50, 50)
    val rectangleList = listOf<Rectangle>(
            Rectangle(150, 325, 100, 100), // rectangle for duck 0
            Rectangle(275, 325, 100, 100), // rectangle for duck 1
            Rectangle(400, 325, 100, 100), // rectangle for duck 2
            Rectangle(525, 325, 100, 100), // rectangle for duck 3
            Rectangle(150, 425, 100, 100), // rectangle for duck 4
            Rectangle(275, 425, 100, 100), // rectangle for duck 5
            Rectangle(400, 425, 100, 100), // rectangle for duck 6
            Rectangle(525, 425, 100, 100)  // rectangle for duck 7
    )

    // ==========================================================
    // Paint methods
    // ==========================================================

    // Paint the quacking duck animation.
    private fun paintQuack(g: Graphics) {
        if (!duckPond.hasCurrentDuck || !quackTimer.isRunning) return

        g.color = Color.BLACK
        val quack: String = duckPond.currentDuck!!.quackBehavior.quackText
        g.font = Font("Verdana", Font.BOLD, 36)
        val fm = g.fontMetrics
        val totalWidth = fm.stringWidth(quack)
        g.drawString(quack, 400 - totalWidth / 2, 100)
        quackCounter += 100

        if (quackCounter > 2000) stopQuackAnimation()
    }

    private fun stopQuackAnimation() {
        quackTimer.stop()
        duckPond.currentDuck!!.swim()
        quackCounter = 0
        repaint()
    }

    // Paint the flying duck animation.
    private fun paintFlyingDuck(g: Graphics) {
        if (!duckPond.hasCurrentDuck) return

        g.color = duckPond.currentDuck!!.color
        g.fillRect(animationX, 100, 50, 25)

        if (animationX == 800) stopFlyAnimation()
    }

    private fun stopFlyAnimation() {
        flyTimer.stop()
        duckPond.currentDuck!!.swim()
        animationX = -50
        repaint()
    }

    // Paint a duck rectangle based on the state of the duck
    private fun paintDuck(g: Graphics, duck: Duck, rect: Rectangle) {

        // Paint the square with a border around it.
        g.color = if (isActive(duck)) Color.CYAN else Color.WHITE
        fillRect(g, rect)
        g.color = Color.GRAY
        fillRect(g, reduceRect(rect, 10))
        g.color = if (duckPond.selectedDucks.contains(duck)) duck.color else Color.WHITE
        fillRect(g, reduceRect(rect, 20))

        // Paint the text in the square.

        // The display string has form "name:bling1:bling2:bling3" – name is required; bling is optional
        val displayText = duck.display().split(":")
        val nameText = displayText.first()
        val blingText = displayText.drop(1).joinToString(separator = " ")


        // Print optional welcome/warning message
        if (duck.isOnDSWC && duck.state === State.WELCOMING) {
            if (duck.isFree) {
                drawCenteredTextInRectangle(g, color = Color.BLUE, text = "Welcome!", rectangle = rect, yOffset = 35)
            } else {
                drawCenteredTextInRectangle(g, color = Color.BLUE, text = "Beware!", rectangle = rect, yOffset = 35)
            }
            duck.swim()
        }

        // Print display text
        val textColor = if (duckPond.selectedDucks.contains(duck)) Color.WHITE else Color.BLACK
        drawCenteredTextInRectangle(g, color = textColor, text = nameText, rectangle = rect, yOffset = 55)

        // Draw the bling text (if any) in the lower portion of the square
        drawCenteredTextInRectangle(g, color = textColor, text = blingText, rectangle = rect, yOffset = 75)

        // If the duck is captured, put a red X in the lower right corner of the square.
        if (!duck.isFree) {
            drawCenteredTextInRectangle(g, color = Color.RED, text = "X", rectangle = rect, xOffset = 30, yOffset = 85)
        }

        // If the duck is in the welcome committee, put a red w in the lower right corner of the square.
        if (duck.isOnDSWC) {
            drawCenteredTextInRectangle(g, color = Color.CYAN, text = "W", rectangle = rect, xOffset = -30, yOffset = 85)
        }
    }

    private fun isActive(duck: Duck) = (duckPond.currentDuck == duck && (flyTimer.isRunning || quackTimer.isRunning))

    // paint the add-duck button (+) in the bottom left
    private fun paintAddDuckButton(g: Graphics) {
        val x = 30
        val y = 500
        g.color = Color.BLACK
        g.fillOval(x, y, 50, 50)
        g.color = Color.GRAY
        g.fillOval(x + 2, y + 2, 46, 46)
        g.color = Color.BLACK
        g.fillOval(x + 5, y + 5, 40, 40)
        g.color = Color.GRAY
        g.fillRect(x + 20, y + 10, 10, 30)
        g.fillRect(x + 10, y + 20, 30, 10)
    }

    public override fun paintComponent(g: Graphics) {

        // paint the background
        g.color = Color.GRAY
        g.fillRect(0, 0, width, height)

        // paint the ducks (up to 8 of them)

        // duck-rectangle-pair
        val duckRectPairList = duckPond.duckList.zip(rectangleList)

        duckRectPairList.forEach { (duck, rectangle) -> paintDuck(g, duck, rectangle) }

        // paint the add-duck button (+) in the bottom left
        paintAddDuckButton(g)

        // if one of the ducks is quacking, paint it
        paintQuack(g)

        // if one of the ducks is flying, paint it
        paintFlyingDuck(g)
    }

    private fun reduceRect(rect: Rectangle, amount: Int) =
            Rectangle(
                    rect.x + amount / 2,
                    rect.y + amount / 2,
                    rect.width - amount,
                    rect.height - amount
            )

    private fun drawCenteredTextInRectangle(
            g: Graphics,
            color: Color = Color.BLACK,
            font: Font = Font("Verdana", Font.BOLD, 14),
            text: String,
            rectangle: Rectangle,
            xOffset: Int = 0,
            yOffset: Int = 0) {
        g.color = color
        g.font = font
        val fm = g.fontMetrics
        val textWidth = fm.stringWidth(text)
        val xPos = rectangle.x + rectangle.width / 2 - textWidth / 2 + xOffset // centered (positive offset move it right)
        val yPos = rectangle.y + yOffset // top (positive offset move it down)
        g.drawString(text, xPos, yPos)
    }

    private fun fillRect(g: Graphics, rect: Rectangle) {
        g.fillRect(rect.x, rect.y, rect.width, rect.height)
    }
}
