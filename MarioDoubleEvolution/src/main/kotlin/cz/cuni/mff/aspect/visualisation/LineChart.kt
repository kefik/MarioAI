package cz.cuni.mff.aspect.visualisation

import org.knowm.xchart.XChartPanel
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries
import org.knowm.xchart.internal.series.Series
import org.knowm.xchart.style.Styler
import java.awt.BorderLayout
import javax.swing.JFrame


class LineChart {

    private val chart: XYChart = XYChartBuilder()
        .width(600)
        .height(480)
        .title("Line chart")
        .xAxisTitle("X")
        .yAxisTitle("Y")
        .build()

    private var series: List<Series> = mutableListOf()
    private lateinit var chartUIPanel: XChartPanel<XYChart>

    init {
        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line
        chart.styler.isChartTitleVisible = true
        chart.styler.legendPosition = Styler.LegendPosition.InsideSE
        chart.styler.markerSize = 16
    }

    fun renderChart() {
        javax.swing.SwingUtilities.invokeLater {
            val frame = JFrame("Chart")
            frame.layout = BorderLayout()
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

            val chartPanel = XChartPanel(chart)
            frame.add(chartPanel, BorderLayout.CENTER)

            frame.pack()
            frame.isVisible = true
        }
    }

    fun updateChart(values: List<Pair<String, List<Pair<Double, Double>>>>) {
        for ((seriesLabel, seriesData) in values) {
            val currentSeries = this.getOrCreateSeries(seriesLabel)

            val xData: DoubleArray = seriesData.map { it.first }.toDoubleArray()
            val yData: DoubleArray = seriesData.map { it.first }.toDoubleArray()

            this.chart.updateXYSeries(currentSeries.name, xData, yData, null)

            if (!this::chartUIPanel.isInitialized) {
                return
            }

            this.chartUIPanel.revalidate()
            this.chartUIPanel.repaint()
        }
    }

    private fun getOrCreateSeries(label: String): Series {
        val existingSeries = this.series.find { it.name == label }
        return existingSeries ?: this.chart.addSeries(label, doubleArrayOf(), doubleArrayOf())
    }

}