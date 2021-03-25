tasks {
    create<Delete>("clean") {
        delete(buildDir)
    }
}
