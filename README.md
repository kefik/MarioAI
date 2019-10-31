# Mario AI and LevelGenerator coevolution

Welcome to my diploma thesis work! Meaningful readme coming soon...

## How to start development in IntelliJ IDEA

1. ```git clone https://github.com/Aspect26/MarioDoubleEvolution.git```
2. Open the topmost project (MarioDoubleEvolution) in IDEA
3. A notification about gradle project appears -> click **Import Gradle Project**. If you missed the notification or it didn't appear, right click root build.gradle.kts -> **Import Gradle Project**
4. Build Project (Ctrl+F9)
5. *(Optional)* You can test if the project is correctly set up by running any of the launchers. E.g. create **Kotlin** (not Kotlin script) run configuration. Set main class as *cz.cuni.mff.aspect.launch.AIPlayMarioKt* (it may appear in red, but it seems to be some IDEA bug, don't mind that) and use classpath of module: mario.MarioDoubleEvolution.main

## 3rd party libraries used

1. [Jenetics](http://jenetics.io/) - genetic algorithms
2. [DeepLearning4J](https://deeplearning4j.org/) - (deep) neural networks 
3. [evo-NEAT](https://github.com/vishnugh/evo-NEAT) - NEAT algorithm (Neuroevolution of augmented topologies)
4. [XChart](http://knowm.org/open-source/xchart) - charts
