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

/**
 * We need this task, because the resources are by default moved directly to build/resources, not their corresponding
 * folders.
 */
tasks.register("fixResources", Copy::class) {
    from("src/ch/idsia/benchmark/mario/engine/resources")
    into("$buildDir/resources/main/ch/idsia/benchmark/mario/engine/resources")
}

tasks.named("processResources", ProcessResources::class) {
    dependsOn("fixResources")
}

