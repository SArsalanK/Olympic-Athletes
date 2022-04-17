package com.assessment.olympicathletes.detail.contract

import com.assessment.olympicathletes.model.Result
import com.assessment.olympicathletes.mvp.BaseMvpPresenter
import com.assessment.olympicathletes.mvp.BaseMvpView

interface OlympicAthletesDetailContract {
    interface View : BaseMvpView {
        fun showReloadButton()
        fun showResults(it: List<Result> = mutableListOf())
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun loadResults(athleteId: String)
    }
}