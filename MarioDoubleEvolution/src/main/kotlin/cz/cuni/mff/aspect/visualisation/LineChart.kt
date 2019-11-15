package cz.cuni.mff.aspect.visualisation

import org.knowm.xchart.*
import org.knowm.xchart.internal.series.Series
import org.knowm.xchart.style.Styler
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JFrame
import java.io.File


class LineChart(label: String = "Line chart", xLabel: String = "X", yLabel: String = "Y") {

    private val chart: XYChart = XYChartBuilder()
        .width(600)
        .height(480)
        .title(label)
        .xAxisTitle(xLabel)
        .yAxisTitle(yLabel)
        .build()

    private var series: MutableList<Series> = mutableListOf()
    private lateinit var chartUIPanel: XChartPanel<XYChart>

    init {
        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line
        chart.styler.isChartTitleVisible = true
        chart.styler.legendPosition = Styler.LegendPosition.InsideNW
        chart.styler.isLegendVisible = true
        chart.styler.markerSize = 16
    }

    fun renderChart() {
        javax.swing.SwingUtilities.invokeLater {
            val frame = JFrame("Chart")
            frame.layout = BorderLayout()

            this.chartUIPanel = XChartPanel(chart)
            frame.add(this.chartUIPanel, BorderLayout.CENTER)

            frame.pack()
            frame.isVisible = true
        }
    }

    fun updateChart(values: List<Triple<String, Color, List<Pair<Double, Double>>>>) {
        for ((seriesLabel, seriesColor, seriesData) in values) {
            val currentSeries = this.getOrCreateSeries(seriesLabel, seriesColor)

            val xData: DoubleArray = seriesData.map { it.first }.toDoubleArray()
            val yData: DoubleArray = seriesData.map { it.second }.toDoubleArray()

            this.chart.updateXYSeries(currentSeries.name, xData, yData, null)
        }

        if (this::chartUIPanel.isInitialized) {
            this.chartUIPanel.revalidate()
            this.chartUIPanel.repaint()
        }
    }

    fun save(path: String) {
        val fullPath = "data/$path"
        val directoryPath = fullPath.replaceAfterLast("/", "")

        val directory = File(directoryPath)
        if (!directory.exists()) directory.mkdirs()

        VectorGraphicsEncoder.saveVectorGraphic(this.chart, fullPath, VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
    }

    private fun getOrCreateSeries(label: String, color: Color): Series {
        val existingSeries = this.series.find { it.name == label }

        if (existingSeries != null) {
            return existingSeries
        }

        val newSeries = this.chart.addSeries(label, doubleArrayOf(0.0), doubleArrayOf(0.0))
        newSeries.marker = SeriesMarkers.NONE
        newSeries.lineColor = color
        this.series.add(newSeries)

        return newSeries
    }

}