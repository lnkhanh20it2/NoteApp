package com.appofkhanh.noteapp.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.appofkhanh.noteapp.ui.Model.Notes
import com.appofkhanh.noteapp.R
import com.appofkhanh.noteapp.ViewModel.NotesViewModel
import com.appofkhanh.noteapp.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment(){
    private val oldNotes by navArgs<EditNotesFragmentArgs>()
    private lateinit var binding: FragmentEditNotesBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_delete, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.btnDelete -> {
                        val bottomSheet =
                            BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
                        bottomSheet.setContentView(R.layout.dialog_delete)
                        val tvYes = bottomSheet.findViewById<TextView>(R.id.dialogYes)
                        val tvNo = bottomSheet.findViewById<TextView>(R.id.dialogNo)
                        tvYes?.setOnClickListener {
                            viewModel.deleteNotes(oldNotes.data.id!!)
                            bottomSheet.dismiss()
                            binding.edtTitle.setText("")
                            binding.edtSubtitle.setText("")
                            binding.edtNotes.setText("")
                        }
                        tvNo?.setOnClickListener {
                            bottomSheet.dismiss()
                        }
                        bottomSheet.show()
                        true
                    }
                    else -> true
                }
            }

        }, viewLifecycleOwner)

        binding.edtTitle.setText(oldNotes.data.title)
        binding.edtSubtitle.setText(oldNotes.data.subTitle)
        binding.edtNotes.setText(oldNotes.data.notes)
        when (oldNotes.data.priority) {
            "1" -> {
                priority = "1"
                binding.btnGreen.setImageResource(R.drawable.ic_done)
                binding.btnRed.setImageResource(0)
                binding.btnYellow.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.btnYellow.setImageResource(R.drawable.ic_done)
                binding.btnRed.setImageResource(0)
                binding.btnGreen.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.btnRed.setImageResource(R.drawable.ic_done)
                binding.btnGreen.setImageResource(0)
                binding.btnYellow.setImageResource(0)
            }
        }
        binding.btnGreen.setOnClickListener {
            priority = "1"
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
        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()
        val date = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", date.time)
        val data = Notes(
            oldNotes.data.id,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date = notesDate.toString(),
            priority
        )
        viewModel.updateNotes(data)

        Toast.makeText(requireActivity(), "Update Notes Successfully", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }
}
