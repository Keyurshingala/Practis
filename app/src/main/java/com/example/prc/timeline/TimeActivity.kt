package com.example.prc.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.prc.R
import com.example.prc.databinding.ActivityTimeBinding
import com.example.prc.databinding.RvShowBinding
import com.example.prc.databinding.RvTimelineBinding
import com.example.prc.databinding.RvTvChannaleBinding
import com.example.prc.log
import com.example.prc.currentDate
import com.example.prc.startHm
import java.text.SimpleDateFormat
import java.util.*

class TimeActivity : AppCompatActivity() {

    lateinit var bind: ActivityTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)

        val dateStart = sdf.parse("${currentDate()} ${startHm()}") as Date
        val dateEnd = sdf.parse("${currentDate()} 24:00") as Date

        ("Date Start: $dateStart").log()
        ("Date End: $dateEnd").log()

        var list = arrayListOf<String>()

        var dif: Long = dateStart.time
        while (dif < dateEnd.time) {
            val slot = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(dif))
            "Hour Slot --> $slot".log()
            list.add(slot)
            dif += 1800000
        }

        bind.rvTime.adapter = CustomAdapter(list)
        bind.rvCh.adapter = ChAdapter(arrayListOf(1))
    }
}

class CustomAdapter<T>(var list: List<T>) : RecyclerView.Adapter<CustomAdapter.VH>() {

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val bind = holder.binding
        val data = list[pos]

        bind.tv.text = "$data"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(RvTimelineBinding.inflate(LayoutInflater.from(parent.context)))
    class VH(var binding: RvTimelineBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = list.size
}

class ChAdapter<T>(var list: List<T>) : RecyclerView.Adapter<ChAdapter.VH>() {

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val bind = holder.binding
        val data = list[pos]

        bind.rvShow.adapter = ShowAdapter(arrayListOf(2, 2, 1, 1, 1, 2, 1, 2, 2, 2, 2))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(RvTvChannaleBinding.inflate(LayoutInflater.from(parent.context)))
    class VH(var binding: RvTvChannaleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = 1
}

class ShowAdapter<T>(var list: List<T>) : RecyclerView.Adapter<ShowAdapter.VH>() {

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val bind = holder.binding
        val data = list[pos]


        bind.iv.setImageResource(R.drawable.ic_launcher_background)

        bind.iv.layoutParams = bind.iv.layoutParams.apply { width = if (data == 2) (270 * 2) + 40 else 270 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(RvShowBinding.inflate(LayoutInflater.from(parent.context)))
    class VH(var binding: RvShowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = list.size
}