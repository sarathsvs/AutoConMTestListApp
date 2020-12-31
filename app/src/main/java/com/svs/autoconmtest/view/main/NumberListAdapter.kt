package com.svs.autoconmtest.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svs.autoconmtest.R
import kotlinx.android.synthetic.main.adapter_item_number.view.*


class NumberListAdapter(var listNumbers: ArrayList<String>, val mContext: Context,
                        val listener: (String, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DefaultViewHOlder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_number, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return listNumbers.size
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_NORMAL
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is DefaultViewHOlder){
            holder.bind(listNumbers[position], mContext, listener)
        }

    }


   inner class DefaultViewHOlder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(number: String, mContext: Context, listener: (String, Int) -> Unit)= with(itemView) {

            tvNumber.text=number

            itemView.setOnClickListener {
                listener(number,0)
            }
        }
    }


    fun updateList(list:ArrayList<String>){
        this.listNumbers=list
        notifyDataSetChanged()
    }
    
    companion object{
        const val TYPE_NORMAL=1
    }

}