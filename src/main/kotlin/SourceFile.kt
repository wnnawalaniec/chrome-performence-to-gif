import java.io.File
import java.lang.Exception

class SourceFile(path: String = "") : File(path)

interface SourceFileValidator
{
    fun validate()
}

class BasicSourceFileValidator(val file: SourceFile): SourceFileValidator {
    override fun validate() {
        existsOrThrowException(file)
        isNotDirectoryOrThrowException(file)
        isReadableOrThrowException(file)
    }

    fun existsOrThrowException(file: SourceFile) {
        if(!file.exists()) {
            throw Exception("Plik nie istnieje")
        }
    }

    fun isNotDirectoryOrThrowException(file: SourceFile) {
        if(file.isDirectory) {
            throw Exception("Plik jest katalogiem")
        }
    }

    fun isReadableOrThrowException(file: SourceFile) {
        if(!file.canRead()) {
            throw Exception("Brak uprawnień do odczytu wybranego pliku")
        }
    }
}