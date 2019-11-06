package cz.cuni.mff.aspect.storage

import com.evo.NEAT.Genome


object NeatAIStorage {

    const val LATEST: String = "latest.ai"
    const val STAGE4_LEVEL1: String = "Stage4Level1.ai"

    private val storage: ObjectStorage = ObjectStorage
    private const val aisDirectory = "neat-ai"

    fun storeAi(filePath: String, genome: Genome) {
        val fullFilePath = this.getFullFilePath(filePath)
        this.storage.store(fullFilePath, genome)
    }

    fun loadAi(filePath: String): Genome {
        val fullFilePath = this.getFullFilePath(filePath)
        return this.storage.load(fullFilePath) as Genome
    }

    private fun getFullFilePath(filePath: String): String = "${this.aisDirectory}/$filePath"

}