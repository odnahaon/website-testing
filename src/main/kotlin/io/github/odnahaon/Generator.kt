package io.github.odnahaon

/**
 * Class to handle generating random numbers.
 *
 * @author noah
 */
class Generator {
    /**
     * Generates a random Position between 0 and 7
     *
     * @return pos a random Position
     */
    private fun generate(): Position {

        // Get random integer in range
        val randInRange1: Int = (0..7).random()
        val randInRange2: Int = (0..7).random()
        return Position(randInRange1, randInRange2)
    }

    /**
     * Uses generate() to generate four random positions between 0 and 7 and
     * checks to make sure each position group is different.
     *
     * @return listOfAtoms four random positions between 0 and 7
     */
    val atoms: Array<Position?>
        get() {
            val listOfAtoms: Array<Position?> = arrayOfNulls(4)
            var count = 0
            var invalid: Boolean
            var pos: Position
            while (count < 4) {
                invalid = false
                pos = generate()
                for (i in 0 until count) {
                    if (pos.equals(listOfAtoms[i])) {
                        invalid = true
                    }
                }
                if (!invalid) {
                    listOfAtoms[count++] = pos
                }
            }
            return listOfAtoms
        }

    companion object {
        /**
         * Launch the application.
         *
         * @param args
         */
        fun main(args: Array<String?>?) {
            val gen = Generator()
            val pos: Array<Position?>?
            pos = gen.atoms
            println(pos[0].toString() + " " + pos[1] + " " + pos[2] + " " + pos[3])
        }
    }
}