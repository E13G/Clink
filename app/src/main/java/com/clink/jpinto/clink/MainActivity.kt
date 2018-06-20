package com.clink.jpinto.clink

import android.support.v7.app.AppCompatActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.clink.jpinto.clink.domain.Gate
import com.clink.jpinto.clink.utils.GateListAdapter
import com.clink.jpinto.clink.utils.getSavedTheme
import com.clink.jpinto.clink.viewmodel.GateViewModel


class MainActivity : AppCompatActivity() {

    val tag = "Main Activity"
    val NEW_GATE_ACTIVITY_REQUEST_CODE = 1

    private var gateViewModel: GateViewModel? = null
    lateinit var gate_list_recycler_view: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSavedTheme(this))
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        gate_list_recycler_view = findViewById(R.id.gate_list_recycler_view)
        Log.e(tag, "onCreate")

        // Creates a vertical Layout Manager
        val adapter =  GateListAdapter(this)
        gate_list_recycler_view.adapter = adapter;
        gate_list_recycler_view.layoutManager = LinearLayoutManager(this)

        gateViewModel = ViewModelProviders.of(this).get(GateViewModel::class.java)
        gateViewModel?.loadGateList()?.observe(this, Observer {

            Log.e(tag,"change")
            Log.e(tag, it.toString())
            if(it !== null)  adapter.setGates(it)

        })



        (findViewById<View>(R.id.fab)).setOnClickListener { _ ->
            val intent = Intent(this, GateForm::class.java)
            startActivityForResult(intent,NEW_GATE_ACTIVITY_REQUEST_CODE)
        }
    }


    fun clearList():Boolean{

        Log.e(tag,"Clear List")

        gateViewModel?.deleteAll()

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode.equals(NEW_GATE_ACTIVITY_REQUEST_CODE) && resultCode.equals(AppCompatActivity.RESULT_OK)) {

            val gate = Gate(data.getStringExtra("code"),
                    data.getStringExtra("name"),
                    data.getStringExtra("category"),
                    data.getStringExtra("description"),
                    data.getDoubleExtra("latitude",38.72440632017217),
                    data.getDoubleExtra("longitude",-9.15580234204458))

            gateViewModel!!.addGate(gate)

        }
    }

    override fun onResume() {
        super.onResume()
        val prefs :SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        val previouslyStarted:Boolean= prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (!previouslyStarted) {
            val intent = Intent(this, GateForm::class.java)
            startActivityForResult(intent,NEW_GATE_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_theme_swap -> themeSwap()
            R.id.action_delete -> clearList()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun themeSwap():Boolean{
        Log.e(tag,"theme Swap")
        val theme = getSharedPreferences("Theme",Context.MODE_PRIVATE).getString("theme", "darkRed")
        Log.e(tag,(theme == "indigo").toString())
        if(theme == "indigo"){
            saveTheme("darkRed")
        }else{
            saveTheme("indigo")
        }
        return true
    }

    private fun saveTheme(value: String) {
        Log.e(tag,"saveTheme")
        val editor = getSharedPreferences("Theme",Context.MODE_PRIVATE).edit()
        Log.e(tag,value)
        editor.putString("theme", value)
        editor.apply()
        recreate()
    }

}
