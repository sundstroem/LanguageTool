import Module._
import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer

object ModuleHandler {
    /*
    This parses the raw string data from the text file into a Module object
    */
    def unpackModule(name: String, data: ArrayBuffer[String]): Module = {
        /*
        The raw data is of the form:
        The module introduction text
        ;;
        The question;answer1:answer2:answer3;0:0:0\n

        Thus each line can be split into three parts
        1. The question (String)
        2. The answers (Seq[String])
        3. The statistics (Seq[Int])
        */
        val module = new Module(name)

        for (line <- data) {
            val splitLine = line.split(";")
            if (splitLine.length != 3) {
                println("the line that caused the error: " + line)
                scala.sys.error("the data is in wrong form (ModuleHandler.UnpackModule)")
            }
            val question = splitLine.head
            val answer = splitLine.tail.head.split(":")
            val statistic = splitLine.tail.tail.head.split(":").map(_.toInt)

            module.addQuestion(question, answer, statistic)
        }

        return module
    }
}