package ducksim

object DuckFactory : Subject() {
    fun createDuck(duckType: String, starCount: Int, moonCount: Int, crossCount: Int): Duck {
        var duck: Duck = when (duckType) {
            "Mallard" -> MallardDuck()
            "Redhead" -> RedheadDuck()
            "Rubber" -> RubberDuck()
            "Decoy" -> DecoyDuck()
            "Goose" -> GooseDuck(Goose())
            else -> MallardDuck()
        }
        repeat(starCount) {
            duck = StarBling(duck)
        }
        repeat(moonCount) {
            duck = MoonBling(duck)
        }
        repeat(crossCount) {
            duck = CrossBling(duck)
        }
        notifyObservers()
        return duck
    }
}