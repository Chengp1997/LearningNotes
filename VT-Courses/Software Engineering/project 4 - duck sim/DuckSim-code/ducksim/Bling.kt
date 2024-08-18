package ducksim

import java.awt.Color

abstract class Bling(val duck: Duck) : Duck() {
    init {
        flyBehavior = duck.flyBehavior
        quackBehavior = duck.quackBehavior
    }

    override fun display(): String {
        return duck.display();
    }

    override val color: Color
        get() = duck.color

    override val defaultFlyBehavior: FlyBehavior = duck.defaultFlyBehavior

    override val defaultQuackBehavior: QuackBehavior = duck.defaultQuackBehavior

}