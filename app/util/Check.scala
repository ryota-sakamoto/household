package util

object Check {
    def isNumber(str: String): Boolean = {
        str.matches("[0-9]+")
    }
}