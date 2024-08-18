package ducksim

import java.awt.Color

//adapter target:duck adaptee:goose
class GooseDuck(val goose: Goose) : Duck() {

    override val color: Color
        get() = Color.BLUE

    override val defaultFlyBehavior: FlyBehavior
        get() = super.defaultFlyBehavior

    override val defaultQuackBehavior: QuackBehavior
        get() = QuackHonk()

    override fun display(): String {
        return goose.name
    }
}