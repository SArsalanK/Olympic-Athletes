package com.assessment.olympicathletes.contract

import com.assessment.olympicathletes.model.Game
import com.assessment.olympicathletes.mvp.BaseMvpPresenter
import com.assessment.olympicathletes.mvp.BaseMvpView
import rx.Observable

interface OlympicAthletesContract {

    interface View : BaseMvpView {
        fun showReloadButton()
        fun showData(it: List<Game> = mutableListOf())
        fun displayNoGamesView()
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun loadData()
    }

}