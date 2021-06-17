package io.github.odnahaon

import io.github.odnahaon.Position

/**
 * Listener so View can invoke methods on Controller.
 *
 * @author noah
 */
interface ViewListener {
    /**
     * Invoked when a atom button is pressed.
     *
     * @param pos       button position in grid
     * @param atomState state of atom; default is false (no atom)
     */
    fun atomButton(pos: Position, atomState: Boolean)

    /**
     * Invoked when a ray button is pressed.
     *
     * @param pos    button position in direction and column
     * @param number current ray number; default is 0 (no ray assigned)
     */
    fun rayButton(pos: Position?, number: Int)

    /**
     * Invoked when the user starts a new game.
     */
    fun newGame()

    /**
     * Invoked when the user solves a game.
     */
    fun solve()
}