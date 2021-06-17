package io.github.odnahaon

/**
 * The calculator engine. Uses the atom locations and starting ray position to
 * calculate the end ray state. It returns the positions as a two element array.
 * The first element is always the initial ray position. The second element is
 * either the exit position, a (-1, -1) position if the ray hit an atom or a
 * (-2, -2) position if the ray was reflected back to its start position.
 *
 * @author noah
 */
class Calculator {
    // Atom array and atom creation generator.
    private val atoms: Array<BooleanArray> = Array(8) { BooleanArray(8) }
    private val gen: Generator = Generator()
    private var currPos: Position? = null
    private val hitPos: Position = Position(-1, -1)
    private val rfltPos: Position = Position(-2, -2)

    // Defines the four corners for checking ray conversions.
    private val NWPos: Position = Position(0, 0)
    private val NEPos: Position = Position(7, 0)
    private val SWPos: Position = Position(0, 7)
    private val SEPos: Position = Position(7, 7)

    // The directions and current direction for movement.
    private enum class Move {
        LEFT, RIGHT, UP, DOWN
    }

    private var currDir: Move? = null

    /**
     * Set all the atoms in the array to false to clear it.
     */
    fun clearAtoms() {
        for (x in 0..7) {
            for (y in 0..7) {
                atoms[x][y] = false
            }
        }
    }

    /**
     * Create a list of four random atom positions. The X and Y positions range
     * from 0 to 7 to match the atom array size. There are no duplicate positions
     * in the atom list.
     *
     * @return The list of atoms.
     */
    fun createAtoms(): ArrayList<Position?> {
        val list: ArrayList<Position?> = ArrayList<Position?>()
        val pos: Array<Position?>?
        pos = gen.atoms
        list.add(pos[0])
        list.add(pos[1])
        list.add(pos[2])
        list.add(pos[3])
        clearAtoms()
        atoms[pos[0]!!.x][pos[0]!!.y] = true
        atoms[pos[1]!!.x][pos[1]!!.y] = true
        atoms[pos[2]!!.x][pos[2]!!.y] = true
        atoms[pos[3]!!.x][pos[3]!!.y] = true
        return list
    }

    /**
     * Calculate the ray path using the starting position and atom placements to
     * return the correct response in the two element array. The first element is
     * the initial position while the second element represents the exit position.
     * A hit is represented as a (-1, -1) position in the returned second array
     * element while a reflection is represented as a (-2, -2) position.
     *
     * @param pos starting position
     * @return The expected response as a two element array of positions.
     */
    fun calculateRays(pos: Position?): Array<Position?> {
        // Create the return array.
        val array: Array<Position?> = arrayOf(
            pos, null
        )
        if (pos != null) {
            when (Direction.toDirection(pos.x)) {
                Direction.NORTH -> {
                    currDir = Move.DOWN
                    currPos = Position(pos.y, 0)
                }
                Direction.SOUTH -> {
                    currDir = Move.UP
                    currPos = Position(pos.y, 7)
                }
                Direction.EAST -> {
                    currDir = Move.LEFT
                    currPos = Position(7, pos.y)
                }
                Direction.WEST -> {
                    currDir = Move.RIGHT
                    currPos = Position(0, pos.y)
                }
                Direction.INVALID -> {
                }
            }
        }

        // Checks before first move.
        if (checkFirstHit(currPos)) {
            array[1] = hitPos
            return array
        }
        if (checkFirstReflection(currPos)) {
            array[1] = rfltPos
            return array
        }

        // Check for hit, reflect and detour. Continue to move until run into a
        // grid boundary.
        do {
            if (checkHit()) {
                array[1] = hitPos
                return array
            }
            if (checkReflection()) {
                array[1] = rfltPos
                return array
            }
            checkDetour()
        } while (!move())

        // Done moving so set exit position and return.
        array[1] = currPos
        return array
    }

    /**
     * Move the location one step in the current direction. If the X or Y are
     * either 0 or 7 after the move the ray has reached a boundary. Hitting a
     * boundary ends the move and converts the point to a ray position.
     *
     * @return True if done moving; false otherwise.
     */
    private fun move(): Boolean {
        when (currDir) {
            Move.UP -> {
                if (currPos!!.y == 0) {
                    currPos = toRayPosition()
                    return true
                }
                currPos!!.y--
            }
            Move.DOWN -> {
                if (currPos!!.y == 7) {
                    currPos = toRayPosition()
                    return true
                }
                currPos!!.y++
            }
            Move.LEFT -> {
                if (currPos!!.x == 0) {
                    currPos = toRayPosition()
                    return true
                }
                currPos!!.x--
            }
            Move.RIGHT -> {
                if (currPos!!.x == 7) {
                    currPos = toRayPosition()
                    return true
                }
                currPos!!.x++
            }
            else -> {
            }
        }
        return false
    }

    /**
     * If it is at an atom boundary return the associated ray location for use in
     * the returned position array second element.
     *
     * @return Null if not at boundary and ray position if at boundary.
     */
    private fun toRayPosition(): Position? {
        if (currPos!!.equals(NWPos)) {
            when (currDir) {
                Move.UP -> return Position(Direction.NORTH, 0)
                Move.LEFT -> return Position(Direction.WEST, 0)
                else -> {
                }
            }
        } else if (currPos!!.equals(NEPos)) {
            when (currDir) {
                Move.UP -> return Position(Direction.NORTH, 7)
                Move.RIGHT -> return Position(Direction.EAST, 0)
                else -> {
                }
            }
        } else if (currPos!!.equals(SWPos)) {
            when (currDir) {
                Move.DOWN -> return Position(Direction.SOUTH, 0)
                Move.LEFT -> return Position(Direction.WEST, 7)
                else -> {
                }
            }
        } else if (currPos!!.equals(SEPos)) {
            when (currDir) {
                Move.DOWN -> return Position(Direction.SOUTH, 7)
                Move.RIGHT -> return Position(Direction.EAST, 7)
                else -> {
                }
            }
        }

        // Not in a corner so check each of the boundaries.
        return if (currPos!!.x == 0) {
            Position(Direction.WEST, currPos!!.y)
        } else if (currPos!!.x == 7) {
            Position(Direction.EAST, currPos!!.y)
        } else if (currPos!!.y == 0) {
            Position(Direction.NORTH, currPos!!.x)
        } else if (currPos!!.y == 7) {
            Position(Direction.SOUTH, currPos!!.x)
        } else {
            null
        }
    }

    /**
     * Check for an atom hit. A hit is when an atom is directly in front of the
     * position in the current direction of movement.
     *
     * @return True if found a hit; false otherwise.
     */
    private fun checkHit(): Boolean {
        return when (currDir) {
            Move.UP -> {
                if (currPos!!.y == 0) {
                    false
                } else atoms[currPos!!.x][currPos!!.y - 1]
            }
            Move.DOWN -> {
                if (currPos!!.y == 7) {
                    false
                } else atoms[currPos!!.x][currPos!!.y + 1]
            }
            Move.LEFT -> {
                if (currPos!!.x == 0) {
                    false
                } else atoms[currPos!!.x - 1][currPos!!.y]
            }
            Move.RIGHT -> {
                if (currPos!!.x == 7) {
                    false
                } else atoms[currPos!!.x + 1][currPos!!.y]
            }
            else -> false
        }
    }

    /**
     * Check for an atom first hit. A hit is when an atom is directly in front of
     * the position in the current direction of movement.
     *
     * @param pos current position
     * @return True if found a hit; false otherwise.
     */
    private fun checkFirstHit(pos: Position?): Boolean {
        return atoms[pos!!.x][pos.y]
    }

    /**
     * Check for an atom reflection. A reflection is when there are two atoms on
     * each diagonal in front of the position in the current direction of
     * movement. If there is also an atom directly in front of the position it is
     * a hit and takes precedence over the reflection.
     *
     * @return True if found a reflection; false otherwise.
     */
    private fun checkReflection(): Boolean {
        when (currDir) {
            Move.UP -> if (currPos!!.y > 0 && currPos!!.x > 0 && currPos!!.x < 7
                && atoms[currPos!!.x + 1][currPos!!.y - 1]
                && atoms[currPos!!.x - 1][currPos!!.y - 1]
            ) {
                return true
            }
            Move.DOWN -> if (currPos!!.y < 7 && currPos!!.x > 0 && currPos!!.x < 7
                && atoms[currPos!!.x + 1][currPos!!.y + 1]
                && atoms[currPos!!.x - 1][currPos!!.y + 1]
            ) {
                return true
            }
            Move.LEFT -> if (currPos!!.x > 0 && currPos!!.y > 0 && currPos!!.y < 7
                && atoms[currPos!!.x - 1][currPos!!.y + 1]
                && atoms[currPos!!.x - 1][currPos!!.y - 1]
            ) {
                return true
            }
            Move.RIGHT -> if (currPos!!.x < 7 && currPos!!.y > 0 && currPos!!.y < 7
                && atoms[currPos!!.x + 1][currPos!!.y + 1]
                && atoms[currPos!!.x + 1][currPos!!.y - 1]
            ) {
                return true
            }
            else -> return false
        }
        return false
    }

    /**
     * Check for an atom first reflection. For the starting position on an atom
     * grid boundary a reflection is when an atom is diagonal in front of the
     * position in the current direction of movement. If there is also an atom
     * directly in front of the position it is a hit which should takes precedence
     * over the reflection.
     *
     * @param pos current position
     * @return True if found a reflection; false otherwise.
     */
    private fun checkFirstReflection(pos: Position?): Boolean {
        when (currDir) {
            Move.UP, Move.DOWN -> {
                if (pos!!.x > 0 && atoms[pos.x - 1][pos.y]) {
                    return true
                }
                if (pos.x < 7 && atoms[pos.x + 1][pos.y]) {
                    return true
                }
            }
            Move.LEFT, Move.RIGHT -> {
                if (pos!!.y > 0 && atoms[pos.x][pos.y - 1]) {
                    return true
                }
                if (pos.y < 7 && atoms[pos.x][pos.y + 1]) {
                    return true
                }
            }
            else -> return false
        }
        return false
    }

    /**
     * Check for a detour direction change.
     */
    private fun checkDetour() {
        when (currDir) {
            Move.UP -> {
                if (currPos!!.y > 0 && currPos!!.x < 7
                    && atoms[currPos!!.x + 1][currPos!!.y - 1]
                ) {
                    currDir = Move.LEFT
                }
                if (currPos!!.y > 0 && currPos!!.x > 0
                    && atoms[currPos!!.x - 1][currPos!!.y - 1]
                ) {
                    currDir = Move.RIGHT
                }
            }
            Move.DOWN -> {
                if (currPos!!.y < 7 && currPos!!.x < 7
                    && atoms[currPos!!.x + 1][currPos!!.y + 1]
                ) {
                    currDir = Move.LEFT
                }
                if (currPos!!.y < 7 && currPos!!.x > 0
                    && atoms[currPos!!.x - 1][currPos!!.y + 1]
                ) {
                    currDir = Move.RIGHT
                }
            }
            Move.LEFT -> {
                if (currPos!!.y < 7 && currPos!!.x > 0
                    && atoms[currPos!!.x - 1][currPos!!.y + 1]
                ) {
                    currDir = Move.UP
                }
                if (currPos!!.y > 0 && currPos!!.x > 0
                    && atoms[currPos!!.x - 1][currPos!!.y - 1]
                ) {
                    currDir = Move.DOWN
                }
            }
            Move.RIGHT -> {
                if (currPos!!.y > 0 && currPos!!.x < 7
                    && atoms[currPos!!.x + 1][currPos!!.y - 1]
                ) {
                    currDir = Move.DOWN
                }
                if (currPos!!.y < 7 && currPos!!.x < 7
                    && atoms[currPos!!.x + 1][currPos!!.y + 1]
                ) {
                    currDir = Move.UP
                }
            }
            else -> {
            }
        }
    }

    /**
     * It clears the atom array and sets an arbitrary number of atom positions.
     * This is used by the unit test to setup the atom grid.
     *
     * @param pos atom positions
     */
    fun setAtoms(pos: Array<Position>) {
        clearAtoms()
        for (i in pos.indices) {
            atoms[pos[i].x][pos[i].y] = true
        }
    }

    /**
     * It sets an atom position. Used during user testing.
     *
     * @param pos   atom position
     * @param state atom state
     */
    fun setAtom(pos: Position, state: Boolean) {
        atoms[pos.x][pos.y] = state
    }

}