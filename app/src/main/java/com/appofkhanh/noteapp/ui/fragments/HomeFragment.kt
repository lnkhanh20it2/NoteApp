package com.appofkhanh.noteapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.appofkhanh.noteapp.R
import com.appofkhanh.noteapp.databinding.FragmentHomeBinding
import com.appofkhanh.noteapp.ui.adapter.NotesAdapter
import com.appofkhanh.noteapp.viewModel.NotesViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NotesViewModel by activityViewModels()
    private val adapter = NotesAdapter(arrayListOf())
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
                            viewModel.notesFiltering(if (newText.isNullOrBlank()) "" else newText)
                            return true
                        }
                    })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner)
        binding.rcvAllNote.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllNote.adapter = adapter
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.filteringNotes(it)
        }
        binding.btnFilterUnfinished.setOnClickListener {
            viewModel.setProperty("3")
        }
        binding.btnFilterDoing.setOnClickListener {
            viewModel.setProperty("2")
        }
        binding.btnFilterDone.setOnClickListener {
            viewModel.setProperty("1")
        }
        binding.btnAllNotes.setOnClickListener {
            viewModel.setProperty("")
        }
        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }
}