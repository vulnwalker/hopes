package vulnwalker.codes.hopes

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import vulnwalker.codes.hopes.base.config
import java.util.HashMap
import vulnwalker.codes.hopes.volley.URL
import vulnwalker.codes.hopes.database.KotlinHelper


class MainActivity : AppCompatActivity(){
    val configClass = config()
    val databaseHelper = KotlinHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLogin()
        buttonLogin.setOnClickListener{
            databaseHelper.sqlDelete("member"," 1=1")
            login(email.text,password.text)
        }
    }

    fun checkLogin(){
        if(databaseHelper.sqlRowCount("select * from member").equals("1")){
            val i = Intent(this@MainActivity, MainMenu::class.java)
                startActivity(i)
                finish()
        }else{
            configClass.alert(this@MainActivity,databaseHelper.sqlDelete("member","1=1"))
            databaseHelper.sqlQuery(databaseHelper.sqlDelete("member","1=1"))
            configClass.alert(this@MainActivity,databaseHelper.sqlRowCount("select * from member"))
        }
    }


    fun login(email: Editable, password: Editable){
        val queue = Volley.newRequestQueue(this@MainActivity)
        val response: String? = null
        val postRequest = object : StringRequest(Method.POST, URL.AUTH, Response.Listener<String>{
            response ->
            val resp = JSONObject(response)
            val err = resp.getString("err")
            val cek = resp.getString("cek")
            if(err.equals("")){
                val jsonContent = resp.getString("content").toString()
                val content = JSONObject(jsonContent)
                val pesanResponse = DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }
                configClass.fancyAlert(this@MainActivity,"Login Success",FancyToast.SUCCESS)
                val dataMember = arrayOf(
                        arrayOf("email",email.toString()),
                        arrayOf("nama",content.getString("Nama").toString()),
                        arrayOf("nomor_telepon",content.getString("NomorTelepon").toString())
                )
                val queryInsert = databaseHelper.sqlInsert("member",dataMember)
                databaseHelper.sqlQuery(queryInsert)
                val i = Intent(this@MainActivity, MainMenu::class.java)
                startActivity(i)
                finish()
            }else{
                configClass.fancyAlert(this@MainActivity,err,FancyToast.ERROR)
            }

        },
                Response.ErrorListener {
                    toast("error")
                }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email", email.toString())
                params.put("password", password.toString())
                return params
            }
        }
        postRequest.retryPolicy = DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(postRequest)
    }

}
