package cz.cuni.mff.aspect.visualisation

class EvolutionLineChart {

    private val lineChart = LineChart()

    private val data: List<Pair<String, MutableList<Pair<Double, Double>>>> = listOf(
        Pair("Max fitness", mutableListOf()),
        Pair("Average fitness", mutableListOf()),
        Pair("Min fitness", mutableListOf())
    )

    fun show() {
        this.lineChart.renderChart()
    }

    fun update(generation: Int, maxFitness: Double, averageFitness: Double, minFitness: Double) {
        this.data[0].second.add(Pair(maxFitness, generation.toDouble()))
        this.data[1].second.add(Pair(averageFitness, generation.toDouble()))
        this.data[1].second.add(Pair(minFitness, generation.toDouble()))

        this.lineChart.updateChart(this.data)
    }

}