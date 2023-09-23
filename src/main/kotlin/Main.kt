import domain.Experience
import domain.User
import utils.HtmlToPdfUtils
import kotlin.io.path.createTempDirectory

fun main() {
    val user = User(
        name = "Antonio Francesco",
        surname = "Iovine",
        phone = "+393293640567",
        email = "test@gmail.com"
    )

    val userExperiences = listOf(
        Experience(title = "Experience", description = "Description for experience"),
        Experience(title = "Experience1", description = "Description for experience 1"),
        Experience(title = "Experience2", description = "Description for experience 2")
    )

    val document = HtmlToPdfUtils().createPdf(
        user = user,
        experiences = userExperiences,
        tempDirectory = createTempDirectory("pdf_output")
    )
}

