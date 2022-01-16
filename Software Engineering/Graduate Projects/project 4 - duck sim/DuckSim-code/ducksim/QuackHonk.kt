package ducksim

class QuackHonk : QuackBehavior {
    override val state: State
        get() = State.QUACKING

    override val quackText: String
        get() = Goose().honkText
}