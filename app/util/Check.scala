package util

object Check {
    def isNumber(str: String): Boolean = str.matches("[0-9]+")
    def isDate(str: String): Boolean = str.matches("""\d{4}-\d{2}-\d{2}""")
}