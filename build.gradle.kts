plugins {
    kotlin("jvm") version "1.3.41"
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

subprojects {

    repositories {
        flatDir {
            dirs("../MarioAI4J/lib")
            dirs("../MarioAI4J-Tournament/lib")
        }
    }

    sourceSets {
        main {
            java {
                srcDirs("src")
            }
        }
    }

}

val cl = Action<Task> { println("I'm ${this.project.name}") }
tasks.register("hello") { doLast(cl) }
