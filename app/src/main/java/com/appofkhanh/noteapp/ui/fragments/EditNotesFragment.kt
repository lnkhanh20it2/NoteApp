package com.appofkhanh.noteapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appofkhanh.noteapp.R
import com.appofkhanh.noteapp.databinding.FragmentEditNotesBinding
import com.appofkhanh.noteapp.ui.model.Notes
import com.appofkhanh.noteapp.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment() {
    private val oldNotes by navArgs<EditNotesFragmentArgs>()
    private lateinit var binding: FragmentEditNotesBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by activityViewModels()

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
                            findNavController().popBackStack()
                        }
                        tvNo?.setOnClickListener {
                            bottomSheet.dismiss()
                        }
                        bottomSheet.show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            edtTitle.setText(oldNotes.data.title)
            edtSubtitle.setText(oldNotes.data.subTitle)
            edtNotes.setText(oldNotes.data.notes)
            when (oldNotes.data.priority) {
                "1" -> {
                    priority = "1"
                    btnGreen.setImageResource(R.drawable.ic_done)
                    btnRed.setImageResource(0)
                    btnYellow.setImageResource(0)
                }
                "2" -> {
                    priority = "2"
                    btnYellow.setImageResource(R.drawable.ic_done)
                    btnRed.setImageResource(0)
                    btnGreen.setImageResource(0)
                }
                "3" -> {
                    priority = "3"
                    btnRed.setImageResource(R.drawable.ic_done)
                    btnGreen.setImageResource(0)
                    btnYellow.setImageResource(0)
                }
            }
            btnGreen.setOnClickListener {
                priority = "1"
                btnGreen.setImageResource(R.drawable.ic_done)
                btnRed.setImageResource(0)
                btnYellow.setImageResource(0)
            }
            btnRed.setOnClickListener {
                priority = "3"
                btnRed.setImageResource(R.drawable.ic_done)
                btnGreen.setImageResource(0)
                btnYellow.setImageResource(0)
            }
            btnYellow.setOnClickListener {
                priority = "2"
                btnYellow.setImageResource(R.drawable.ic_done)
                btnRed.setImageResource(0)
                btnGreen.setImageResource(0)
            }
            btnEditSaveNotes.setOnClickListener {
                updateNotes()
            }
        }
    }

    private fun updateNotes() {
        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()
        val date = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", date.time)
        val data = Notes(
            id = oldNotes.data.id,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date = notesDate.toString(),
            priority = priority
        )
        viewModel.updateNotes(data)

        Toast.makeText(requireActivity(), "Update Notes Successfully", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
}
