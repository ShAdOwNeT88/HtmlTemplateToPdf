import freemarker.template.Template
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.io.Writer

fun main() {
    val config = HtmlToPdf().createTemplateConfig()
    val inputData = HtmlToPdf().prepareUserDataForTemplateFilling()
    val outerHtml =
        File("C:\\Users\\AntonioLavoro\\Documents\\IdeaProjects\\html_template_to_pdf\\src\\main\\resources\\output\\output.html")
    val outerPdf =
        File("C:\\Users\\AntonioLavoro\\Documents\\IdeaProjects\\html_template_to_pdf\\src\\main\\resources\\output\\output.pdf")

    // 2.2. Get the template
    //val template: Template = config.getTemplate("/firstTemplate.ftl")
    val template: Template = config.getTemplate("/userBaseTemplate.ftl")

    // 2.3. Generate the output
    // Write output to the console
    val consoleWriter: Writer = OutputStreamWriter(System.out)
    template.process(inputData, consoleWriter)

    // Write output to the file
    val outputWriter: Writer = FileWriter(outerHtml)
    outputWriter.use { fileWriter ->
        template.process(inputData, fileWriter)
    }
    outputWriter.close()

    val document = HtmlToPdf().createJsoupDocument(outerHtml)
    HtmlToPdf().createPdf(document = document, output = outerPdf)
}

