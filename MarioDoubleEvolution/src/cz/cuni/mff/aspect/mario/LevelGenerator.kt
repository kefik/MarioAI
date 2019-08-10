package cz.cuni.mff.aspect.mario

import ch.idsia.benchmark.mario.engine.level.Level
import ch.idsia.benchmark.mario.engine.level.SpriteTemplate
import ch.idsia.benchmark.mario.engine.sprites.Sprite
import cz.cuni.mff.aspect.doubleev.game.Enemies
import cz.cuni.mff.aspect.doubleev.game.LevelTypes
import cz.cuni.mff.aspect.doubleev.game.Tiles
import cz.cuni.mff.aspect.doubleev.generator.EvolutionaryGenerator

class LevelGenerator(private val evoGenerator: EvolutionaryGenerator) : ch.idsia.benchmark.mario.engine.level.LevelGenerator {

    override fun createLevel(): Level {
        val map = evoGenerator.generateMap()
        val tiles = map.getTiles()
        val enemies = map.getEnemies()

        val level = Level(tiles.size, tiles[0].size)
        level.type = LevelTypes.DEFAULT

        level.xExit = level.length - 1
        level.yExit = 12
        level.randomSeed = 123
        level.difficulty = 1

        for (x in 0 until tiles.size) {
            for (y in 0 until tiles[x].size) {
                val tile = tiles[x][y]
                level.setBlock(x, y, tile)

                if (tile == Tiles.PEACH)
                    level.setSpriteTemplate(x, y, SpriteTemplate(Sprite.KIND_PRINCESS))
            }
        }

        for (x in 0 until enemies.size) {
            for (y in 0 until enemies[x].size) {
                val enemy = enemies[x][y]
                if (enemy != Enemies.NOTHING) {
                    level.setSpriteTemplate(x, y, SpriteTemplate(enemy))
                }
            }
        }

        return level
    }

}