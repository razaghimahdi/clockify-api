package com.razzaghi.clockifyapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.model.TimeEntry
import com.razzaghi.clockifyapi.util.DateConvector.GregorianCalendarToJalaliCalendar
import kotlinx.android.synthetic.main.item_time_entry_preview.view.*

class TimeEntryAdapter :
    PagingDataAdapter<TimeEntry, TimeEntryAdapter.TimeEntryViewHolder>(TIME_ENTRY_COMPARATOR) {


    inner class TimeEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeEntryAdapter.TimeEntryViewHolder {
        return TimeEntryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_time_entry_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimeEntryViewHolder, position: Int) {

        val timeEntry = getItem(position)

        holder.itemView.apply {

            if (timeEntry!!.description.isEmpty()) {
                linearDesc.visibility = View.GONE
            } else {
                linearDesc.visibility = View.VISIBLE
                txtDescription.text = timeEntry.description
            }

            setTextStartDate(txtStart,timeEntry.timeInterval.start)
            setTextEndDate(txtEnd,timeEntry.timeInterval.end)


        }
    }

    private fun setTextStartDate(txtStart: TextView, start: String) {
        val dateAndTime = start.split("T").toTypedArray()
        val date=dateAndTime[0].split("-").toTypedArray()
        val time=dateAndTime[1].split(":").toTypedArray()
        val minWithZ=time[2].split("Z").toTypedArray()

        val jalaliCalendar=GregorianCalendarToJalaliCalendar(date[0].toInt(),date[1].toInt(),date[2].toInt())
        val jajaliDate="${jalaliCalendar.year}/${jalaliCalendar.month}/${jalaliCalendar.day}"
        val newTime="${time[0]}:${time[1]}:${minWithZ[0]}"

        //txtStart.text = "${jajaliDate}-${newTime}"
        txtStart.text = "تاریخ: $jajaliDate، ساعت: $newTime"
    }

    private fun setTextEndDate(txtEnd: TextView, end: String) {
        val dateAndTime = end.split("T").toTypedArray()
        val date=dateAndTime[0].split("-").toTypedArray()
        val time=dateAndTime[1].split(":").toTypedArray()
        val minWithZ=time[2].split("Z").toTypedArray()

        val jalaliCalendar=GregorianCalendarToJalaliCalendar(date[0].toInt(),date[1].toInt(),date[2].toInt())
        val jajaliDate="${jalaliCalendar.year}/${jalaliCalendar.month}/${jalaliCalendar.day}"
        val newTime="${time[0]}:${time[1]}:${minWithZ[0]}"

        txtEnd.text = "تاریخ: $jajaliDate، ساعت: $newTime"
    }


    companion object {
        private val TIME_ENTRY_COMPARATOR = object : DiffUtil.ItemCallback<TimeEntry>() {
            override fun areItemsTheSame(oldItem: TimeEntry, newItem: TimeEntry) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TimeEntry, newItem: TimeEntry) =
                oldItem == newItem
        }
    }


}