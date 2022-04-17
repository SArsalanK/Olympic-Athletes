package com.assessment.olympicathletes.detail.view

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.olympicathletes.R
import com.assessment.olympicathletes.databinding.OlympicAthletesDetailActivityBinding
import com.assessment.olympicathletes.detail.contract.OlympicAthletesDetailContract
import com.assessment.olympicathletes.detail.presenter.OlympicAthletesDetailPresenter
import com.assessment.olympicathletes.detail.view.list.MedalsAdapter
import com.assessment.olympicathletes.model.Athlete
import com.assessment.olympicathletes.model.Result
import com.assessment.olympicathletes.mvp.BaseMvpActivity
import com.assessment.olympicathletes.view.list.AthletesViewHolder.Companion.ATHLETE_KEY
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class OlympicAthletesDetailActivity :
    BaseMvpActivity<OlympicAthletesDetailContract.View, OlympicAthletesDetailContract.Presenter>(),
    OlympicAthletesDetailContract.View {

    private lateinit var binding: OlympicAthletesDetailActivityBinding
    private lateinit var athlete: Athlete

    override var mPresenter: OlympicAthletesDetailContract.Presenter =
        OlympicAthletesDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = OlympicAthletesDetailActivityBinding.inflate(layoutInflater)
        athlete = intent.getSerializableExtra(ATHLETE_KEY) as Athlete
        setContentView(binding.root)
        loadAthleteData()
        binding.progressBarMedals.visibility = View.VISIBLE
        mPresenter.loadResults(athlete.athlete_id)
    }

    private fun loadAthleteData() {
        binding.progressBarPhoto.visibility = View.VISIBLE
        Picasso.with(this).load(getString(R.string.photo_url, athlete.athlete_id))
            .into(binding.photo, object : Callback {
                override fun onSuccess() {
                    binding.progressBarPhoto.visibility = View.INVISIBLE
                }

                override fun onError() {
                    binding.progressBarPhoto.visibility = View.INVISIBLE
                }
            })

        binding.toolbarTitle.text = athlete.name + " " + athlete.surname + " details"

        val name = "<b>" + "Name:" + "</b> " + athlete.name + " " + athlete.surname
        val dob = "<b>" + "DOB:" + "</b> " + athlete.dateOfBirth
        val weight = "<b>" + "Weight:" + "</b> " + athlete.weight.toString() + "kg"
        val height = "<b>" + "Height:" + "</b> " + athlete.height.toString() + "cm"

        binding.name.text = HtmlCompat.fromHtml(name, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.dob.text = HtmlCompat.fromHtml(dob, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.weight.text = HtmlCompat.fromHtml(weight, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.height.text = HtmlCompat.fromHtml(height, HtmlCompat.FROM_HTML_MODE_LEGACY);
        binding.bio.text = athlete.bio.trim()

        binding.reloadButton.setOnClickListener {
            binding.progressBarMedals.visibility = View.VISIBLE
            mPresenter.loadResults(athlete.athlete_id)
        }
        binding.toolbarBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun initMedalsList(results: List<Result>) {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.medalsList.layoutManager = linearLayoutManager
        val medalsAdapter = MedalsAdapter(results)
        binding.medalsList.adapter = medalsAdapter
        binding.medalsList.isNestedScrollingEnabled = false
    }

    override fun showReloadButton() {
        binding.progressBarMedals.visibility = View.GONE
        binding.medalsList.visibility = View.GONE
        binding.reloadButton.visibility = View.VISIBLE
    }

    override fun showResults(it: List<Result>) {
        binding.progressBarMedals.visibility = View.GONE
        binding.reloadButton.visibility = View.GONE
        binding.medalsList.visibility = View.VISIBLE
        initMedalsList(it)
    }
}