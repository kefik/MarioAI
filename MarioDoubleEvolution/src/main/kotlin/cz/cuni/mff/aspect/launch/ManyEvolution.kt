package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.Fitness
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.fitnessOnlyDistance
import cz.cuni.mff.aspect.evolution.fitnessOnlyVictories
import cz.cuni.mff.aspect.mario.controllers.ann.networks.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1Split
import io.jenetics.*
import io.jenetics.util.DoubleRange

fun main() {
    doManyEvolution()
}

fun doManyEvolution() {
    val evaluationName = "First test evaluation"

    val evolutions = arrayOf(
        NeuroEvolutionLauncher(
            levels = arrayOf(*Stage1Level1Split.levels + PathWithHolesLevel + OnlyPathLevel),
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(Mutator(0.05)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 50,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution Stage 1 Level 1, Mutator 0.05",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = arrayOf(*Stage1Level1Split.levels + PathWithHolesLevel + OnlyPathLevel),
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(Mutator(0.10)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 50,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution Stage 1 Level 1, Mutator 0.10",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = arrayOf(*Stage1Level1Split.levels + PathWithHolesLevel + OnlyPathLevel),
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(Mutator(0.15)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 50,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution Stage 1 Level 1, Mutator 0.15",
            runParallel = true,
            dataLocation = evaluationName
        )
    )

    evolutions.forEach() {
        it.run()
    }
}


interface EvolutionLauncher {
    fun run()
}

class NeuroEvolutionLauncher(
    private val levels: Array<MarioLevel>,
    private val generationsCount: Long,
    private val populationSize: Int,
    private val receptiveFieldSize: Pair<Int, Int>,
    private val receptiveFieldOffset: Pair<Int, Int>,
    private val hiddenLayerSize: Int,
    private val label: String,
    private val fitnessFunction: Fitness<Float>,
    private val objectiveFunction: Fitness<Float>,
    private val mutators: Array<Alterer<DoubleGene, Float>>,
    private val survivorsSelector: Selector<DoubleGene, Float>,
    private val offspringSelector: Selector<DoubleGene, Float>,
    private val weightsRange: DoubleRange,
    private val runParallel: Boolean,
    private val dataLocation: String
) : EvolutionLauncher {

    override fun run() {
        val controllerANN = UpdatedAgentNetwork(
            receptiveFieldSize.first,
            receptiveFieldSize.second,
            receptiveFieldOffset.first,
            receptiveFieldOffset.second,
            hiddenLayerSize
        )

        val controllerEvolution = NeuroControllerEvolution(
            controllerANN,
            generationsCount,
            populationSize,
            chartLabel = label,
            alterers = mutators,
            survivorsSelector = survivorsSelector,
            offspringSelector = offspringSelector,
            weightsRange = weightsRange,
            parallel = runParallel
        )

        // TODO: store also controller
        val resultController = controllerEvolution.evolve(levels, fitnessFunction, objectiveFunction)
        controllerEvolution.storeChart("tests/$dataLocation/$label/chart.svg")
    }
}
