package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.evolution.MarioGameplayEvaluators
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.networks.UpdatedAgentNetwork
import cz.cuni.mff.aspect.mario.level.MarioLevel
import cz.cuni.mff.aspect.mario.level.custom.OnlyPathLevel
import cz.cuni.mff.aspect.mario.level.custom.PathWithHolesLevel
import cz.cuni.mff.aspect.mario.level.original.Stage1Level1Split
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1
import cz.cuni.mff.aspect.mario.level.original.Stage4Level1Split
import io.jenetics.GaussianMutator
import kotlin.system.exitProcess


fun main() {
    evolveAI()
    exitProcess(0)
}


fun evolveAI() {
    val controllerANN = UpdatedAgentNetwork(5, 5, 0, 2, 20)
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution(controllerANN,
        50,
        50,
        chartLabel = "Neuroevolution Stage 1 Level 1 split",
        mutators = arrayOf(GaussianMutator(0.25))
    )
    val levels = emptyArray<MarioLevel>() + Stage1Level1Split.levels + PathWithHolesLevel + OnlyPathLevel
    val resultController = controllerEvolution.evolve(levels, MarioGameplayEvaluators::distanceOnly, MarioGameplayEvaluators::victoriesOnly)

    val marioSimulator = GameSimulator()

    levels.forEach {
        marioSimulator.playMario(resultController, it, true)
    }

}
