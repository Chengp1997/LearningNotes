package ducksim

class CrossBling(duck: Duck) : Bling(duck) {
    override fun display(): String {
        return super.display() + ":+"
    }

}