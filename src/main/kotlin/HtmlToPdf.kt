import domain.Experience
import domain.User
import domain.ValueExampleObject
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import freemarker.template.Version
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream
import java.util.*


//RES
//https://www.baeldung.com/java-html-to-pdf
//https://www.vogella.com/tutorials/FreeMarker/article.html

class HtmlToPdf {
    fun createJsoupDocument(input: File): Document {
        val document = Jsoup.parse(input, "UTF-8")
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml)
        return document
    }

    fun createPdf(document: Document, output: File) {
        FileOutputStream(output).use { outputStream ->
            val renderer = ITextRenderer()
            val sharedContext = renderer.sharedContext
            sharedContext.isPrint = true
            sharedContext.isInteractive = false
            renderer.setDocumentFromString(document.html())
            renderer.layout()
            renderer.createPDF(outputStream)
        }
    }

    fun createTemplateConfig(): Configuration {
        val cfg = Configuration()

        // Where do we load the templates from:

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(HtmlToPdf::class.java, "templates")

        // Some other recommended settings:

        // Some other recommended settings:
        cfg.incompatibleImprovements = Version(2, 3, 20)
        cfg.defaultEncoding = "UTF-8"
        cfg.locale = Locale.ITALIAN
        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER

        return cfg
    }

    fun prepareDataForTemplateFilling(): MutableMap<String, Any> {
        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

        val input: MutableMap<String, Any> = HashMap()

        input["title"] = "This is a fucking example"

        input["exampleObject"] = ValueExampleObject("Java object", "me")

        val systems: MutableList<ValueExampleObject> = ArrayList()
        systems.add(ValueExampleObject("Android", "Google"))
        systems.add(ValueExampleObject("iOS States", "Apple"))
        systems.add(ValueExampleObject("Ubuntu", "Canonical"))
        systems.add(ValueExampleObject("Windows7", "Microsoft"))
        input["systems"] = systems

        return input
    }

    fun prepareUserDataForTemplateFilling(): MutableMap<String, Any> {
        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

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

        val input: MutableMap<String, Any> = HashMap()

        input["user"] = user
        input["experiences"] = userExperiences

        return input
    }
}
