package cz.cuni.mff.aspect.mario.controllers.ann.networks

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import ch.idsia.benchmark.mario.engine.generalization.MarioEntity
import java.lang.Exception

data class NetworkInputBuilder(
    private var tiles: Tiles? = null,
    private var entities: Entities? = null,
    private var mario: MarioEntity? = null,
    private var receptiveFieldRows: Int = 5,
    private var receptiveFieldColumns: Int = 5,
    private var receptiveFieldOffsetRows: Int = 0,
    private var receptiveFieldOffsetColumns: Int = 0
) {

    fun tiles(tiles: Tiles) = apply { this.tiles = tiles }
    fun entities(entities: Entities) = apply { this.entities = entities }
    fun mario(mario: MarioEntity) = apply { this.mario = mario }
    fun receptiveFieldSize(rows: Int, columns: Int) = apply { this.receptiveFieldRows = rows; this.receptiveFieldColumns = columns }
    fun receptiveFieldOffset(rows: Int, columns: Int) = apply { this.receptiveFieldOffsetRows = rows; this.receptiveFieldOffsetColumns = columns }

    // TODO: unit test this
    fun build(): DoubleArray {
        // TODO: throw nicer exceptions -_-
        if (this.tiles == null) throw Exception("Missing `tiles` in the NetworkInputBuilder")
        if (this.entities == null) throw Exception("Missing `entities` in the NetworkInputBuilder")
        if (this.mario == null) throw Exception("Missing `mario` in the NetworkInputBuilder")

        val flatTiles = this.createFlatTiles()
        val flatEntities = this.createFlatEntities()

        val inputLayerSize = this.getInputLayerSize()
        return DoubleArray(inputLayerSize) {
            if (it >= flatEntities.size) {
                flatTiles[it - flatEntities.size].toDouble()
            } else {
                flatEntities[it].toDouble()
            }
        }
    }

    private fun createFlatTiles(): IntArray {
        val flatTiles = IntArray(this.receptiveFieldRows * this.receptiveFieldColumns) { 0 }

        this.iterateOverReceptiveField { index, row, column ->
            val tileAtPosition = this.tiles!!.tileField[row][column]
            val tileCode = when (tileAtPosition.code) {
                -60 -> -1
                else -> tileAtPosition.code
            }
            flatTiles[index] = tileCode
        }

        return flatTiles
    }

    private fun createFlatEntities(): IntArray {
        val flatEntities = IntArray(this.receptiveFieldRows * this.receptiveFieldColumns) { 0 }

        this.iterateOverReceptiveField { index, row, column ->
            val entitiesAtPosition = this.entities!!.entityField[row][column]
            flatEntities[index] = if (entitiesAtPosition.size > 0) entitiesAtPosition[0].type.code else 0
        }

        return flatEntities
    }

    private fun iterateOverReceptiveField(callback: (Int, Int, Int) -> Unit) {
        val receptiveFieldRowMiddle: Int = this.receptiveFieldRows / 2
        val receptiveFieldColumnMiddle: Int = this.receptiveFieldColumns / 2
        val marioX = this.mario!!.egoCol
        val marioY = this.mario!!.egoRow

        for (index in 0 until this.receptiveFieldRows * this.receptiveFieldColumns) {
            val row = index / this.receptiveFieldRows - receptiveFieldRowMiddle + this.receptiveFieldOffsetRows
            val column = index % this.receptiveFieldColumns - receptiveFieldColumnMiddle + this.receptiveFieldOffsetColumns
            callback(index, marioY + row, marioX + column)
        }
    }

    private fun getInputLayerSize(): Int {
        return 2 * this.receptiveFieldRows * this.receptiveFieldColumns
    }

}