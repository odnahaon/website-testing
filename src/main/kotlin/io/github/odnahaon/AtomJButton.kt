package io.github.odnahaon

/**
 * Contains functions for the atom buttons.
 *
 * @author noah
 */
class AtomJButton(x: Int, y: Int) {

    private var pos: Position = Position(x, y)
    var atomState: Boolean = false

    /**
     * Creates an atom button at the position specified. Default visiblity (state)
     * is false.
     *
     * @param x X position
     * @param y Y position
     */
    fun AtomJButton(x: Int, y: Int) {
        pos = Position(x, y)
        atomState = false
    }

    /**
     * Get the atom button's grid position.
     *
     * @return pos the buttons grid position
     */
    val position: Position
        get() = pos

    /**
     * Returns whether the atom is visible.
     *
     * @return true if atom in grid
     */
    val isAtom: Boolean
        get() = atomState

    /**
     * Sets whether the atom is visible.
     *
     * @param atomState true places atom in grid
     */
    fun setAtom(atomState: Boolean) {
        this.atomState = atomState;
    }

    companion object {
        private const val serialVersionUID = 1L
    }

}