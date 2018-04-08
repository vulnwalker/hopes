package vulnwalker.codes.hopes.fragment.refAccount

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import vulnwalker.codes.hopes.R


import vulnwalker.codes.hopes.fragment.refAccount.dataAccount.DummyItem

import kotlinx.android.synthetic.main.fragment_item.view.*
import vulnwalker.codes.hopes.base.config


/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class dataAccountAdapter(
        private val mValues: List<DummyItem>,
        private val mListener: refAccount.OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<dataAccountAdapter.ViewHolder>() {


    val configClass = config()
    private val mOnClickListener: View.OnClickListener


    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.

            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.id
        holder.mContentView.text = item.content

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        // Send the event to the host activity

    }
}
