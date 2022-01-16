package ducksim

import java.awt.Color

class RubberDuck : Duck() {

    override val color: Color = Color.YELLOW
    override val defaultQuackBehavior: QuackBehavior
        get() = QuackSqueak()
    override val defaultFlyBehavior: FlyBehavior
        get() = FlyNoWay()

    override fun display() = "Rubber"
}