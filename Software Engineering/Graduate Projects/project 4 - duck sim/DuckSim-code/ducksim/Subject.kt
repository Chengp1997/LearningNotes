package ducksim

abstract class Subject {
    private val observers = mutableSetOf<Observer>()

    fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        observers.forEach {
            it.update()
        }
    }
}