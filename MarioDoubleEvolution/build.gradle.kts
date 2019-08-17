plugins {
    `java-library`
}

dependencies {
    implementation(project(":mario-simulator"))
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
        attributes["Main-Class"] = "ch.cuni.mff.aspect.Main"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

}
