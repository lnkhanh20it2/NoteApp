package com.appofkhanh.noteapp.ui.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.appofkhanh.noteapp.ui.Model.Notes
import com.appofkhanh.noteapp.R
import com.appofkhanh.noteapp.ViewModel.NotesViewModel
import com.appofkhanh.noteapp.databinding.FragmentHomeBinding
import com.appofkhanh.noteapp.ui.Adapter.NotesAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NotesViewModel by viewModels()
    private var oldNotes = arrayListOf<Notes>()
    private lateinit var adapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val item = menu.findItem(R.id.btnSearch)
                val searchView = item.actionView as android.widget.SearchView
                searchView.queryHint = "Enter Notes ..."
                searchView.setOnQueryTextListener(
                    object : android.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            NotesFilteting(newText)
                            return true
                        }
                    })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner)

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            oldNotes = notesList as ArrayList<Notes>
            binding.rcvAllNote.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = NotesAdapter(requireContext(),notesList)
            binding.rcvAllNote.adapter = adapter
        }
        binding.btnFilterUnfinished.setOnClickListener {
            viewModel.getUnfinishedNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNote.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNote.adapter = adapter
            }
        }
        binding.btnFilterDoing.setOnClickListener {
            viewModel.getDoingNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNote.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNote.adapter = adapter
            }
        }
        binding.btnFilterDone.setOnClickListener {
            viewModel.getDoneNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNote.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNote.adapter = adapter
            }
        }
        binding.btnAllNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNote.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNote.adapter = adapter
            }
        }
        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    private fun NotesFilteting(newText: String?) {
        val newFilteredList = arrayListOf<Notes>()
        for (i in oldNotes) {
            if (i.title.contains(newText!!) || i.subTitle.contains(newText!!)) {
                newFilteredList.add(i)
            }
        }
        adapter.filteringNotes(newFilteredList)
    }
}