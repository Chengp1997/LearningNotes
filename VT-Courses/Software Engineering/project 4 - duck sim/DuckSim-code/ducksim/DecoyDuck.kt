package ducksim

import java.awt.Color

class DecoyDuck : Duck() {

    override val color: Color = Color.ORANGE

    override val defaultQuackBehavior: QuackBehavior
        get() = QuackNoWay()
    override val defaultFlyBehavior: FlyBehavior
        get() = FlyNoWay()

    override fun display() = "Decoy"
}
