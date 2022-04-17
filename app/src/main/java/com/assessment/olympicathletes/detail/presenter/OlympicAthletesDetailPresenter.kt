package com.assessment.olympicathletes.detail.presenter

import com.assessment.olympicathletes.api.GeneralErrorHandler
import com.assessment.olympicathletes.detail.contract.OlympicAthletesDetailContract
import com.assessment.olympicathletes.manager.ApiManager
import com.assessment.olympicathletes.mvp.BaseMvpPresenterImpl

class OlympicAthletesDetailPresenter : BaseMvpPresenterImpl<OlympicAthletesDetailContract.View>(),
    OlympicAthletesDetailContract.Presenter {

    override fun loadResults(athleteId: String) {
        // get all Medals
        ApiManager.loadResultsByAthleteId(athleteId).subscribe(
            { results ->
                mView?.showResults(results)
            },
            GeneralErrorHandler(
                mView,
                true
            ) { throwable, errorBody, isNetwork -> mView?.showReloadButton() })
    }
}