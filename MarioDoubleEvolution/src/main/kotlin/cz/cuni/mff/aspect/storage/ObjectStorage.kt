package cz.cuni.mff.aspect.storage

import java.io.*


object ObjectStorage {

    private const val storageDirectory: String = "data"

    fun store(filePath: String, data: Any) {
        val fullFilePath = this.getFullFilePath(filePath)
        val file = File(fullFilePath)
        file.createNewFile()
        val fos = FileOutputStream(file)
        val oos = ObjectOutputStream(fos)

        oos.use { it.writeObject(data)}
        oos.flush()
    }

    fun load(filePath: String): Any {
        val fullFilePath = this.getFullFilePath(filePath)
        val file = File(fullFilePath)
        val fis = FileInputStream(file)
        val ois = ObjectInputStream(fis)

        var result: Any = Object()
        ois.use {
            result = it.readObject()
        }

        return result
    }

    private fun getFullFilePath(filePath: String): String = "${this.storageDirectory}/$filePath"

}