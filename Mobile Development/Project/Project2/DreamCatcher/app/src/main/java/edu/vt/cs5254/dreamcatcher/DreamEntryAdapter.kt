package edu.vt.cs5254.dreamcatcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.dreamcatcher.databinding.ListItemDreamEntryBinding

class DreamEntryHolder(
    val binding : ListItemDreamEntryBinding
): RecyclerView.ViewHolder(binding.root){
    lateinit var boundEntry: DreamEntry
        private set

    fun bind(dreamEntry: DreamEntry){
        boundEntry = dreamEntry
        //bind buttons
        binding.listItemButton.visibility = View.GONE
        binding.listItemButton.displayEntry(dreamEntry)
    }


    //extending
    private fun Button.displayEntry(dreamEntry: DreamEntry){
        visibility = View.VISIBLE
        text = dreamEntry.kind.toString()
        when(dreamEntry.kind){
            DreamEntryKind.CONCEIVED -> {
                setBackgroundWithContrastingText("green")
            }
            DreamEntryKind.DEFERRED -> {
                setBackgroundWithContrastingText("red")
            }
            DreamEntryKind.FULFILLED -> {
                setBackgroundWithContrastingText("silver")
            }
            DreamEntryKind.REFLECTION -> {
                isAllCaps = false
                text = dreamEntry.text
                setBackgroundWithContrastingText("blue")
            }
        }
    }
}

class DreamEntryAdapter(
    private val entries: List<DreamEntry>
) : RecyclerView.Adapter<DreamEntryHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamEntryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDreamEntryBinding.inflate(inflater,parent,false)
        return DreamEntryHolder(binding)
    }

    override fun onBindViewHolder(holder: DreamEntryHolder, position: Int) {
        val dreamEntry = entries[position]
        holder.bind(dreamEntry)// bind data and function
    }

    override fun getItemCount() = entries.size

}