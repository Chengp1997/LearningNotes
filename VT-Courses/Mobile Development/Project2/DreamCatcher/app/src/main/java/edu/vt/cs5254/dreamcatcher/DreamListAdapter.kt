package edu.vt.cs5254.dreamcatcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.dreamcatcher.databinding.ListItemDreamBinding
import java.util.*

//hold item view
class DreamHolder(
    val binding: ListItemDreamBinding
): RecyclerView.ViewHolder(binding.root){
    lateinit var boundDream: Dream
        private set
    fun bind(dream: Dream, onDreamClicked:(dreamId: UUID) -> Unit){
        boundDream = dream
        binding.listItemTitle.text = dream.title
        val reflectionCount = "Reflections: "+
            dream.entries.filter { it.kind == DreamEntryKind.REFLECTION }.size.toString()
        binding.listItemReflectionCount.text = reflectionCount
        binding.listItemImage.visibility = if (dream.isFulfilled){
            binding.listItemImage.setImageResource(R.drawable.dream_fulfilled_icon)
            View.VISIBLE
        }else if (dream.isDeferred){
            binding.listItemImage.setImageResource(R.drawable.dream_deferred_icon)
            View.VISIBLE
        }else{
            View.GONE
        }
        //if the root is clicked, trigger the event passed in
        binding.root.setOnClickListener{
            onDreamClicked(dream.id)
        }
    }
}

//creates holder for recycler view, bind data to holder
class DreamListAdapter(
    private val dreams: List<Dream>,
    private val onDreamClicked: (dreamId: UUID) -> Unit
    ) :RecyclerView.Adapter<DreamHolder>(){

    //create view holder, and binding, once created, remain there
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDreamBinding.inflate(inflater,parent,false)
        return DreamHolder(binding)
    }

    //populate given holder, be called very often, because recycle!
    override fun onBindViewHolder(holder: DreamHolder, position: Int) {
        val dream = dreams[position]
        holder.bind(dream,onDreamClicked)// bind data and function
    }

    override fun getItemCount() = dreams.size

}