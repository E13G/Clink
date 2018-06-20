package com.clink.jpinto.clink

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.animation.AnimationUtils
import com.clink.jpinto.clink.domain.Gate
import com.clink.jpinto.clink.utils.getSavedTheme
import com.clink.jpinto.clink.viewmodel.GateViewModel
import kotlinx.android.synthetic.main.activity_details.*



class DetailsActivity : AppCompatActivity() {

    val tag = "Details Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSavedTheme(this))
        setContentView(R.layout.activity_details)

        val code = intent.extras.getString("gateCode")

        val slide = Slide(Gravity.BOTTOM)
        slide.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.interpolator.linear_out_slow_in))
        window.enterTransition = slide

        val gateViewModel:GateViewModel = ViewModelProviders.of(this).get(GateViewModel::class.java)
        gateViewModel.loadGateByCode(code).observe(this, Observer {

            Log.e(tag, it.toString())
            if(it is Gate) {
                details_name.text = it.name
                details_description.text = it.description
                details_category.text = it.category
                val coordinates:String = it.latitude.toString() + ", " + it.longitude.toString()
                details_coordinates.text = coordinates
            }
        })
    }
}
