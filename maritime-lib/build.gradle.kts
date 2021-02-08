plugins {
    id("maritime.java-library-conventions")
}

dependencies {
    api(project(":model"))
    api(project(":db"))
    api(project(":parser"))
    api(project(":codelists"))
    api(project(":retriever"))
}
