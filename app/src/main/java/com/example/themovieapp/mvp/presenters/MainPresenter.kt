package com.example.themovieapp.mvp.presenters

import com.example.themovieapp.delegates.BannerViewHolderDelegate
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.delegates.ShowcaseViewHolderDelegate
import com.example.themovieapp.mvp.views.MainView

interface MainPresenter : IBasePresenter,BannerViewHolderDelegate,ShowcaseViewHolderDelegate,
    MovieViewHolderDelegate{

        fun initView(view : MainView)
        fun onTapGenre(genrePosition : Int)
}