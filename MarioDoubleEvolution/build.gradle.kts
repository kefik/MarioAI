plugins {
    `java-library`
    kotlin("jvm") version "1.3.41"
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(project(":MarioAI4J"))
    implementation(kotlin("stdlib"))
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
        attributes["Main-Class"] = "cz.cuni.mff.aspect.MainKt"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

}
