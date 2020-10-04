

package com.example.android.quizgame

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.quizgame.GameWonFragmentArgs
import com.example.android.quizgame.GameWonFragmentDirections
import com.example.android.quizgame.R
import com.example.android.quizgame.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener{
            it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToTitleFragment())

        }
        setHasOptionsMenu(true)
        val args= GameWonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"Questions ${args.numQuestions} and Answers ${args.numCorrect}",Toast.LENGTH_SHORT).show()
        (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.Right)
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
    fun getshareintent(): Intent {
        var args=GameWonFragmentArgs.fromBundle(arguments!!)
        var shareintent:Intent=Intent(Intent.ACTION_SEND).setType("text/Plain").putExtra(Intent.EXTRA_TEXT,getString(R.string.share_success_text,args.numQuestions,args.numCorrect))
        return shareintent
    }

}
