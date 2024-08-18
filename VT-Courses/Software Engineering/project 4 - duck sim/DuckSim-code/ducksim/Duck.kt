package ducksim

import java.awt.Color

abstract class Duck : Observer {

    // values that can be overridden
    open val defaultFlyBehavior: FlyBehavior = FlyWithWings()
    open val defaultQuackBehavior: QuackBehavior = QuackNormal()
    open val color: Color = Color.BLACK


    // variables that can be changed only by functions in this class
    var flyBehavior: FlyBehavior = defaultFlyBehavior
    var quackBehavior: QuackBehavior = defaultQuackBehavior
    var state = State.SWIMMING
        private set
    var isFree = true
        private set
    var isOnDSWC = false
        private set


    // function for setting the state back to its default (swimming)

    fun swim() {
        state = State.SWIMMING
    }

    // functions from the context menu

    fun fly() {
        state = flyBehavior.state
    }

    fun quack() {
        state = quackBehavior.state
    }

    fun doJoinDSCW() {
        DuckFactory.registerObserver(this@Duck)
        isOnDSWC = true
    }

    open val joinDSCW = object : DuckMenuItem {
        override fun invoke() {
            doJoinDSCW()
        }
    }


    fun doQuitDSCW() {
        DuckFactory.removeObserver(this@Duck)
        isOnDSWC = false
    }

    open val quitDSCW = object : DuckMenuItem {
        override fun invoke() {
            doQuitDSCW()
        }
    }

    fun doCapture() {
        isFree = false
        flyBehavior = FlyNoWay()
        quackBehavior = QuackNoWay()
    }

    open val capture = object : DuckMenuItem {
        override fun invoke() {
            doCapture()
        }
    }

    fun doRelease() {
        isFree = true
        flyBehavior = defaultFlyBehavior
        quackBehavior = defaultQuackBehavior
    }

    open val release = object : DuckMenuItem {
        override fun invoke() {
            doRelease()
        }
    }

    // abstract display function that must be implemented by concrete classes

    abstract fun display(): String

    //update function as an observer
    override fun update() {
        state = State.WELCOMING
    }


}
