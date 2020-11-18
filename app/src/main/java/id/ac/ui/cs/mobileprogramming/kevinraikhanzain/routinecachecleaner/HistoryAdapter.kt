package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        var currentHistory: History = histories.get(position)
        holder.textViewTitle.text = currentHistory.title
        holder.textViewDescription.text = currentHistory.amountCleared.toString()
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