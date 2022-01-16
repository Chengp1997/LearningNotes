package ducksim

class MoonBling(duck: Duck) : Bling(duck) {
    override fun display(): String {
        return super.display() + ":)"
    }


}