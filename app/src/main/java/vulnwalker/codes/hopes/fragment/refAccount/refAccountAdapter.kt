package `in`.creativelizard.recyclerviewtest

import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import vulnwalker.codes.hopes.R
import java.util.ArrayList
import vulnwalker.codes.hopes.base.config





/**
 * Created by siddhartha on 23/5/17.
 */

internal class refAccountAdapter(private val arrayList: ArrayList<refAccountData>,
                                 private val context: Context,
                                 private val layout: Int) : RecyclerView.Adapter<refAccountAdapter.ViewHolder>() {
    private lateinit var namaAccount : TextView
    private lateinit var idAccount : TextView
    private lateinit var publisherID : TextView
    private lateinit var bannerAdsUnit : TextView
    private lateinit var popupAdsUnit : TextView
    private lateinit var videoAdsUnit : TextView


    val configClass = config()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): refAccountAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        namaAccount = v.findViewById(R.id.namaAccount)
        publisherID = v.findViewById(R.id.publisherID)
        bannerAdsUnit = v.findViewById(R.id.bannerAdsUnitId)
        popupAdsUnit = v.findViewById(R.id.popupAdsUnitID)
        videoAdsUnit = v.findViewById(R.id.videoAdsUnitId)
        idAccount = v.findViewById(R.id.idAccount)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: refAccountAdapter.ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(items: refAccountData) {
            namaAccount.text = items.namaAccount
            idAccount.text = items.idAccount
            publisherID.text = items.publisherID
            bannerAdsUnit.text = items.bannerAdsUnit
            popupAdsUnit.text = items.popupAdsUnit
            videoAdsUnit.text = items.videoAdsUnit
            itemView.setOnClickListener {
                itemAccountClicked(items.idAccount.toString())
            }
        }
    }

    fun itemAccountClicked(idAccount : String){
        configClass.alert(context,idAccount)
    }


    internal class refAccountData {
        var namaAccount: String? = null
        var publisherID: String? = null
        var bannerAdsUnit: String? = null
        var popupAdsUnit: String? = null
        var videoAdsUnit: String? = null
        var idAccount: String? = null
    }




}
