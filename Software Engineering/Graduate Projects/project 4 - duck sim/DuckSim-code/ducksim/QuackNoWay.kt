package ducksim

class QuackNoWay : QuackBehavior {
    override val state: State
        get() = State.SWIMMING
    override val quackText: String
        get() = ""

}