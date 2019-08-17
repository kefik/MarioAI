package cz.cuni.mff.aspect

import cz.cuni.mff.aspect.coevolution.MarioEvolver
import cz.cuni.mff.aspect.coevolution.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.coevolution.agent.MockEvolutionaryAgent
import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.coevolution.generator.MockEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.MarioSimulator
import kotlin.system.exitProcess


fun main() {
    val evolution = MarioEvolver()
    val evoAgent: EvolutionaryAgent = MockEvolutionaryAgent()
    val evoGenerator: EvolutionaryGenerator = MockEvolutionaryGenerator()
    evolution.evolve(evoAgent, evoGenerator)

    val marioSimulator = MarioSimulator()
    marioSimulator.playMario(evoAgent, evoGenerator)

    exitProcess(0)
}
