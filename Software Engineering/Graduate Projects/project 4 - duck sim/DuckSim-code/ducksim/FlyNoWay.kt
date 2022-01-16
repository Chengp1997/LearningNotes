package ducksim

class FlyNoWay : FlyBehavior {
    override val state: State
        get() = State.SWIMMING

}