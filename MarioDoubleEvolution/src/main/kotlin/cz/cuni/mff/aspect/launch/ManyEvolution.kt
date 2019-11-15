package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.Fitness
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.fitnessDistanceLeastActions
import cz.cuni.mff.aspect.evolution.fitnessOnlyDistance
import cz.cuni.mff.aspect.evolution.fitnessOnlyVictories
import cz.cuni.mff.aspect.mario.controllers.ann.networks.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage2Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1Split
import cz.cuni.mff.aspect.storage.ObjectStorage
import io.jenetics.*
import io.jenetics.util.DoubleRange

fun main() {
    doManyEvolution()
}

fun doManyEvolution() {
    val learningLevels = arrayOf(*Stage4Level1Split.levels + PathWithHolesLevel + OnlyPathLevel)
    val evaluationName = "Gaussian test evaluation - Stage 4 Level Split"

    val evolutions = arrayOf(
        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(GaussianMutator(0.01)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 100,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, Mutator 0.01",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(GaussianMutator(0.05)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 100,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, Mutator 0.05",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(GaussianMutator(0.1)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 100,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, Mutator 0.1",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(GaussianMutator(0.25)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 100,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, Mutator 0.25",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = ::fitnessOnlyDistance,
            objectiveFunction = ::fitnessOnlyVictories,
            mutators = arrayOf(GaussianMutator(0.45)),
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = 100,
            populationSize = 50,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = 20,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, Mutator 0.45",
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
            mutators = mutators,
            survivorsSelector = survivorsSelector,
            offspringSelector = offspringSelector,
            weightsRange = weightsRange,
            parallel = runParallel
        )

        // TODO: store also controller
        val resultController = controllerEvolution.evolve(levels, fitnessFunction, objectiveFunction)
        controllerEvolution.storeChart("tests/$dataLocation/${label}_chart.svg")
        ObjectStorage.store("tests/$dataLocation/${label}_ai.ai", resultController)
    }
}
