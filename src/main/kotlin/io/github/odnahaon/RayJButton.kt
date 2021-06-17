package io.github.odnahaon

import io.github.odnahaon.*

/**
 * Contains functions for the ray buttons.
 *
 * @author noah
 */
class RayJButton(row: Direction, col: Int) {
    private val pos: Position = Position(row, col)

    /**
     * Get the button number. Default is zero which means it is not set.
     *
     * @return The button number.
     */
    var number: Int
        private set

    /**
     * Get the button position.
     *
     * @return The button position.
     */
    val position: Position
        get() = pos

    /**
     * Set the button number. This could be caused by the user or an end. If
     * number greater than one it sets the button text to the number. If the color
     * is not null it sets the new background color.
     *
     * @param num    number to set
     * @param bColor new background color; may be null
     */
    fun setNumber(num: Int, bColor: Color?, element: String) {
        number = num
        if (num > 0) {
            js("document.getElementById(element).value=num")
            js("document.getElementById(element).style.color='black'")
        } else if (num == -1) {
            js("document.getElementById(element).value='H'")
            js("document.getElementById(element).style.color='white'")
        } else if (num == -2) {
            js("document.getElementById(element).value='R'")
            js("document.getElementById(element).style.color='white'")
        } else {
            js("document.getElementById(element).value=''")
        }
        if (bColor != null) {
            Color.changeColor(bColor, element)
        }
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 1L
    }

    /**
     * Ray button default number is zero which means it has not been used yet.
     *
     * @param row row position (NORTH, SOUTH, EAST, WEST)
     * @param col column position
     */
    init {
        number = 0
    }
}