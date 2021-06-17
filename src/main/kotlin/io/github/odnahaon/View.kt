package io.github.odnahaon

import io.github.odnahaon.*
import kotlinx.browser.window

/**
 * Class that handles changing the display on the game board. The game board
 * controls the graphics.
 *
 * @author noah
 */
class View(viewListener: ViewListener?) : GameBoardListener {
    private val listener: ViewListener? = viewListener

    // The array of colors and color counter.
    private val colors: Array<Color> = arrayOf<Color>(
        Color.WHITE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE,
        Color.PINK, Color.YELLOW
    )
    private val hitColors: Array<Color> = arrayOf<Color>(
        Color.RED, Color.WHITE
    )
    private val reflectColors: Array<Color> = arrayOf<Color>(
        Color.BLUE, Color.WHITE
    )
    private val numColors: Int = colors.size
    private var nextColor = 0

    val atomButtons = Array<Array<AtomJButton?>>(8) { arrayOfNulls<AtomJButton>(8) }
    val rayButtons = Array<Array<RayJButton?>>(4) { arrayOfNulls<RayJButton>(8) }

    /**
     * Get the next ray button color. It cycles to the start when it reaches the
     * end.
     *
     * @return The next color.
     */
    fun getNextColor(): Color {
        return colors[nextColor++ % numColors]
    }

    /**
     * Get the hit background and foreground colors.
     *
     * @return hitColors The two hit colors.
     */
    val hitColor: Array<Color>
        get() = hitColors

    /**
     * Get the reflect background and foreground colors.
     *
     * @return The two reflect colors.
     */
    val reflectColor: Array<Color>
        get() = reflectColors

    /**
     * Sets an atom's background color to green.
     *
     * @param pos the position of the target button
     */
    fun setAtomGreen(pos: Position?) {
        val x = pos?.x
        val y = pos?.y
        val element: String = "a$x$y"
        Color.changeColor(Color.GREEN, element)
    }

    /**
     * Sets an atom's background color to red.
     *
     * @param pos the position of the target button
     */
    fun setAtomRed(pos: Position) {
        val x = pos.x
        val y = pos.y
        val element: String = "a$x$y"
        Color.changeColor(Color.RED, element)
    }

    /**
     * Error message called when user attempts to place atom when four already
     * exist. Four is the current maximum.
     */
    fun atomSizeMessage() {
        js("alert('There is already the maximum amount of atoms (4)! Please delete one first.')")
    }

    /**
     * Error message called when user attempts to place a ray at a location where
     * one already exists.
     */
    fun rayInUseMessage() {
        js("alert('There is already a ray here! Please choose a different location.')")
    }

    /**
     * Error message called when user attempts to solve game with less than four
     * atoms.
     *
     * @return True is OK; false is cancel.
     */
    fun missingAtomsMessage(): Boolean {
        return js("return confirm('You have not guessed the maximum amount of atoms. Missing atoms will be counted as incorrect. Proceed?')") as Boolean
    }

    /**
     * Error message called when user attempts to quit with an unsolved game.
     *
     * @return True is OK; false is cancel.
     */
    fun quitMessage(): Boolean {
        return js("return confirm('You have not solved the game yet. Proceed to quit?')") as Boolean
    }

    /**
     * Error message called when user attempts to start new game with an unsolved
     * game.
     *
     * @return True is OK; false is cancel.
     */
    fun newGameNotSolved(): Boolean {
        return js("return confirm('You have not solved the game yet. Proceed to start new game?')") as Boolean
    }

    /**
     * Set all the atom buttons to default.
     */
    fun setAtomDefault() {
        for (x in 0..7) {
            for (y in 0..7) {
                setAtomButton(Position(x, y), false)
                val element: String = "a$x$y"
                Color.changeColor(Color.BLACK, element)
            }
        }
    }

    /**
     * Get the specified atom button state.
     *
     * @param pos grid position
     * @return The button state.
     */
    fun getAtom(pos: Position): Boolean? {
        return (atomButtons[pos.x][pos.y] as AtomJButton?)?.isAtom
    }

    /**
     * Set the specified atom button state.
     *
     * @param pos   atom position
     * @param state button atom state
     */
    fun setAtomButton(pos: Position, state: Boolean) {
        val button: AtomJButton? = atomButtons[pos.x][pos.y]
        val btnstr = button.toString()
        button?.setAtom(state)
        if (state) {
            js("document.getElementById(btnstr).src='resource/Atom.png'")
        } else {
            js("document.getElementById(btnstr).src='resource/Clear.png'")
        }
    }

    /**
     * Set all the ray buttons to default.
     */
    fun setRayDefault() {
        for (x in 0..3) {
            for (y in 0..7) {
                setRayData(Position(x, y), 0, Color.LIGHT_GRAY)
            }
        }
        nextColor = 0
    }

    /**
     * Set the specified ray button number and background color. If the number is
     * zero or less it resets the button to its default state.
     *
     * @param pos       ray position
     * @param number    ray number; zero or less clears the text
     * @param bgndColor background color; null sets to background color
     */
    fun setRayData(pos: Position?, number: Int, bgndColor: Color?) {
        rayButtons[pos!!.x][pos!!.y]?.setNumber(number, bgndColor ?: Color.LIGHT_GRAY)
    }

    // GameBoardListener methods.
    /**
     * Invoked when a atom button is pressed.
     *
     * @param button calling button
     */
    override fun atomButtonEvent(pos: Position, atomState: Boolean) {
        listener!!.atomButton(pos, atomState)
    }

    /**
     * Invoked when a ray button is pressed.
     *
     * @param button calling button
     */
    override fun rayButtonEvent(pos: Position?, num: Int) {
        listener!!.rayButton(pos, num)
    }

    /**
     * Invoked when the new game button is pressed.
     */
    override fun newGame() {
        listener!!.newGame()
    }

    /**
     * Invoked when the solve button is pressed.
     */
    override fun solve() {
        listener!!.solve()
    }

    /**
     * Set the score on the game board.
     *
     * @param score new score
     */
    fun setScore(score: Int) {
        js("document.getElementById('score').innerHTML = score")
    }

    /**
     * Reset the score on the game board.
     */
    fun resetScore() {
        js("document.getElementById('score').innerHTML = '0'")
    }

    /**
     * Creates the help window when the help button is pressed.
     */
    override fun help() {
        window.open("/blackboxhelp")
    }

    /**
     * Create and display the button GUI.
     *
     * @param viewListener listener for view events
     */
    init {
        // Create and display the form
        // board = GameBoard(this)
    }
}