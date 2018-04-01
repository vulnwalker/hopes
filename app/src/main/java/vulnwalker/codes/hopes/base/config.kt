package vulnwalker.codes.hopes.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.jetbrains.anko.toast
import org.json.JSONObject
import vulnwalker.codes.hopes.MainMenu
import vulnwalker.codes.hopes.volley.URL
import java.util.HashMap
import com.shashank.sony.fancytoastlib.FancyToast


/**
 * Created by root on 25/03/18.
 */
public class config{
    fun alert(namaClass: Context,isiPesan : String){
        val pesanResponse = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }
            }
        }
        val builder = AlertDialog.Builder(namaClass)
        builder.setMessage(isiPesan).setPositiveButton("Tutup", pesanResponse)
                .setNegativeButton("", pesanResponse).show()
    }
    fun fancyAlert(namaClass : Context,isiPesan : String,alertType : Int){
        // 0 = default
        // 1 = success
        // 2 = info
        // 3 = warning
        // 4 = error
        // 5 = confusing
        FancyToast.makeText(namaClass,isiPesan,FancyToast.LENGTH_LONG,alertType,true).show();
    }
    fun curlSync(namaClass: Context,parameter :  HashMap<String, String>,urlApi : String): JSONObject {
        val queue = Volley.newRequestQueue(namaClass)
        val response: String? = null
        val postRequest = object : StringRequest(Method.POST, urlApi, Response.Listener<String>{
            response ->
            val resp = JSONObject(response)

        },
                Response.ErrorListener {

                }
        ) {
            override fun getParams(): Map<String, String> {

                return parameter
            }
        }
        postRequest.retryPolicy = DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(postRequest)
        return JSONObject(response)
    }
}