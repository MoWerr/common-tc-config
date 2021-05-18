import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.merge
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

open class RunBuildScript(vcsRoot: CommonRoot) : BuildType({
    id(generateId(IdType.Build, vcsRoot).relativeId)
    name = "Build"

    vcs {
        this.root(vcsRoot)
    }

    steps {
        exec {
            name = "Build image"
            path = "build.sh"
            formatStderrAsError = true
        }
    }

    triggers {
        vcs {
            branchFilter = "+:<default>"
        }
    }

    val dependencyId = DslContext.getParameter("dependencyId", "")
    if (dependencyId.isNotBlank()) {
        dependencies {
            snapshot(AbsoluteId(dependencyId)) {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.FAIL_TO_START
            }
        }
    }
})

open class PromoteToStable(vcsRoot: CommonRoot, dependency: BuildType) : BuildType({
    id(generateId(IdType.Promote, vcsRoot).relativeId)
    name = "Promote to Stable"

    vcs {
        root(vcsRoot)
    }

    features {
        merge {
            branchFilter = "+:<default>"
            destinationBranch = "master"
        }
    }

    dependencies {
        snapshot(dependency) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})