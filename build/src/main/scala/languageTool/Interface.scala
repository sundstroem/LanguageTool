import FileHandler._
import ModuleHandler._
import Module._
import java.util.logging.FileHandler
import scala.collection.mutable.ArrayBuffer

object Interface extends App {
    var modules: Array[Module] = Array()
    def main() {
        showMenu()

        val command = readChar()
        command match {
            case 'L' => modules = loadModulesDebug()
            case 'S' => selectModule()
            case 'Q' => println("Quitting!")
            case _ => {
                println("Invalid command: " + command)
                main()
            }
        }
        
        //val data = FileHandler.parseFile("src/main/scala/languageTool/data/testInput")
        //println(data.mkString(","))
    }

    def showMenu() {
        println("Welcome to Language Tool!")
        println("- * -")
        println("H: Help")
        println("L: Load word modules")
        println("T: Train a module")
        println("Q: Quit")
    }
    /*
    def loadModules() {
        println("Loading modules from modulePaths-file")
        val modules = FileParser.parseFile("src/main/scala/languageTool/modulePaths").map(_.split(""))
        val filepath = modules.head
        val name = modules.tail.fold("")((a, b) => a + b)
        println(modules.head)
        println("f:" + filepath + "; n:" + name)
        //(paths, names) = modules.map(l => l.split(" ")).map(l => (l.head, l.tail))
        
    }
    */
    def loadModulesDebug(): Array[Module] = {
        println("Loading data from modulePaths-file...")
        val modulePathsData = FileHandler.readModulePaths()
        for ((path, name) <- modulePathsData) {
            println("-*-")
            println("filepath: " + path)
            println("name of the module: " + name)
        }
        
        println("ModulePaths loaded!")
        println("Now loading the data for each module...")
        var moduleData = scala.collection.mutable.Map[String, ArrayBuffer[String]]()
        for ((path, name) <- modulePathsData) {
            moduleData += ((name, FileHandler.readModule(path)))
            println("-*-")
            println(s"Data from ${name} loaded:")
            println(moduleData(name).mkString)
        }
        println("All the necessary data loaded!")
        println("Unpacking the raw data from the modules and creating Module objects")
        val modules = moduleData.keys.map(moduleName => ModuleHandler.unpackModule(moduleName, moduleData(moduleName)))
        println("All modules created!")
        return modules.toArray
    }   

    def selectModule() = {
        modules.zipWithIndex.foreach(m => println(s"${m._2}: ${m._1.getName}"))
        println("Choose the module to open:")
        val command = scala.io.StdIn.readInt()
    }

    main()
}