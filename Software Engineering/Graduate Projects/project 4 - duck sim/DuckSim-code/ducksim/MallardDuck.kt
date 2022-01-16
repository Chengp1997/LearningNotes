package ducksim

import java.awt.Color

class MallardDuck : Duck() {

    override val color: Color = Color.GREEN

    override fun display() = "Mallard"
}
