plugins {
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

tasks {
    val buildAdminGuideDocs by registering(org.asciidoctor.gradle.jvm.AsciidoctorTask::class) {
        setSourceDir(file("asciidoc/admin-guide"))
        setOutputDir(file("${buildDir}/docs/admin-guide"))
        baseDirFollowsSourceDir()
        sources(delegateClosureOf<PatternSet> {
            include("index.adoc")
        })
        attributes(mapOf("source-highlighter" to "coderay"))
    }

    val buildDevGuideDocs by registering(org.asciidoctor.gradle.jvm.AsciidoctorTask::class) {
        setSourceDir(file("asciidoc/dev-guide"))
        setOutputDir(file("${buildDir}/docs/dev-guide"))
        baseDirFollowsSourceDir()
        sources(delegateClosureOf<PatternSet> {
            include("index.adoc")
        })
        attributes(mapOf("source-highlighter" to "coderay"))
    }

    build {
        dependsOn(buildAdminGuideDocs)
        dependsOn(buildDevGuideDocs)
    }
}