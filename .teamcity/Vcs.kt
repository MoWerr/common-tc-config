import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

open class CommonRoot(val repoName: String, val branchType: BranchType) : GitVcsRoot({
    val branchName = getBranchName(branchType)
    id(generateId(IdType.Vcs, repoName, branchName).relativeId)

    name = "${repoName}_${branchName}"
    url = "https://github.com/MoWerr/${repoName}"
    branch = "refs/heads/${branchName}"
    branchSpec = "+:refs/heads/*"
    userForTags = "tc_mower"

    authMethod = password {
        userName = "MoWerr"
        password = DslContext.getParameter("credentialToken", "")
    }
})