package ducksim

import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.AbstractAction
import javax.swing.Action
import javax.swing.JMenuItem
import javax.swing.SwingUtilities

class DuckSimController(val view: DuckSimView, val duckPond: DuckPond) : MouseListener {

    val popupListener: MouseListener
    val escapeAction: Action

    inner class EscapeAction : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            duckPond.selectedDucks.clear()
            view.repaint()
        }
    }

    inner class PopupListener : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) {
            maybeShowPopup(e)
        }

        override fun mouseReleased(e: MouseEvent) {
            maybeShowPopup(e)
        }

        private fun maybeShowPopup(e: MouseEvent) {
            if (animationIsRunning()) return

            if (e.isPopupTrigger) {

                // Do not show popup if *any* of the ducks are selected
                if (!duckPond.noSelectedDucks()) return

                // If the mouse click is *not* over a duck, return
                if (!aDuckWasClicked(e)) return

                // otherwise, get the duck
                val duckRectPairs = duckPond.duckList.zip(view.rectangleList)
                val clickedDuck = duckRectPairs.first { (_, rect) -> rect.contains(e.x, e.y) }.first
                duckPond.currentDuck = clickedDuck

                if (clickedDuck.state == State.SWIMMING) {

                    // Create all the menu items for the popup menus
                    view.initializePopup()

                    val flyMenuItem: JMenuItem = JMenuItem("Fly")
                    flyMenuItem.addActionListener {
                        processFlyAction(clickedDuck)
                    }
                    view.popup.add(flyMenuItem)

                    val quackMenuItem = JMenuItem("Quack")
                    quackMenuItem.addActionListener {
                        processQuackAction(clickedDuck)
                    }
                    view.popup.add(quackMenuItem)

                    val joinDSWCMenuItem = JMenuItem("Join DSWC")
                    joinDSWCMenuItem.addActionListener {
                        processBasicMenuAction(clickedDuck.joinDSCW)
                    }
                    if (!clickedDuck.isOnDSWC) {
                        view.popup.add(joinDSWCMenuItem)
                    }

                    val quitDSWCMenuItem = JMenuItem("Quit DSWC")
                    quitDSWCMenuItem.addActionListener {
                        processBasicMenuAction(clickedDuck.quitDSCW)
                    }
                    if (clickedDuck.isOnDSWC) {
                        view.popup.add(quitDSWCMenuItem)
                    }

                    val captureMenuItem = JMenuItem("Capture")
                    captureMenuItem.addActionListener {
                        processBasicMenuAction(clickedDuck.capture)
                    }
                    if (clickedDuck.isFree) {
                        view.popup.add(captureMenuItem)
                    }

                    val releaseMenuItem = JMenuItem("Release")
                    releaseMenuItem.addActionListener {
                        processBasicMenuAction(clickedDuck.release)
                    }
                    if (!clickedDuck.isFree) {
                        view.popup.add(releaseMenuItem)
                    }

                    val deleteMenuItem = JMenuItem("Delete")
                    deleteMenuItem.addActionListener {
                        duckPond.deleteDuck(clickedDuck)
                        view.repaint()
                    }
                    view.popup.add(deleteMenuItem)

                    view.popup.show(view, e.x, e.y)
                }
            }
        }

        private fun processBasicMenuAction(duckMenuItem: DuckMenuItem) {
            duckMenuItem.invoke()
            view.repaint()
        }

        private fun processQuackAction(clickedDuck: Duck) {
            clickedDuck.quack()
            if (clickedDuck.state === State.QUACKING) {
                view.quackTimer.start()
            }
            view.repaint()
        }

        private fun processFlyAction(clickedDuck: Duck) {
            clickedDuck.fly()
            if (clickedDuck.state === State.FLYING) {
                view.flyTimer.start()
            }
            view.repaint()
        }
    }

    private fun aDuckWasClicked(e: MouseEvent) =
            view.rectangleList.take(duckPond.duckList.size).any { it.contains(e.x, e.y) }

    private fun clickedDuck(e: MouseEvent): Duck {
        val duckRectPairs = duckPond.duckList.zip(view.rectangleList)
        return duckRectPairs.first { (_, rect) -> rect.contains(e.x, e.y) }.first
    }

    override fun mouseClicked(e: MouseEvent) {
        if (animationIsRunning()) return

        if (SwingUtilities.isLeftMouseButton(e)) {
            when {
                view.newDuckButtonRectangle.contains(e.x, e.y) -> {
                    NewDuckController(duckPond, view).createNewDuck()
                }
                aDuckWasClicked(e) -> {
                    duckPond.toggleSelection(clickedDuck(e))
                }
                else -> { /* do nothing */
                }
            }
            view.repaint()
        }
    }

    override fun mousePressed(e: MouseEvent) {
        //
    }

    override fun mouseReleased(e: MouseEvent) {
        //
    }

    override fun mouseEntered(e: MouseEvent) {
        //
    }

    override fun mouseExited(e: MouseEvent) {
        //
    }

    private fun animationIsRunning() = view.flyTimer.isRunning || view.quackTimer.isRunning

    init {
        popupListener = PopupListener()
        escapeAction = EscapeAction()
    }
}
