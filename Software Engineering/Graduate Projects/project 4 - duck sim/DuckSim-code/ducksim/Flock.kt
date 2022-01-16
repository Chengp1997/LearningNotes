package ducksim

class Flock(val children: MutableSet<Duck>) : Duck() {

    override val defaultQuackBehavior: QuackBehavior get() = QuackNoise()

    override fun display(): String {
        var string = "Flock"
        children.forEach {
            string += (":" + it.display().split(":").first().substring(0, 1))
        }
        return string
    }

    override val capture
        get() = object : DuckMenuItem {
            override fun invoke() {
                children.forEach {
                    it.doCapture()
                }
                doCapture()
            }
        }

    override val release
        get() = object : DuckMenuItem {
            override fun invoke() {
                children.forEach {
                    it.doRelease()
                }
                doRelease()
            }
        }

    override val joinDSCW
        get() = object : DuckMenuItem {
            override fun invoke() {
                children.forEach {
                    it.doJoinDSCW()
                }
                doJoinDSCW()
            }
        }

    override val quitDSCW
        get() = object : DuckMenuItem {
            override fun invoke() {
                children.forEach {
                    it.doQuitDSCW()
                }
                doQuitDSCW()
            }
        }


}