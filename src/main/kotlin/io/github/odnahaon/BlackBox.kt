package io.github.odnahaon

/**
 * The Black Box game main class. It takes one optional argument for testing the
 * ray routing. When true the game does not generate atoms. Instead placing a
 * user atom on the grid also places it into the model as a computer generated
 * atom. This way the user can place up to four atoms and check the ray routing
 * of the game. The argument must be "test=true" to activate the ray routing
 * feature.
 *
 * @author noah
 */
object BlackBox {
    /**
     * Global package variables for user testing.
     */
    internal var TEST: Boolean = false

    /**
     * Global package variables for running the robot. This gets changed by the
     * robot class.
     */
    internal var ROBOT = false

    /**
     * The game invocation method.
     *
     * @param args optional "test=true" to activate ray route testing
     */
    fun main(args: Array<String>) {
        // Check for the testing argument.
//      if (args.size == 1 && args[0] == "test=true") {
//          TEST = true
//      }
        fun run() {
            Controller()
        }
    }
}