package cz.cuni.mff.aspect.coevolution.agent

import ch.idsia.agents.controllers.modules.Entities
import ch.idsia.agents.controllers.modules.Tiles
import cz.cuni.mff.aspect.coevolution.generator.EvolutionaryGenerator

class MockEvolutionaryAgent : EvolutionaryAgent {

    override fun playAction(tiles: Tiles, entities: Entities): List<MarioAction> {
        return listOf(MarioAction.JUMP)
    }

    override fun evolve(levelGenerator: EvolutionaryGenerator) {

    }

}