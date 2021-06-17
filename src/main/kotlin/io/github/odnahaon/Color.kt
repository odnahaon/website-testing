package io.github.odnahaon

/**
 * A substitute for the java.awt.Color class which doesn't have a Kotlin equivalent.
 * Only includes the very basics that we need, namely the different colors.
 *
 * @author noah
 */
enum class Color(
    /**
     * Value for the color.
     */
    val value: Int
) {
    BLACK(0),
    BLUE(1),
    CYAN(2),
    DARK_GRAY(3),
    GRAY(4),
    GREEN(5),
    LIGHT_GRAY(6),
    MAGENTA(7),
    ORANGE(8),
    PINK(9),
    RED(10),
    WHITE(11),
    YELLOW(12),

    /**
     * Not a valid color.
     */
    INVALID(13);

    companion object {
        /**
         * Convert the row number to a direction.
         *
         * @param color color number
         * @return color or INVALID if row not in range
         */
        fun toColor(color: Int): Color {
            return when (color) {
                0 -> BLACK
                1 -> BLUE
                2 -> CYAN
                3 -> DARK_GRAY
                4 -> GRAY
                5 -> GREEN
                6 -> LIGHT_GRAY
                7 -> MAGENTA
                8 -> ORANGE
                9 -> PINK
                10 -> RED
                11 -> WHITE
                12 -> YELLOW
                else -> INVALID
            }
        }

        /**
         * Return the direction string.
         *
         * @param color color value to display
         * @return color string
         */
        fun toString(color: Int): String {
            return when (color) {
                0 -> "BLACK"
                1 -> "BLUE"
                2 -> "CYAN"
                3 -> "DARK_GRAY"
                4 -> "GRAY"
                5 -> "GREEN"
                6 -> "LIGHT_GRAY"
                7 -> "MAGENTA"
                8 -> "ORANGE"
                9 -> "PINK"
                10 -> "RED"
                11 -> "WHITE"
                12 -> "YELLOW"
                else -> "Invalid color value: $color"
            }
        }

        /**
         * Change the element color.
         */
        fun changeColor(color: Color, element: String) {
            when (color) {
                BLACK -> js("document.getElementById(element).style.backgroundColor = \"#000000\"")
                BLUE -> js("document.getElementById(element).style.backgroundColor = \"#0000FF\"")
                CYAN -> js("document.getElementById(element).style.backgroundColor = \"#00FFFF\"")
                DARK_GRAY -> js("document.getElementById(element).style.backgroundColor = \"#404040\"")
                GRAY -> js("document.getElementById(element).style.backgroundColor = \"#808080\"")
                GREEN -> js("document.getElementById(element).style.backgroundColor = \"#00FF00\"")
                LIGHT_GRAY -> js("document.getElementById(element).style.backgroundColor = \"#C0C0C0\"")
                MAGENTA -> js("document.getElementById(element).style.backgroundColor = \"#FF00FF\"")
                ORANGE -> js("document.getElementById(element).style.backgroundColor = \"#FFC800\"")
                PINK -> js("document.getElementById(element).style.backgroundColor = \"#FFAFAF\"")
                WHITE -> js("document.getElementById(element).style.backgroundColor = \"#FFFFFF\"")
                YELLOW -> js("document.getElementById(element).style.backgroundColor = \"#FFFF00\"")
                else -> {
                    js("console.log(\"Error: bad color input when changing color of button.\")")
                }
            }
        }
    }
}