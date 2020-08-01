sealed class Failure {
    object NetworkConnection : Failure()
    object UnExpectedError : Failure()
}