package io.github.odnahaon

/**
 * Contains functions for managing the position of the atoms and rays.
 *
 * @author noah
 */
class Position {
    /**
     * Position X value (going from left the right).
     */
    var x: Int

    /**
     * Position Y value (going from top to bottom).
     */
    var y: Int

    // Determines position display format.
    private var useDirection = false

    /**
     * The position as X and Y. Once set the values cannot be changed.
     *
     * @param x X value
     * @param y Y value
     */
    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    /**
     * The position as direction row and column. Once set the values cannot be
     * changed.
     *
     * @param row row value
     * @param col column value
     */
    constructor(row: Direction, col: Int) {
        x = row.value
        y = col
        useDirection = true
    }

    constructor(pos: Position) {
        x = pos.x
        y = pos.y
    }

    /**
     * Compares the values of the two positions and not the objects.
     *
     * @param o object to compare with
     * @return true if positions are identical
     */
    fun equals(o: Position?): Boolean {
        if (o === this) {
            return true
        }
        if (o !is Position) {
            return false
        }
        val pos = o as Position?
        return x == pos!!.x && y == pos.y
    }

    /**
     * Compares hashes of the position values.
     *
     * @return true if positions are identical
     */
//    fun hashCode(): Int {
//        return Objects.hash(x, y)
//    }

    /**
     * Return the positions as a string.
     *
     * @return the positions
     */
    override fun toString(): String {
        return "$x$y"
    }

}