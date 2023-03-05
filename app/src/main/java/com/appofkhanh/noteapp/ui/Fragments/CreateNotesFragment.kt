package com.appofkhanh.noteapp.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.appofkhanh.noteapp.ui.Model.Notes
import com.appofkhanh.noteapp.R
import com.appofkhanh.noteapp.ViewModel.NotesViewModel
import com.appofkhanh.noteapp.databinding.FragmentCreateNotesBinding
import java.util.Date

class CreateNotesFragment : Fragment() {
    private lateinit var binding: FragmentCreateNotesBinding
    private var priority : String = "1"
    private val viewModel : NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        binding.btnGreen.setOnClickListener {
            priority =  "1"
            binding.btnGreen.setImageResource(R.drawable.ic_done)
            binding.btnRed.setImageResource(0)
            binding.btnYellow.setImageResource(0)
        }
        binding.btnRed.setOnClickListener {
            priority = "3"
            binding.btnRed.setImageResource(R.drawable.ic_done)
            binding.btnGreen.setImageResource(0)
            binding.btnYellow.setImageResource(0)
        }
        binding.btnYellow.setOnClickListener {
            priority = "2"
            binding.btnYellow.setImageResource(R.drawable.ic_done)
            binding.btnRed.setImageResource(0)
            binding.btnGreen.setImageResource(0)
        }
        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }
        return binding.root
    }
    private fun createNotes(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()
        val date = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", date.time)
        val data = Notes(null,title = title,subTitle = subTitle,notes = notes,date = notesDate.toString(), priority)
        viewModel.insertNotes(data)

        Toast.makeText(requireActivity(), "Create Notes Successfully", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }
}