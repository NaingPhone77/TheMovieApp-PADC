package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.databinding.ViewHolderActorBinding
import com.example.themovieapp.utils.IMAGE_BASE_URL

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding : ViewHolderActorBinding

    init {
        binding = ViewHolderActorBinding.bind(itemView)
    }

    fun bindData(actor : ActorVO){
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${actor.profilePath}")
            .into(binding.ivBestActor)

        binding.tvActorName.text = actor.name
    }
}