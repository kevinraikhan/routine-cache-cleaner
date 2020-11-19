package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.History
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.StringUtils
import kotlinx.android.synthetic.main.history_item.view.*


class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {
    private var histories: List<History> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryHolder(itemView)
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val currentHistory: History = histories[position]

        holder.textViewTitle.text = StringUtils.bytesToHuman(currentHistory.amountCleared.toLong())
        holder.textViewDescription.text = StringUtils.milisToDateString(currentHistory.time)
    }

    fun setHistory(histories: List<History>) {
        this.histories = histories
        notifyDataSetChanged()
    }

    inner class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.text_view_title
        var textViewDescription: TextView = itemView.text_view_description
    }
}