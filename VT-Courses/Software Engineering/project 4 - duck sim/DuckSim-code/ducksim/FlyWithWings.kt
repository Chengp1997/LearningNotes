package ducksim

class FlyWithWings : FlyBehavior {
    override val state: State
        get() = State.FLYING

}