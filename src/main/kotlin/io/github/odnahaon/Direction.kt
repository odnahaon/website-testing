package io.github.odnahaon

/**
 * Direction location of the ray buttons row positions. They are:
 *
 *  * NORTH - 0
 *  * SOUTH - 1
 *  * EAST - 2
 *  * WEST - 3
 *  * INVALID - 4 (not a valid direction)
 *
 *
 * @author noah
 */
enum class Direction(
    /**
     * Value for the direction.
     */
    val value: Int
) {
    /**
     * Direction NORTH (up, negative y-value).
     */
    NORTH(0),

    /**
     * Direction SOUTH (down, positive y-value).
     */
    SOUTH(1),

    /**
     * Direction EAST (right, positive x-value).
     */
    EAST(2),

    /**
     * Direction WEST (left, negative x-value).
     */
    WEST(3),

    /**
     * Not a valid direction.
     */
    INVALID(4);

    companion object {
        /**
         * Convert the row number to a direction.
         *
         * @param row row number
         * @return direction or INVALID if row not in range
         */
        fun toDirection(row: Int): Direction {
            return when (row) {
                0 -> NORTH
                1 -> SOUTH
                2 -> EAST
                3 -> WEST
                else -> INVALID
            }
        }

        /**
         * Return the direction string.
         *
         * @param row row value to display
         * @return direction string
         */
        fun toString(row: Int): String {
            return when (row) {
                0 -> "NORTH"
                1 -> "SOUTH"
                2 -> "EAST"
                3 -> "WEST"
                else -> "Invalid direction value: $row"
            }
        }
    }
}