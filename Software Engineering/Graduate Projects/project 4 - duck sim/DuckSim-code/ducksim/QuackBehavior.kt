package ducksim

interface QuackBehavior {
    val state: State
    val quackText: String
}