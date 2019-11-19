package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.utils.MarioGameplayEvaluator
import cz.cuni.mff.aspect.evolution.utils.MarioGameplayEvaluators
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.TrainingLevelsSet
import cz.cuni.mff.aspect.evolution.utils.UpdatedGaussianMutator
import cz.cuni.mff.aspect.mario.controllers.ann.networks.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1Split
import cz.cuni.mff.aspect.storage.ObjectStorage
import io.jenetics.*
import io.jenetics.util.DoubleRange

fun main() {
    doManyEvolution()
}

fun doManyEvolution() {
    val learningLevels = arrayOf(*TrainingLevelsSet)
    val evaluationName = "All test - multi gaussian"

    val generationsCount = 50
    val populationSize = 50
    val fitness = MarioGameplayEvaluators::distanceOnly
    val mutators = arrayOf<Alterer<DoubleGene, Float>>(
        UpdatedGaussianMutator(0.25, 0.1),
        UpdatedGaussianMutator(0.15, 0.2),
        UpdatedGaussianMutator(0.05, 0.4),
        UpdatedGaussianMutator(0.01, 0.6)
    )
    //val mutators = arrayOf<Alterer<DoubleGene, Float>>(GaussianMutator(0.45))
    val hiddenLayerSize = 5

    val evolutions = arrayOf(
        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = fitness,
            objectiveFunction = MarioGameplayEvaluators::victoriesOnly,
            mutators = mutators,
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = generationsCount,
            populationSize = populationSize,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = hiddenLayerSize,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, experiment 1",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = fitness,
            objectiveFunction = MarioGameplayEvaluators::victoriesOnly,
            mutators = mutators,
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = generationsCount,
            populationSize = populationSize,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = hiddenLayerSize,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, experiment 2",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = fitness,
            objectiveFunction = MarioGameplayEvaluators::victoriesOnly,
            mutators = mutators,
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = generationsCount,
            populationSize = populationSize,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = hiddenLayerSize,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, experiment 3",
            runParallel = true,
            dataLocation = evaluationName
        ),

        NeuroEvolutionLauncher(
            levels = learningLevels,
            fitnessFunction = fitness,
            objectiveFunction = MarioGameplayEvaluators::victoriesOnly,
            mutators = mutators,
            survivorsSelector = EliteSelector(2),
            offspringSelector = TournamentSelector(2),
            generationsCount = generationsCount,
            populationSize = populationSize,
            receptiveFieldSize = Pair(5, 5),
            receptiveFieldOffset = Pair(0, 2),
            hiddenLayerSize = hiddenLayerSize,
            weightsRange = DoubleRange.of(-2.0, 2.0),
            label = "NeuroEvolution, experiment 4",
            runParallel = true,
            dataLocation = evaluationName
        )
    )

    evolutions.forEach {
        it.run()
    }
}


interface EvolutionLauncher {
    fun run()
}

class NeuroEvolutionLauncher(
    private val levels: Array<MarioLevel>,
    private val generationsCount: Int,
    private val populationSize: Int,
    private val receptiveFieldSize: Pair<Int, Int>,
    private val receptiveFieldOffset: Pair<Int, Int>,
    private val hiddenLayerSize: Int,
    private val label: String,
    private val fitnessFunction: MarioGameplayEvaluator<Float>,
    private val objectiveFunction: MarioGameplayEvaluator<Float>,
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
            generationsCount.toLong(),
            populationSize,
            chartLabel = label,
            mutators = mutators,
            survivorsSelector = survivorsSelector,
            offspringSelector = offspringSelector,
            weightsRange = weightsRange,
            parallel = runParallel
        )

        val resultController = controllerEvolution.evolve(levels, fitnessFunction, objectiveFunction)
        controllerEvolution.storeChart("experiments/$dataLocation/${label}_chart.svg")
        ObjectStorage.store("experiments/$dataLocation/${label}_ai.ai", resultController)
    }
}
