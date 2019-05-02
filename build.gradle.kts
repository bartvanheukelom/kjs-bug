plugins {
    idea
    kotlin("multiplatform") version "1.3.31"
}

repositories {
    jcenter()
}

kotlin {

    js()

    js().compilations.all {
        kotlinOptions {
            moduleKind = "commonjs"
        }
    }

    sourceSets {
        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }

}
