package cz.cuni.mff.aspect.launch

import cz.cuni.mff.aspect.coevolution.MarioCoEvolver
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.levels.LevelEvolution
import cz.cuni.mff.aspect.evolution.levels.mock.MockLevelEvolution
import cz.cuni.mff.aspect.mario.GameSimulator
import cz.cuni.mff.aspect.mario.controllers.ann.UpdatedAgentNetwork


fun main() {
    coevolution()
}


fun coevolution() {
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution(UpdatedAgentNetwork())
    val levelEvolution: LevelEvolution = MockLevelEvolution()

    val evolution = MarioCoEvolver()
    val evolutionResult = evolution.evolve(controllerEvolution, levelEvolution)

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(evolutionResult.controller, evolutionResult.levels.first(), true)
}
