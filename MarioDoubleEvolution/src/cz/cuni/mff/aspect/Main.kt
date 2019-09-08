package cz.cuni.mff.aspect

import cz.cuni.mff.aspect.coevolution.MarioCoEvolver
import cz.cuni.mff.aspect.evolution.controller.ControllerEvolution
import cz.cuni.mff.aspect.evolution.controller.NeuroControllerEvolution
import cz.cuni.mff.aspect.evolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.evolution.generator.MockEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.GameSimulator
import kotlin.system.exitProcess


fun main() {
    coevolution()
    // playground()
    exitProcess(0)
}


fun coevolution() {
    val controllerEvolution: ControllerEvolution = NeuroControllerEvolution()
    val evoGenerator: EvolutionaryGenerator = MockEvolutionaryGenerator()

    val evolution = MarioCoEvolver()
    val evolutionResult = evolution.evolve(controllerEvolution, evoGenerator)

    val marioSimulator = GameSimulator()
    marioSimulator.playMario(evolutionResult.controller, evoGenerator, true)
    println(marioSimulator.finalDistance)
}

fun playground() {

}
