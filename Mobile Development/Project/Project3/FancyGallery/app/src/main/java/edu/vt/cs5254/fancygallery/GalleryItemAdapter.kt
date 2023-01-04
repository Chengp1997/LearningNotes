package edu.vt.cs5254.fancygallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import edu.vt.cs5254.fancygallery.api.GalleryItem
import edu.vt.cs5254.fancygallery.databinding.ListItemGalleryBinding

//hold item
class GalleryItemHolder(
    private val binding: ListItemGalleryBinding
): RecyclerView.ViewHolder(binding.root){

    lateinit var boundGalleryItem: GalleryItem
        private set

    fun bind(galleryItem: GalleryItem, onItemClicked: (Uri) ->Unit){
        boundGalleryItem = galleryItem
        //use coil to bind the view -- better performance
        binding.itemImageView.load(galleryItem.url){
            placeholder(R.drawable.ic_placeholder)
            diskCachePolicy(CachePolicy.DISABLED)
        }
        binding.root.setOnClickListener { onItemClicked(galleryItem.photoPageUri) }
    }
}

//communicate between recyclerview and data
//set data to the view
class GalleryItemAdapter(
    private val galleryItems: List<GalleryItem>,
    private val onItemClicked: (Uri) -> Unit
): RecyclerView.Adapter<GalleryItemHolder>(){
    //create item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater,parent,false)
        return GalleryItemHolder(binding)
    }

    //bind data to the view
    override fun onBindViewHolder(holder: GalleryItemHolder, position: Int) {
        val item = galleryItems[position]
        holder.bind(item,onItemClicked)
    }

    override fun getItemCount() = galleryItems.size

}