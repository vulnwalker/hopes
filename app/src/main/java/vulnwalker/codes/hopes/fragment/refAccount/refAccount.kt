package vulnwalker.codes.hopes.fragment.refAccount



import `in`.creativelizard.recyclerviewtest.refAccountAdapter
import `in`.creativelizard.recyclerviewtest.refAccountAdapter.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import vulnwalker.codes.hopes.R
import vulnwalker.codes.hopes.volley.URL
import com.android.volley.*
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap
import org.json.JSONArray
import vulnwalker.codes.hopes.base.config
import vulnwalker.codes.hopes.database.KotlinHelper
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class refAccount : Fragment() {
    private var refAccountAdapter: refAccountAdapter? = null
    private var arrayList: ArrayList<refAccountData>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var rlItems : RecyclerView
    val configClass = config()
    lateinit var databaseHelper: KotlinHelper
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.ref_account_fragment, container, false)
        rlItems = v.findViewById(R.id.rcc)
        initialize()
        setupList()
        loadData()



        return v

    }
    private fun loadData() {
        databaseHelper = KotlinHelper(context)
        val queue = Volley.newRequestQueue(context)
        val response: String? = null
        val postRequest = object : StringRequest(Method.POST, URL.LIST_AKUN, Listener<String>{
            response ->
            val resp = JSONObject(response)
            val err = resp.getString("err")
            val cek = resp.getString("cek")
            if(err.equals("")){
                databaseHelper.sqlQuery(databaseHelper.sqlDelete("account","1=1"))
                val jsonContent = resp.getString("content").toString()
                val jsonArrayContent = JSONArray(jsonContent)
                for (i in 0..(jsonArrayContent.length() - 1)) {
                    val content = jsonArrayContent.getJSONObject(i)
                    val myItem = refAccountData()
                    myItem.namaAccount = content.getString("NamaUnit")
                    myItem.publisherID = content.getString("PublisherId")
                    myItem.bannerAdsUnit = content.getString("BannerAds")
                    myItem.popupAdsUnit = content.getString("PopupAds")
                    myItem.videoAdsUnit = content.getString("VideoAds")
                    myItem.idAccount = content.getString("IdUnit")
                    arrayList!!.add(myItem)
                    val dataAccount = arrayOf(
                            arrayOf("id",content.getString("IdUnit")),
                            arrayOf("nama_akun",content.getString("NamaUnit").toString()),
                            arrayOf("pub_id",content.getString("BannerAds").toString()),
                            arrayOf("banner_id",content.getString("BannerAds").toString()),
                            arrayOf("popup_id",content.getString("PopupAds").toString()),
                            arrayOf("video_id",content.getString("VideoAds").toString())
                    )
                    databaseHelper.sqlQuery(databaseHelper.sqlInsert("account",dataAccount))

                }
                refAccountAdapter!!.notifyDataSetChanged()


            }else{
            }

        },
                Response.ErrorListener {
                    //  toast("error")
                }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("idMember", "1")
                return params
            }
        }
        postRequest.retryPolicy = DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(postRequest)



    }

    private fun setupList() {
        rlItems!!.layoutManager = layoutManager
        rlItems!!.adapter = refAccountAdapter
    }

    private fun initialize() {
        arrayList = ArrayList<refAccountData>()
        layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        refAccountAdapter = refAccountAdapter(arrayList!!, context, R.layout.item_ref_account)

    }



}// Required empty public constructor

