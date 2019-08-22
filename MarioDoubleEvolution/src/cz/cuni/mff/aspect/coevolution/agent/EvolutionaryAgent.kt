package cz.cuni.mff.aspect.coevolution.agent

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator

interface EvolutionaryAgent {

    fun evolve(levelGenerator: EvolutionaryGenerator)

    fun playAction(tiles: Tiles, entities: Entities): List<MarioAction>

}

enum class MarioAction {
    RUN_RIGHT,
    RUN_LEFT,
    JUMP,
    SPECIAL
}