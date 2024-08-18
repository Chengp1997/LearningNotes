package ducksim

class NewDuckController(val duckPond: DuckPond, val view: DuckSimView) {

    fun createNewDuck() {
        if (duckPond.noSelectedDucks()) {
            val makeDuckDialog = MakeDuckDialog(duckPond, view)
            makeDuckDialog.setSize(300, 200)
            makeDuckDialog.isVisible = true
        } else {
            val ducks = duckPond.selectedDucks.toMutableSet()
            var hasFlock: Boolean? = false
            //flock can not creat flock
            ducks.forEach {
                if (it is Flock) {
                    hasFlock = true
                    return
                }
            }
            if (!hasFlock!!) {
                val flock = Flock(ducks)
                duckPond.addNewDuck(flock)
            }
            view.repaint()

        }
    }
}