package com.assessment.olympicathletes.presenter

import com.assessment.olympicathletes.api.GeneralErrorHandler
import com.assessment.olympicathletes.contract.OlympicAthletesContract
import com.assessment.olympicathletes.manager.ApiManager
import com.assessment.olympicathletes.model.Athlete
import com.assessment.olympicathletes.model.Game
import com.assessment.olympicathletes.model.Result
import com.assessment.olympicathletes.mvp.BaseMvpPresenterImpl
import rx.Observable

class OlympicAthletesPresenter : BaseMvpPresenterImpl<OlympicAthletesContract.View>(),
    OlympicAthletesContract.Presenter {

    private lateinit var games: List<Game>

    override fun loadData() {
        // get the list of Games
        ApiManager.loadGames().subscribe(
            { games ->
                val listOfAthletesByGameIdRequests = ArrayList<Observable<*>>()
                this.games = games
                if (games.isNotEmpty()) {
                    this.games.forEach { game ->
                        listOfAthletesByGameIdRequests.add(ApiManager.loadAthletesByGameId(game.game_id))
                    }
                    loadAthletesByGameId(listOfAthletesByGameIdRequests)
                } else {
                    mView?.displayNoGamesView()
                }
            },
            GeneralErrorHandler(
                mView,
                true
            ) { throwable, errorBody, isNetwork -> mView?.showReloadButton() })
    }

    private fun loadAthletesByGameId(requests: ArrayList<Observable<*>>) {
        val listOfAllAthleteIds = ArrayList<String>()
        //Load all athletes by game id by sending calls "/games/{$GAME_ID}/athletes" one after the other
        Observable.zip(requests) {
            it.forEachIndexed { index, athletes ->
                games[index].athletes = athletes as ArrayList<Athlete>
                //create a list of array to store unique ids for all athletes - No need to send a separate call to fetch all athletes.
                athletes.forEach { athlete ->
                    if (!listOfAllAthleteIds.contains(athlete.athlete_id)) {
                        listOfAllAthleteIds.add(athlete.athlete_id)
                    }
                }
            }
            games
        }.subscribe({
            getAllAthletesResults(listOfAllAthleteIds)
        }) {
            it.message
            GeneralErrorHandler(
                mView,
                true
            ) { throwable, errorBody, isNetwork ->
                mView?.showReloadButton()
            }
        }
    }

    private fun getAllAthletesResults(listOfAllAthletesIds: ArrayList<String>) {
        val listOfResultsByAthleteIdRequests = ArrayList<Observable<*>>()
        listOfAllAthletesIds.forEach { athlete_id ->
            listOfResultsByAthleteIdRequests.add(ApiManager.loadResultsByAthleteId(athlete_id))
        }
        //Load all results by athlete id by sending calls "/athletes/{$ATHLETE_ID}/results" one after the other
        Observable.zip(listOfResultsByAthleteIdRequests) { results ->
            //Get the results and save them against the respective athletes.
            games.forEach { game ->
                game.athletes?.forEach { gameAthlete ->
                    listOfAllAthletesIds.forEachIndexed { index, athlete_id ->
                        if (gameAthlete.athlete_id == athlete_id) {
                            val result = results[index] as ArrayList<Result>
                            val iterator = result.iterator()
                            while (iterator.hasNext()) {
                                val item = iterator.next()
                                if (item.city == game.city && item.year.toString() == game.year) {
                                    if (gameAthlete.results == null) {
                                        gameAthlete.results = mutableListOf()
                                    }
                                    gameAthlete.results!!.add(item)
                                }
                            }
                        }
                    }
                }
            }
            games
        }.subscribe({
            mView?.showData(it)
        }) {
            GeneralErrorHandler(
                mView,
                true
            ) { throwable, errorBody, isNetwork ->
                mView?.showReloadButton()
            }
        }

    }
}