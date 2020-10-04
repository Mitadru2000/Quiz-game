
package com.example.android.quizgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.quizgame.GameOverFragmentArgs
import com.example.android.quizgame.GameOverFragmentDirections
import com.example.android.quizgame.R
import com.example.android.quizgame.databinding.FragmentGameOverBinding


class GameOverFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
               val binding: FragmentGameOverBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_over, container, false)
        (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.Wrong)
        binding.tryAgainButton.setOnClickListener{
            it.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToTitleFragment())
        }
        setHasOptionsMenu(true)
        val args= GameOverFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"Questions ${args.numQuestions} Answers ${args.numCorrect}",Toast.LENGTH_SHORT).show()
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share->startActivity(getshareintent())
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("StringFormatInvalid")
    fun getshareintent(): Intent {
        var args=GameWonFragmentArgs.fromBundle(arguments!!)
        var shareintent: Intent =
            Intent(Intent.ACTION_SEND).setType("text/Plain").putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text,args.numCorrect,args.numQuestions))
        return shareintent
    }
}
