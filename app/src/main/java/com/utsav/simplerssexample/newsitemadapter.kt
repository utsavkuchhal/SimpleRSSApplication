package com.utsav.simplerssexample
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.utsav.simplerssexample.newsitemadapter.*
import kotlinx.android.synthetic.main.items.view.*

class newsitemadapter (private val listitems:ArrayList<newsitem>):RecyclerView.Adapter<itemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
            itemsViewHolder (LayoutInflater.from(parent?.context).inflate(
            R.layout.items,parent,false))


    override fun getItemCount()=listitems.size

    override fun onBindViewHolder(holder: itemsViewHolder, position: Int) {
        holder?.bind(listitems[position])
    }


    class itemsViewHolder(itemView: View?):RecyclerView.ViewHolder(itemView!!) {
        fun bind(listitem: newsitem){
            itemView.tv2?.text=listitem.summary;
            itemView.tv1?.text=listitem.title;
            //itemView.tv3?.text=listitem.guid;
           // itemView.tv4?.text=listitem.content_html
            itemView.tv5?.text=listitem.url;
        }

    }

}