plugins {
    `java-library`
}

dependencies {
    implementation(files("./lib/simple-logging-1.0.1.jar"))
}

sourceSets {
    main {
        java {
            srcDir("./src")
        }
    }
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "ch.idsia.benchmark.mario.MarioSimulator"
    }

    from("src") {
        include("ch/idsia/benchmark/mario/engine/resources/*")
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

}
