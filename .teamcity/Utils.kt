import jetbrains.buildServer.configs.kotlin.v10.toExtId
import jetbrains.buildServer.configs.kotlin.v2019_2.RelativeId

enum class BranchType { Master, Dev }
enum class IdType { Vcs, Build, Promote, Project }

fun getBranchName(branchType: BranchType) = branchType.name.toLowerCase()
fun getIdTypeName(idType: IdType) = idType.name.toLowerCase()

fun generateIdString(idType: IdType, repoName: String, branchName: String) = "${getIdTypeName(idType)}${repoName}${branchName}".toExtId()
fun generateId(idType: IdType, repoName: String, branchName: String) = RelativeId(generateIdString(idType, repoName, branchName))
fun generateId(idType: IdType, vcsRoot: CommonRoot) = generateId(idType, vcsRoot.repoName, getBranchName(vcsRoot.branchType))

