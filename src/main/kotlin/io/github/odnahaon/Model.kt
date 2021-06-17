package io.github.odnahaon

/**
 * Contains variables to store information as the game is played and functions to
 * get and modify the variables.
 *
 * @author noah
 */
class Model {

    // Fields

    /**
     * Running score.
     */
    private var score = 0

    /**
     * Atoms set by the game.
     */
    private var atoms: ArrayList<Position> = ArrayList()

    /**
     * Two-dimensional array of ray positions, start and end.
     */
    private var userRays: ArrayList<Position> = ArrayList()

    /**
     * Atoms set by the user.
     */
    private var userAtoms: ArrayList<Position> = ArrayList()

    // Other methods

    /**
     * Increases the score and returns it.
     *
     * @return int
     * @param value The value to increase the score by.
     */
    fun increaseScore(value: Int): Int {
        score += value
        return score
    }

    /**
     * Gets the current score.
     *
     * @return int
     */
    fun getScore(): Int {
        return score
    }

    /**
     * Add a user ray on the grid.
     *
     * @param pos The position to add the ray to.
     */
    fun addUserRay(pos: Position) {
        userRays.add(pos)
    }

    /**
     * Resets the score and clears the array lists of atoms and rays.
     */
    fun resetModel() {
        atoms.clear()
        userAtoms.clear()
        userRays.clear()
        score = 0
    }

    /**
     * Sets a new atom on the game board.
     *
     * @param pos The position to add the atom to.
     */
    fun setAtom(pos: Position?) {
        if (pos != null) {
            atoms.add(pos)
        }
    }

    /**
     * Deletes an atom on the game board.
     *
     * @param pos The position to delete the atom from.
     */
    fun deleteAtom(pos: Position) {
        val itr: MutableIterator<Position> = atoms.iterator()
        while (itr.hasNext()) {
            val i: Position = itr.next()
            if (i === pos) {
                itr.remove()
            }
        }
    }

    /**
     * Sets a new user atom guess on the game board.
     *
     * @param pos
     */
    fun setUserAtom(pos: Position?) {
        if (pos != null) {
            userAtoms.add(pos)
        }
    }

    /**
     * Iterator of user-placed atoms.
     *
     * @return ArrayList
     */
    fun getUserAtoms(): ArrayList<Position> {
        return userAtoms
    }

    /**
     * Gets the game atoms.
     *
     * @return ArrayList
     */
    fun getAtoms(): ArrayList<Position> {
        return atoms
    }

    /**
     * Iterator of user-placed rays.
     *
     * @return ArrayList
     */
    fun getUserRays(): ArrayList<Position> {
        return userRays
    }

    /**
     * Deletes a user atom guess on the game board.
     *
     * @param pos
     */
    fun deleteUserAtom(pos: Position) {
        val itr: MutableIterator<Position> = userAtoms.iterator()
        while (itr.hasNext()) {
            val i: Position = itr.next()
            if (i === pos) {
                itr.remove()
            }
        }
    }
}