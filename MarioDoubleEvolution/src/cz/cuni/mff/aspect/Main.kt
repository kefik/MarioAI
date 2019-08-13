package cz.cuni.mff.aspect

import cz.cuni.mff.aspect.doubleev.MarioEvolver
import cz.cuni.mff.aspect.doubleev.agent.EvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.agent.MockEvolutionaryAgent
import cz.cuni.mff.aspect.doubleev.generator.EvolutionaryGenerator
import cz.cuni.mff.aspect.doubleev.generator.MockEvolutionaryGenerator
import cz.cuni.mff.aspect.mario.LevelGenerator
import cz.cuni.mff.aspect.mario.MarioAgent
import cz.cuni.mff.aspect.mario.MarioSimulator
import kotlin.system.exitProcess


fun main() {
    val evolution = MarioEvolver()
    val evoAgent: EvolutionaryAgent = MockEvolutionaryAgent()
    val evoGenerator: EvolutionaryGenerator = MockEvolutionaryGenerator()
    evolution.evolve(evoAgent, evoGenerator)

    val marioSimulator = MarioSimulator()
    val marioAgent = MarioAgent(evoAgent)
    val marioLevelGenerator = LevelGenerator(evoGenerator)
    marioSimulator.playMario(marioAgent, marioLevelGenerator)

    exitProcess(0)
}
