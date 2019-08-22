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

    implementation(group = "org.deeplearning4j", name = "deeplearning4j-core", version = "0.9.1")
    implementation(group = "org.nd4j", name = "nd4j-native-platform", version = "0.9.1")
    implementation(group = "org.datavec", name = "datavec-api", version = "0.9.1")

    implementation(group = "io.jenetics", name = "jenetics", version = "5.0.1")
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

val fatJar = task("fatJar", type = Jar::class) {
    manifest {
        attributes["Main-Class"] = "cz.cuni.mff.aspect.MainKt"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

val compileKotlin by tasks.getting(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
