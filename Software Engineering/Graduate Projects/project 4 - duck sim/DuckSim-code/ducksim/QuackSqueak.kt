package ducksim

class QuackSqueak : QuackBehavior {
    override val state: State
        get() = State.QUACKING
    override val quackText: String
        get() = "Squeak!"

}