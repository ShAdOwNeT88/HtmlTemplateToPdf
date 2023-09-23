import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.absolutePathString


class FileUtils {
    fun zipFolder(srcFolder: File, outputFolder: Path): File? {
        return try {
            val outputZipFile = File(outputFolder.absolutePathString().plus("/").plus("users_cvs_output.zip"))
            outputZipFile.createNewFile()
            FileOutputStream(outputZipFile).use { fileWriter ->
                ZipOutputStream(fileWriter).use { zip -> addFolderToZip(srcFolder, srcFolder, zip) }
            }
            outputZipFile
        } catch (e: java.lang.Exception) {
            System.err.println("Error in zip processing for folder $srcFolder")
            null
        }
    }

    //TODO Need to add only .pdf files into the folder, not other type of files like .html
    private fun addFileToZip(rootPath: File, srcFile: File, zip: ZipOutputStream) {
        try {
            if (srcFile.isDirectory()) {
                addFolderToZip(rootPath, srcFile, zip)
            } else {
                val buf = ByteArray(1024)
                var len: Int
                FileInputStream(srcFile).use { `in` ->
                    var name = srcFile.path
                    name = name.replace(rootPath.path, "")
                    System.err.println("Zip $srcFile\n to $name")
                    zip.putNextEntry(ZipEntry(name))
                    while (`in`.read(buf).also { len = it } > 0) {
                        zip.write(buf, 0, len)
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            System.err.println("Error in zip creation in path $rootPath for file $srcFile")
        }
    }

    @Throws(java.lang.Exception::class)
    private fun addFolderToZip(rootPath: File, srcFolder: File, zip: ZipOutputStream) {
        try {
            for (fileName in srcFolder.listFiles()) {
                addFileToZip(rootPath, fileName, zip)
            }
        } catch (e: java.lang.Exception) {
            System.err.println("Error during addition of folder into zip file in path $rootPath from folder $srcFolder")
        }
    }
}
