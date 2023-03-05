package com.appofkhanh.noteapp.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.appofkhanh.noteapp.ui.Model.Notes
import com.appofkhanh.noteapp.R
import com.appofkhanh.noteapp.databinding.ItemNotesBinding
import com.appofkhanh.noteapp.ui.Fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var noteList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    class notesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    fun filteringNotes(newFilteredList: ArrayList<Notes>){
        noteList = newFilteredList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val data = noteList[position]
        holder.binding.tvItemNotesTitle.text = data.title
        holder.binding.tvItemSubtitleNotes.text = data.subTitle
        holder.binding.tvItemNotesDate.text = data.date

        when (data.priority) {
            "1" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }
            "2" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            }
            "3" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
            }
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount() = noteList.size
}