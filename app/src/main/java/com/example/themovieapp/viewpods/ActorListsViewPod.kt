package com.example.themovieapp.viewpods

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.adapter.ActorAdapter
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.databinding.ViewPodActorListsBinding

class ActorListsViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding : ViewPodActorListsBinding

    private var mActorAdapter : ActorAdapter ? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewPodActorListsBinding.bind(this)
    }

    fun setUpActorListViewPod(){
        setUpActorRecyclerView()
    }

    // Detail
    //this fun must be Public because activity will call to use this Fun !!
    fun setUpActorViewPod(backgroundColorReference : Int, titleText : String , moreTitleText : String){
        setBackgroundColor(ContextCompat.getColor(context,backgroundColorReference))
        binding.tvBestActor.text = titleText
        binding.tvMoreActors.text = moreTitleText
        binding.tvMoreActors.paintFlags = Paint.UNDERLINE_TEXT_FLAG //underline
        setUpActorRecyclerView()
    }

    fun setData(actorsList : List<ActorVO>){
        mActorAdapter?.setNewData(actorsList)
    }

    private fun setUpActorRecyclerView(){
        mActorAdapter = ActorAdapter()
        binding.rvActorLists.adapter = mActorAdapter
        binding.rvActorLists.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
    }
}