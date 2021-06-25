import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object FileHandler {
    def readModulePaths(): ArrayBuffer[(String, String)] = {
        /*
        This function reads modulePaths-file which contains the filepaths and the names of each module
        In the file, each line corresponds to a module.
        The first word of a line is the filepath to the module data (questions, answers and statistics)
        The rest of the line is the name of the module
        [TODO: implement a hierarchical structure for the modules]

        The return value of this function is an array of double strings (filepath, name)
        */

        var data = ArrayBuffer[(String, String)]()
        val lines = Source.fromFile("modulePaths").getLines

        def splitByFirstWhitespace(line: String): (String, String) = {
            val firstPart = line.split(" ").head
            val secondPart = line.drop(firstPart.length + 1)
            return (firstPart, secondPart)
        }

        for (line <- lines) {
            data += splitByFirstWhitespace(line)
        }
        return data

    }
    def readModule(path: String): ArrayBuffer[String] = {
        /*
        This function reads the raw data of a module from a file specified by the "path" parameter.
        The results are collected line by line in an array which is then returned.
        (The data will be parsed further in ModuleHandler.unpackModule)
        */

        var data = ArrayBuffer[String]()

        for (line <- Source.fromFile(path).getLines) {
            data += line
        }
        return data
    }

}