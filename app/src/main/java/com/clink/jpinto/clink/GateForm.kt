package com.clink.jpinto.clink

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.clink.jpinto.clink.utils.getSavedTheme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_form.*


class GateForm : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSavedTheme(this))
        setContentView(R.layout.activity_gate_form)
        setSupportActionBar(toolbar)

        val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext())
        val edit: SharedPreferences.Editor = prefs.edit()
        edit.putBoolean(getString(R.string.pref_previously_started),true)
        edit.apply()

        btn_submit.setOnClickListener{

            submitForm()
        }

        input_gate_code.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event -> (
                submitForm()
            )})

    }

    /**
     * Validating form
     */
    private fun submitForm():Boolean {
        var errors = false

        if (!validateName()) {
            errors = true
        }

        if (!validateCategory()) {
            errors = true
        }

        if (!validateDescription()) {
            errors = true
        }

        if (!validateCode()) {
            errors = true
        }

        if(errors) return errors;

        val bundle = Bundle()
        bundle.putString("name"       , input_gate_name.text.toString())
        bundle.putString("category"   , input_gate_category.text.toString())
        bundle.putString("description", input_gate_description.text.toString())
        bundle.putString("code"       , input_gate_code.text.toString())
        //bundle.putDouble("latitude"   , input_gate_location.text.toString().split((",").toRegex())[0].toDouble())
        //bundle.putDouble("longitude"  , input_gate_location.text.toString().split((",").toRegex())[1].toDouble())

        val replyIntent = Intent()
        replyIntent.putExtras(bundle)
        setResult(AppCompatActivity.RESULT_OK, replyIntent)
        finish()

        return errors
    }

    override fun onBackPressed() {

        setResult(AppCompatActivity.RESULT_CANCELED, Intent())
        super.onBackPressed()
    }

    private fun validateName(): Boolean {

        if (TextUtils.isEmpty(input_gate_name.text)) {
            input_layout_name.error = (getString(R.string.err_msg_name))
            requestFocus(input_gate_name)
            return false
        } else {
            input_layout_name.isErrorEnabled = false
        }
        return true
    }

    private fun validateCategory(): Boolean {

        if (TextUtils.isEmpty(input_gate_category.text)) {
            input_layout_category.error = (getString(R.string.err_msg_category))
            requestFocus(input_gate_category)
            return false
        } else {
            input_layout_category.isErrorEnabled = false
        }
        return true
    }

    private fun validateDescription(): Boolean {

        if (TextUtils.isEmpty(input_gate_description.text)) {
            input_layout_description.error = (getString(R.string.err_msg_description))
            requestFocus(input_gate_description)
            return false
        } else {
            input_layout_description.isErrorEnabled = false
        }

        return true
    }

    private fun validateCode(): Boolean {

        if (TextUtils.isEmpty(input_gate_code.text)) {
            input_layout_code.error = (getString(R.string.err_msg_code))
            requestFocus(input_gate_code)
            return false
        } else {
            input_layout_code.isErrorEnabled = false
        }

        return true
    }

    /*private fun validateLocation(): Boolean {

        if (validateCoordinates()) {
            input_layout_location.error = (getString(R.string.err_msg_location))
            requestFocus(input_gate_code)
            return false
        } else {
            input_layout_location.isErrorEnabled = false
        }

        return true
    }

    private fun validateCoordinates(): Boolean {

        if(TextUtils.isEmpty(input_gate_location.text)) return true

        if(!input_gate_location.text.toString().split((",")).size.equals(2)) return true

        if(!input_gate_location.text.toString().split((","))[0].matches("-?\\d+(\\.\\d+)?".toRegex())) return true

        if(!input_gate_location.text.toString().split((","))[1].matches("-?\\d+(\\.\\d+)?".toRegex())) return true

        return false
    }*/

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }
}
