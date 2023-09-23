import domain.Experience
import domain.User
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import freemarker.template.Version
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.*
import java.nio.file.Path
import java.util.*
import kotlin.io.path.absolutePathString

const val TEMPLATE_PATH =
    "C:\\Users\\AntonioLavoro\\Documents\\IdeaProjects\\uniwhere-w-backtwo2\\backtwo2-backend\\src\\main\\resources\\templates"

class PdfUtils {

    fun createPdf(user: User, experiences: List<Experience>, tempDirectory: Path): File {
        val config = createTemplateConfig()
        val inputData = prepareUserDataForTemplateFilling(user = user, experiences = experiences)

        val dirPath = tempDirectory.absolutePathString().plus("/")
        val filename = "user_id_".plus(user.userId)

        val outerHtml = File(dirPath.plus(filename).plus(".html"))
        val outerPdf = File(dirPath.plus(filename).plus(".pdf"))
        outerHtml.createNewFile()
        outerPdf.createNewFile()

        val template: Template = config.getTemplate("/baseTemplate.ftl")

        val consoleWriter: Writer = OutputStreamWriter(System.out)
        template.process(inputData, consoleWriter)

        val outputWriter: Writer = FileWriter(outerHtml)
        outputWriter.use { fileWriter ->
            template.process(inputData, fileWriter)
        }
        outputWriter.close()

        val document = createJsoupDocument(outerHtml)
        val output = createPdf(document = document, output = outerPdf)

        return output
    }

    private fun createJsoupDocument(input: File): Document {
        val document = Jsoup.parse(input, "UTF-8")
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml)
        return document
    }

    private fun createPdf(document: Document, output: File): File {
        FileOutputStream(output).use { outputStream ->
            val renderer = ITextRenderer()
            val sharedContext = renderer.sharedContext
            sharedContext.isPrint = true
            sharedContext.isInteractive = false
            renderer.setDocumentFromString(document.html())
            renderer.layout()
            renderer.createPDF(outputStream)
        }

        return output
    }

    private fun createTemplateConfig(): Configuration {
        val cfg = Configuration()
        val templatePath = File(TEMPLATE_PATH)
        cfg.setDirectoryForTemplateLoading(templatePath)

        cfg.incompatibleImprovements = Version(2, 3, 20)
        cfg.defaultEncoding = "UTF-8"
        //TODO Maybe we need to change the locale accordingly to the locale passed from the user?
        cfg.locale = Locale.ITALIAN
        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER

        return cfg
    }

    private fun prepareUserDataForTemplateFilling(user: User, experiences: List<Experience>): MutableMap<String, Any> {
        val input: MutableMap<String, Any> = HashMap()
        input["user"] = user
        input["experiences"] = experiences
        return input
    }
}
