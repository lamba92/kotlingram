plugins {
    id("org.jetbrains.dokka")
}

tasks {
    create<Delete>("clean") {
        delete(buildDir)
    }
}
