import jetbrains.buildServer.configs.kotlin.v2019_2.Project

open class DevProj(vcsRoot: CommonRoot) : Project({
    id(generateId(IdType.Project, vcsRoot).relativeId)
    name = "dev"

    val build = RunBuildScript(vcsRoot)

    buildType(build)
    buildType(PromoteToStable(vcsRoot, build))
})