package ducksim

import java.awt.Color

class RedheadDuck : Duck() {

    override val color: Color = Color.RED

    override fun display() = "Redhead"
}