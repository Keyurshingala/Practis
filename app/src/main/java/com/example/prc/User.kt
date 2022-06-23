package com.example.prc

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prc.databinding.RvCaBinding
import com.example.prc.databinding.RvPosterHBinding

//data class User(val name: String = "abc", val email: String = "abc@g.co", val imgurl: List<String> = arrayListOf("https://picsum.photos/id/237/200/300", "https://picsum.photos/id/237/200/300", "https://picsum.photos/id/237/200/300", "https://picsum.photos/id/237/200/300", "https://picsum.photos/id/237/200/300", "https://picsum.photos/id/237/200/300", "https://picsum.photos/id/237/200/300"))
//
//class PosterImgHorizontalAdapter(var context: Context, var list: List<String>) : RecyclerView.Adapter<PosterImgHorizontalAdapter.VH>() {
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//
//        val bind = holder.binding
//        val posters = list[position]
//
//
//        bind.ivPosterImg.load(posters)
//
//        bind.ivLike.setOnClickListener {
//            "as".log()
//            notifyDataSetChanged()
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(RvPosterHBinding.inflate(LayoutInflater.from(parent.context)))
//    inner class VH(var binding: RvPosterHBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun getItemCount() = list.size
//}
//
//class CustomAdapter(var activity: Activity, var list: User) : RecyclerView.Adapter<CustomAdapter.VH>() {
//
//    override fun onBindViewHolder(holder: VH, pos: Int) {
//        val bind = holder.binding
//
//        bind.rvCatList.adapter = PosterImgHorizontalAdapter(activity, list.imgurl)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(RvCaBinding.inflate(LayoutInflater.from(parent.context)))
//    inner class VH(var binding: RvCaBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun getItemCount() = 1
//}
