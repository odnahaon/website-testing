package io.github.odnahaon

/**
 * Controls what happens on the game board. Instructions are sent to class View
 * for displaying, class Model for storing and class Calculator for routing the
 * rays.
 *
 * @author noah
 */
class Controller : ViewListener {
    /**
     *
     */
    private var lock // True if solve in progress.
            : Boolean
    private val view: View = View(this)
    private val model: Model = Model()
    private val calculator: Calculator = Calculator()
    private var currNum: Int
    private val hitPos: Position = Position(-1, -1)
    private val reflectPos: Position = Position(-2, -2)

    /**
     * Adds or deletes an atom. If TEST active (see class BlackBoxRobot) user
     * placed atoms also become "generated" atoms.
     *
     * @param pos       where the atom is located
     * @param atomState true if atom in grid
     */
    override fun atomButton(pos: Position, atomState: Boolean) {
        if (lock) {
            return
        }

        // If TEST active user placed atoms also become "generated" atoms.
        val userAtoms: ArrayList<Position> = model.getUserAtoms()
        if (atomState) {
            model.deleteUserAtom(pos)
            if (BlackBox.TEST) {
                model.deleteAtom(pos)
                calculator.setAtom(pos, false)
            }
            view.setAtom(pos, false)
            return
        }
        if (userAtoms.size >= 4) {
            view.atomSizeMessage()
            return
        }
        model.setUserAtom(pos)
        if (BlackBox.TEST) {
            model.setAtom(pos)
            calculator.setAtom(pos, true)
        }
        view.setAtom(pos, true)
    }

    /**
     * Set the ray position and number. The number is displayed on the ray to help
     * the user identify where the ray entered and exited.
     *
     * @param pos    location of the ray
     * @param number number of the ray
     */
    override fun rayButton(pos: Position?, number: Int) {
        if (lock) {
            return
        }
        var upos: Array<Position?>
        val itr: MutableIterator<ArrayList<Position>> = model.getUserRays().iterator()
        while (itr.hasNext()) {
            upos = itr.next()
            if (pos != null) {
                if (pos.equals(upos[0]) || pos.equals(upos[1])) {
                    view.rayInUseMessage()
                    return
                }
            }
        }
        val apos: Position = calculator.calculateRays(pos)
        model.addUserRay(apos)
        val p: Array<Color>
        var score = 1
        if (hitPos.equals(apos[1])) {
            p = view.hitColor
            view.setRayData(pos, -1, p[0], p[1])
        } else if (reflectPos.equals(apos[1])) {
            p = view.reflectColor
            view.setRayData(pos, -2, p[0], p[1])
        } else {
            val color: Color = view.getNextColor()
            view.setRayData(apos[0], currNum, color)
            view.setRayData(apos[1], currNum, color)
            score++
            currNum++
        }
        view.setScore(model.increaseScore(score))
    }

    /**
     * Clears the board for a new game after checking whether it has been solved
     * and asking the user for confirmation if it has not been solved.
     */
    override fun newGame() {
        if (!lock) {
            if (!view.newGameNotSolved()) {
                return
            }
        }
        view.setAtomDefault()
        view.setRayDefault()
        view.resetScore()
        currNum = 1
        model.resetModel()
        // If the TEST and ROBOT are not active generate random atoms.
        if (!BlackBox.TEST && !BlackBox.ROBOT) {
            var list: ArrayList<Position?>?
            list = calculator.createAtoms()
            val itr = list.iterator()
            while (itr.hasNext()) {
                model.setAtom(itr.next())
            }
        } else {
            calculator.clearAtoms()
        }
        lock = false
    }

    /**
     * Solves the game, shows incorrect atom guesses, updates the score for each
     * incorrect guess, and locks the board so it cannot be messed with.
     */
    override fun solve() {
        if (lock) {
            return
        }
        val userAtoms: ArrayList<Position> = model.getUserAtoms()
        if (userAtoms.size != 4) {
            val result: Boolean = view.missingAtomsMessage()
            if (result) {
            } else {
                return
            }
        }
        model.increaseScore((4 - userAtoms.size) * 5)
        val atoms: ArrayList<Position> = model.getAtoms()
        var itr: Iterator<Position?> = atoms.iterator()
        val uitr: Iterator<Position> = userAtoms.iterator()
        var pos: Position
        var match: Boolean
        while (itr.hasNext()) {
            view.setAtomGreen(itr.next())
        }
        while (uitr.hasNext()) {
            pos = uitr.next()
            itr = atoms.iterator()
            match = false
            while (itr.hasNext()) {
                if (pos.equals(itr.next())) {
                    match = true
                    break
                }
            }
            if (!match) {
                view.setAtomRed(pos)
                model.increaseScore(5)
            }
        }
        view.setScore(model.getScore())
        lock = true
    }

    /**
     * Sets the "generated" atom to true. Used by the BlackBoxRobot class.
     *
     * @param pos atom position to set true
     */
    private fun setAtom(pos: Position) {
        model.setAtom(pos)
        calculator.setAtom(pos, true)
    }

    companion object {
        /**
         * Run the controller for testing purposes.
         *
         * @param args not used
         */
        fun main(args: Array<String?>?) {
            fun run() {
                Controller()
            }
        }
    }

    /**
     * Initializes important variables and starts a new game.
     */
    init {
        currNum = 1
        lock = true
        newGame()
    }
}