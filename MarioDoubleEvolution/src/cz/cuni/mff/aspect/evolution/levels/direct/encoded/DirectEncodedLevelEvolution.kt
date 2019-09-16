package cz.cuni.mff.aspect.evolution.levels.direct.encoded

import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.extensions.getIntValues
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.controllers.MarioController
import cz.cuni.mff.aspect.mario.level.DirectMarioLevel
import cz.cuni.mff.aspect.mario.level.MarioLevel
import io.jenetics.*
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import java.util.function.Function


/**
 * Evolution of mario level, using GA and direct level encoding. Evolves only tiles, not enemies.
 */
class DirectEncodedLevelEvolution : LevelEvolution {

    private lateinit var controller: MarioController

    override fun evolve(controller: MarioController): Array<MarioLevel> {
        this.controller = controller
        val genotype = this.createInitialGenotype()
        val evolutionEngine = this.createEvolutionEngine(genotype)
        val result = this.doEvolution(evolutionEngine)

        val level = DirectMarioLevel.createFromTilesArray(LEVEL_WIDTH, LEVEL_HEIGHT, result.bestPhenotype.genotype.getIntValues())
        return arrayOf(level)
    }

    private fun createInitialGenotype(): Genotype<IntegerGene> {
        return Genotype.of(IntegerChromosome.of(0, 1, LEVEL_WIDTH * LEVEL_HEIGHT))
    }

    private fun createEvolutionEngine(initialGenotype: Genotype<IntegerGene>): Engine<IntegerGene, Float> {
        return Engine.builder(fitness, initialGenotype)
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
    }

    private fun doEvolution(evolutionEngine: Engine<IntegerGene, Float>): EvolutionResult<IntegerGene, Float> {
        return evolutionEngine.stream()
            .limit(GENERATIONS_COUNT)
            .collect(EvolutionResult.toBestEvolutionResult<IntegerGene, Float>())
    }

    private val fitness = Function<Genotype<IntegerGene>, Float> { genotype -> fitness(genotype) }
    private fun fitness(genotype: Genotype<IntegerGene>): Float {
        val marioSimulator = GameSimulator()
        val level = DirectMarioLevel.createFromTilesArray(LEVEL_WIDTH, LEVEL_HEIGHT, genotype.getIntValues())
        val agent = MarioAgent(this.controller)

        marioSimulator.playMario(agent, level, false)

        return marioSimulator.finalDistance
    }

    companion object {
        const val LEVEL_WIDTH = 64
        const val LEVEL_HEIGHT = 15

        const val POPULATION_SIZE = 40
        const val GENERATIONS_COUNT = 150L
    }
}
