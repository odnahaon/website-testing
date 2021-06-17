package io.github.odnahaon

/**
 * Listener interface so GameBoard can invoke methods on view.
 *
 * @author noah
 */
interface GameBoardListener {
    /**
     * Invoked when a atom button is pressed.
     *
     * @param x the x pos of the button
     * @param y the y pos of the button
     */
    fun atomButtonEvent(pos: Position)

    /**
     * Invoked when a ray button is pressed.
     *
     * @param ray the ray ID
     */
    fun rayButtonEvent(pos: Position?, num: Int)

    /**
     * Invoked when the user starts a new game.
     */
    fun newGame()

    /**
     * Invoked when the user solves a game.
     */
    fun solve()

    /**
     * Invoked when the user requests the help menu.
     */
    fun help()
}