package com.example.android.quizgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.quizgame.databinding.FragmentGameBinding
import com.example.android.quizgame.GameFragmentDirections
import com.example.android.quizgame.R


class GameFragment : Fragment() {
    data class Question(
        val text: String,
        val answers: List<String>)

    // The first answer is the correct one. The answers are randomized before showing the text.
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What was the first feature-length animated film ever released?",
            answers = listOf("Snow White and the Seven Dwarfs", "Pinocchio", "Fantasia", "Dumbo")),
        Question(text = "What is Harry Potter’s patronus?",
            answers = listOf("A stag", "A horse", "An otter", "A hare")),
        Question(text = "Which house only became one of the Great Houses of Westeros after Aegon Targaryen’s invasion?",
            answers = listOf("House Tyrell", "House Stark", "House Bolton", "House Tully")),
        Question(text = "How many black keys are found on a traditional piano?",
            answers = listOf("32", "34", "52", "54")),
        Question(text = "What MTV music show premiered in 1998?",
            answers = listOf("Total Request Live", "Unplugged", "MTV News", "MTV Rocks Off")),
        Question(text = "Who scored the first three-point basket in NBA history?",
            answers = listOf("Chris Ford", "Howard Porter", "Rick Robey", "M. L. Carr")),
        Question(text = "What three teams did Babe Ruth play for?",
            answers = listOf("Boston Red Sox, New York Yankees, Boston Braves", "Boston Red Sox, New York Yankees, Chicago Cubs", "St. Louis Cardinals, New York Yankees, Chicago Cubs", "New York Yankees, Boston Red Sox, Houston Astros")),
        Question(text = "Who scored the first goal at Wembley Stadium?",
            answers = listOf("David Jack", "Fernando Cabrita", "Josep Seguer", "Olavo Rodrigues Barbosa")),
        Question(text = "Who was the Man of the Match in the first One-Day International Cricket match?",
            answers = listOf("John Edrich", "Ashley Mallett", "Ian Chappell", "Ray Illingworth")),
        Question(text = "How many regular season games are played in the NFL?",
            answers = listOf("16", "12", "18", "20")),
        Question(text="Which mathematician published “The Element” and influenced the study of geometry?",
            answers= listOf("Euclid","Archimedes","Leonhard Euler","Pierre de Fermat")),
        Question(text="What is the warmest sea on Earth?",
            answers= listOf("Red Sea","Dead Sea","White Sea","Black Sea")),
        Question(text="What is the oldest active volcano on Earth?",
            answers= listOf("Mount Etna","Mount Saint Helen","Mount Vesuvius","Mount Nyiragongo")),
        Question(text="What year did the Cold War officially end?",
            answers= listOf("1989","1988","1990","1991")),
        Question(text="In what month is Earth closest to the sun?",
            answers= listOf("January","May","October","April")),
        Question(text=" Which astronomer first discovered the sunspots on the surface of the sun?",
            answers= listOf("Galileo Galilei","Nicolaus Copernicus","Johannes Kepler","Isaac Newton"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 8)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

       val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)

       randomizeQuestions()

        binding.game =this
        binding.submitButton.setOnClickListener {
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId

            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    }
                    else
                    {
                        it.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))
                    }
                }
                else
                {
                    it.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(questionIndex+1,questionIndex))
                }
            }
        }
        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }


    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.trivia_question, questionIndex + 1, numQuestions)
    }
}
