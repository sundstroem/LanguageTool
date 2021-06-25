
package module 

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer

class Module(val name: String){
    /*
    The Module class contains the questions, the answers and the statistics related to the
    questions

    Statistics:
    0: The number of times that the question was answered correctly
    1: The number of times that the question was initially answered wrong, but then corrected
    2: The number of times that the question was answered wrong and not corrected
    */
    def getName = name
    var questions = ArrayBuffer[String]()
    var answers = ArrayBuffer[Seq[String]]()
    var statistics = ArrayBuffer[Seq[Int]]()

    def addQuestion(question: String, answer: Seq[String]) = {
        questions += question
        answers += answer
        statistics += Seq(0,0,0)
    }
    def addQuestion(question: String, answer: Seq[String], statistic: Seq[Int]) = {
        questions += question
        answers += answer
        statistics += statistic
    }

    def submitAnswer(questionIndex: Int, answer: Seq[String], firstSubmit: Boolean): Boolean = {
        val initStats = statistics(questionIndex)
        val isCorrect: Boolean = {
            Range(0, answers(questionIndex).size)
                .map(i => answers(questionIndex)(i) == answer(i))
                .foldRight(true)((a, b) => a && b)
        }
        
        if(firstSubmit) {
            if(isCorrect) statistics(questionIndex) = Seq(initStats(0) + 1, initStats(1), initStats(2))
            else statistics(questionIndex) = Seq(initStats(0), initStats(1), initStats(2) + 1) 
        } else {
            if(isCorrect) statistics(questionIndex) = Seq(initStats(0), initStats(1) + 1, initStats(2))
        }
        return isCorrect
    }
    
    def randomizeQuestions(): IndexedSeq[Int] = {
        val indexSeq = Range(0, questions.size).toIndexedSeq
        return scala.util.Random.shuffle(indexSeq)
    }
}