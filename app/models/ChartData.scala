package models

case class ChartData(title: String, list: List[ChartMonthData]) {
    def toJson: String = {
        val d = list.map { data =>
            s"""
                {
                    "month": "${data.month}",
                    "value": "${data.value}"
                }
             """
        }.mkString(",")
        s"""
            {
                "title": "$title",
                "data": [$d]
            }
         """
    }
}
case class ChartMonthData(month: Int, value: Int)

object ChartData {
    def getData: ChartData = {
        ChartData(
            title = "title",
            list = List[ChartMonthData](
                ChartMonthData(1, 100),
                ChartMonthData(2, 50),
                ChartMonthData(3, 78),
                ChartMonthData(4, 55),
                ChartMonthData(5, 28),
                ChartMonthData(6, 60)
            )
        )
    }
}