package vulnwalker.codes.hopes.firebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import vulnwalker.codes.hopes.MainActivity
import vulnwalker.codes.hopes.database.Account

import vulnwalker.codes.hopes.database.FirebaseToken
import vulnwalker.codes.hopes.database.KotlinHelper
import vulnwalker.codes.hopes.volley.URL
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.Response
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService;
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.HashMap


/**
 * Created by root on 05/09/17.
 */
class FirebaseGetId : FirebaseInstanceIdService() {
//    var currentEmail : String = ""
//    override fun onTokenRefresh() {
//        // Get updated InstanceID token.
//        val helper = KotlinHelper(applicationContext)
//        val refreshedToken = FirebaseInstanceId.getInstance().token
//        val dataUpdate = FirebaseToken(1, refreshedToken.toString())
//        helper.updateToken(dataUpdate)
//        for (data: Account in helper.getAccount()) {
//         currentEmail = data.email
//        }
//
//        val queue = Volley.newRequestQueue(applicationContext)
//        val response: String? = null
//        val postRequest = object : StringRequest(Method.POST, URL.SET_TOKEN, com.android.volley.Response.Listener<String>{
//            response ->
//        },
//                com.android.volley.Response.ErrorListener {
//                    toast("error")
//                }
//        ) {
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params.put("email", currentEmail)
//                params.put("token", refreshedToken.toString())
//                return params
//            }
//        }
//        postRequest.retryPolicy = DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        queue.add(postRequest)
//    }
}