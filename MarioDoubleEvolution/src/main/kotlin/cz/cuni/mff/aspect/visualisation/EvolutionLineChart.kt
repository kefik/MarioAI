package cz.cuni.mff.aspect.visualisation

class EvolutionLineChart(label: String = "Evolution", private val hideFirst: Int = 0) {

    private val lineChart = LineChart(label, "Generations", "Fitness")

    private val data: List<Pair<String, MutableList<Pair<Double, Double>>>> = listOf(
        Pair("Max fitness", mutableListOf(Pair(0.0, 0.0))),
        Pair("Average fitness", mutableListOf(Pair(0.0, 0.0))),
        Pair("Min fitness", mutableListOf(Pair(0.0, 0.0)))
    )

    fun show() {
        this.lineChart.renderChart()
    }

    fun update(generation: Int, maxFitness: Double, averageFitness: Double, minFitness: Double) {
        this.data[0].second.add(Pair(generation.toDouble(), maxFitness))
        this.data[1].second.add(Pair(generation.toDouble(), averageFitness))
        this.data[2].second.add(Pair(generation.toDouble(), minFitness))

        if (this.hideFirst > 0 && this.data[0].second.size > this.hideFirst) {
            val offset = this.hideFirst.coerceAtMost(this.data[0].second.size - this.hideFirst)
            val shownData = this.data.map { lineData -> Pair(lineData.first, lineData.second.subList(offset, lineData.second.size)) }
            this.lineChart.updateChart(shownData)
        } else {
            this.lineChart.updateChart(this.data)
        }
    }

}