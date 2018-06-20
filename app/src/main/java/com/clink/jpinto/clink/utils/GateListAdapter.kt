package com.clink.jpinto.clink.utils

import android.support.v7.app.AppCompatActivity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.clink.jpinto.clink.DetailsActivity
import com.clink.jpinto.clink.R
import com.clink.jpinto.clink.domain.Gate

class GateListAdapter ( val context: Context) : RecyclerView.Adapter<GateViewHolder>() {

    var roomGateList : List<Gate> = emptyList()


    fun setGates(roomGates: List<Gate>) {
        roomGateList = roomGates
        Log.e("List Adapter",roomGateList.toString())
        notifyDataSetChanged()
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return roomGateList.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GateViewHolder {
        return GateViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_card_item, parent, false))
    }

    // Binds each gate in the ArrayList to a view
    override fun onBindViewHolder(holder: GateViewHolder, position: Int) {
        holder.tvGateName.text = roomGateList.get(position).name
        holder.tvGateCategory.text = roomGateList.get(position).category
        holder.tvGateDescription.text = roomGateList.get(position).description
        holder.ivGateLocation.setImageResource(R.drawable.location)

        holder.btnOpen.setOnClickListener {
            val result = sendRequest(roomGateList.get(position).code)
            val text = when (result){
                null -> "Your api key is not correct"
                "0"  -> "Gate is already opening"
                else -> "Gate is opening"
            }
            val duration = Toast.LENGTH_LONG
            Toast.makeText(context, text, duration).show()
        }

        holder.cardView.setOnClickListener { _ ->
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("gateCode", roomGateList.get(position).code)
            intent.action = Intent.ACTION_VIEW
            startActivity(context,intent, ActivityOptions.makeSceneTransitionAnimation(context as AppCompatActivity,holder.ivGateLocation,"details_location_image").toBundle());
        }
    }
}

class GateViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val cardView = view
    val tvGateName = view.findViewById<TextView>(R.id.info_name)
    val tvGateCategory = view.findViewById<TextView>(R.id.info_category)
    val tvGateDescription = view.findViewById<TextView>(R.id.info_description)
    val ivGateLocation = view.findViewById<ImageView>(R.id.location_image)

    val btnOpen = view.findViewById<Button>(R.id.open_gate)

}
