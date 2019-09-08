package cz.cuni.mff.aspect.evolution.generator

import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.mario.Map
import cz.cuni.mff.aspect.mario.Tiles
import cz.cuni.mff.aspect.mario.GameSimulator
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import java.util.function.Function


class DirectEncodedEvolutionaryGenerator : EvolutionaryGenerator {

    private lateinit var bestChromosome: EvolutionResult<IntegerGene, Float>
    private lateinit var currentAgent: ControllerEvolution
    private lateinit var map: Map

    override fun generateMap(): Map {
        return this.map
    }

    override fun evolve(agent: ControllerEvolution) {
        this.currentAgent = agent
        val genotype = Genotype.of(IntegerChromosome.of(0, 1, MAP_WIDTH * MAP_HEIGHT))

        val engine: Engine<IntegerGene, Float> =
                Engine.builder(fitness, genotype)
                        .optimize(Optimize.MAXIMUM)
                        .populationSize(POPULATION_SIZE)
                        .alterers(SinglePointCrossover(0.2), Mutator(0.30))
                        .survivorsSelector(EliteSelector(2))
                        .offspringSelector(TournamentSelector(3))
                        .mapping { evolutionResult ->
                            println("new gen: ${evolutionResult.generation} (best fitness: ${evolutionResult.bestFitness})")
                            evolutionResult
                        }
                        .build()

        val result = engine.stream()
                .limit(GENERATIONS_COUNT)
                .collect(EvolutionResult.toBestEvolutionResult<IntegerGene, Float>())

        this.bestChromosome = result
    }

    private val fitness = Function<Genotype<IntegerGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(gt: Genotype<IntegerGene>): Float {
        val marioSimulator = GameSimulator()
        val generator = DirectEncodedEvolutionaryGenerator()
        generator.map = DirectEncodedMap.createFromGeneratorRepresentation(gt)

        // TODO: uncomment me
        // marioSimulator.playMario(this.currentAgent, MockEvolutionaryGenerator(), false)

        return marioSimulator.finalDistance
    }

    companion object {
        const val MAP_WIDTH = 64
        const val MAP_HEIGHT = 15

        const val POPULATION_SIZE = 20
        const val GENERATIONS_COUNT = 20L
    }
}

// TODO: this may be also called something like StaticMap
class DirectEncodedMap(private val tiles: Array<ByteArray>, private val enemies: Array<Array<Int>>) : Map {

    override fun getTiles(): Array<ByteArray> {
        return this.tiles
    }

    override fun getEnemies(): Array<Array<Int>> {
        return this.enemies
    }

    companion object {

        fun createFromGeneratorRepresentation(individual: Genotype<IntegerGene>): DirectEncodedMap {
            val alleles: List<Int> = individual.chromosome.toSeq().toList().map { it.allele }
            val tiles = Array(DirectEncodedEvolutionaryGenerator.MAP_WIDTH) { x ->
                ByteArray(DirectEncodedEvolutionaryGenerator.MAP_HEIGHT) { y ->
                    val index = x * DirectEncodedEvolutionaryGenerator.MAP_WIDTH + y
                    when (alleles[index]) {
                        0 -> Tiles.NOTHING
                        1 -> Tiles.DIRT
                        else -> Tiles.NOTHING
                    }
                }
            }

            return DirectEncodedMap(tiles, emptyArray())
        }
    }

}