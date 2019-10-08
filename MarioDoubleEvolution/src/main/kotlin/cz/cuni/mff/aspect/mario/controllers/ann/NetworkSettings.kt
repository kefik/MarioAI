package cz.cuni.mff.aspect.mario.controllers.ann

// TODO: use this in the SimpleANNController also
data class NetworkSettings(val receptiveFieldSizeRow: Int = 3,
                           val receptiveFieldSizeColumn: Int = 3,
                           val receptiveFieldRowOffset: Int = 0,
                           val receptiveFieldColumnOffset: Int = 1,
                           val hiddenLayerSize: Int = 7)