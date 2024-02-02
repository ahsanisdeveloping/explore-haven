package com.example.explorehaven

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView

import com.example.explorehaven.databinding.RowBinding
import com.squareup.picasso.Picasso

class ListingAdapter(val listingList:List<Listing>, private var ctx: Context) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {
    inner class ListingViewHolder(var binding: RowBinding,clickListener: onItemClickListener) : RecyclerView.ViewHolder(binding.root){
        init{
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position:Int)

    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        Toast.makeText(ctx,"OnCreate",Toast.LENGTH_SHORT).show()

        return ListingViewHolder(binding,mListener)
    }
    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
//        Toast.makeText(ctx,"OnCreate",Toast.LENGTH_SHORT).show()

        with(holder.binding) {
            with(listingList[position]) {
                Picasso.get().load(this.image.toUri()).into(rowImage)
                rowName.text = this.name
                rowDesc.text = this.description
                rowImage.setImageURI(this.image.toUri())
                rowLocation.text = this.location
                rowRent.text = this.rent
                rowProperty.text = this.property
//               Toast.makeText(ctx,"hehehe",Toast.LENGTH_SHORT).show()

            }
        }
    }
    override fun getItemCount(): Int {
        return listingList.size
    }
}