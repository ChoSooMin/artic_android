package com.android.artic.ui.mypage.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.artic.R
import com.android.artic.data.Archive
import com.bumptech.glide.Glide

class MyPageMeAdapter(val ctx: Context, var data:List<Archive>): RecyclerView.Adapter<MyPageMeAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_mypage_me,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.run {
            val cur=data[position]
            img_url?.let {
                Glide.with(ctx)
                    .load(cur.title_img_url)
                    .into(it)

            }

        directory_name?.text=cur.title
        holder.container?.setOnClickListener {
        }
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val container=itemView.findViewById<View?>(R.id.rv_item_mypage_me_container)
        val img_url=itemView.findViewById<ImageView?>(R.id.rv_item_mypage_me_img)?.apply {
            clipToOutline = true
        }
        val directory_name=itemView.findViewById<TextView?>(R.id.rv_item_mypage_me_directory_name)
    }
}