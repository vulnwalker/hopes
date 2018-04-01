package vulnwalker.codes.hopes.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

/**
 * Created by root on 05/09/17.
 */


class KotlinHelper(context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CREATE TABLE member (email TEXT,nama TEXT,nomor_telepon TEXT,no_telepon TEXT,saldo Text,firebase_id Text)"
        p0!!.execSQL(query)
        p0!!.execSQL("CREATE TABLE account (id TEXT,nama_akun TEXT,pub_id TEXT,banned_id TEXT,popup_id Text,video_id Text)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXIST ACCOUNT")
        onCreate(p0)
    }
    fun sqlQuery(querySQL: String) {
        val db = this.writableDatabase
        return db.execSQL(querySQL)
    }
    fun implode(separator:String,items:List<String>):String {
        if (items == null || items.isEmpty())
        {
            return null.toString()
        }
        var delimiter = ""
        val builder = StringBuilder()
        for (item in items)
        {
            builder.append(delimiter).append(item)
            delimiter = separator
        }
        return builder.toString()
    }

    fun sqlInsert(tableName : String,data: Array<Array<String>>): String {
        var listKolom = ArrayList<String>()
        var listValue = ArrayList<String>()
        for (i in 0 until data.size){
            listKolom.add(data[i][0])
            listValue.add("'"+data[i][1]+"'")
        }
        val query = "INSERT INTO "+tableName+" ("+implode(", ",listKolom)+") values ("+implode(", ",listValue)+")"
        return query
    }
    fun sqlUpdate(tableName : String,data: Array<Array<String>>, condition : String): String {
        var setQuery = ArrayList<String>()
        for (i in 0 until data.size){
            setQuery.add(data[i][0] + " = '"+data[i][1]+"'")
        }
        val query = "UPDATE "+tableName+" "+implode(", ",setQuery)+" WHERE "+condition
        return query
    }
    fun sqlDelete(tableName : String, condition : String): String {
        val query = "DELETE FROM "+tableName+" WHERE "+condition
        return query
    }

    companion object {
        private val DATABASENAME = "hopes"
        private val DATABASEVERSION = 1
    }

    fun addAccount(users: Account) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("userID", users.userID)
        values.put("email", users.email)
        values.put("nama_lengkap", users.nama_lengkap)
        values.put("no_telepon", users.no_telepon)
        values.put("saldo", users.saldo)
        values.put("firebase_id", users.firebase_id)
        db.insert("ACCOUNT", null, values)
        db.close()
        Log.v("@@@WWe ", " Record Inserted Sucessfully")
    }

    fun getAccount(): List<Account> {
        val db = this.writableDatabase
        val list = ArrayList<Account>()
        val cusrsor: Cursor
        cusrsor = db.rawQuery("SELECT * FROM ACCOUNT", null)
        if (cusrsor != null) {
            if (cusrsor.count > 0) {
                cusrsor.moveToFirst()
                do {
                    val userID = cusrsor.getInt(cusrsor.getColumnIndex("userID"))
                    val email = cusrsor.getString(cusrsor.getColumnIndex("email"))
                    val nama_lengkap = cusrsor.getString(cusrsor.getColumnIndex("nama_lengkap"))
                    val no_telepon = cusrsor.getString(cusrsor.getColumnIndex("no_telepon"))
                    val saldo = cusrsor.getString(cusrsor.getColumnIndex("saldo"))
                    val firebase_id = cusrsor.getString(cusrsor.getColumnIndex("firebase_id"))
                    val data = Account(userID, email, nama_lengkap, no_telepon,saldo,firebase_id)
                    list.add(data)
                } while (cusrsor.moveToNext())
            }
        }
        return list
    }
    fun sqlRowCount(querySql : String): String {
        val db = this.writableDatabase
        val cusrsor: Cursor
        cusrsor = db.rawQuery(querySql, null)
        if (cusrsor != null) {
        }
        return cusrsor.count.toString()
    }

    fun updateAccount(users: Account) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("userID", users.userID)
        values.put("email", users.email)
        values.put("nama_lengkap", users.nama_lengkap)
        values.put("no_telepon", users.no_telepon)
        values.put("saldo", users.saldo)
        values.put("firebase_id", users.saldo)

        val retVal = db.update("ACCOUNT", values, "userID = " + users.userID, null)
        if (retVal >= 1) {
            Log.v("@@@WWe", " Record updated")
        } else {
            Log.v("@@@WWe", " Not updated")
        }
        db.close()

    }

    fun deleteUser(users: Account) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("userID", users.userID)
        val retVal = db.delete("ACCOUNT", "userID = " + users.userID, null)
        if (retVal >= 1) {
            Log.v("@@@WWe", " Record deleted")
        } else {
            Log.v("@@@WWe", " Not deleted")
        }
        db.close()

    }

    fun updateToken(kolom: FirebaseToken) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("userID", kolom.userID)
        values.put("firebase_id", kolom.firebase_id)

        val retVal = db.update("ACCOUNT", values, "userID = " + kolom.userID, null)
        if (retVal >= 1) {
            Log.v("@@@WWe", " Record updated")
        } else {
            Log.v("@@@WWe", " Not updated")
        }
        db.close()

    }

    fun addHistori(data: Histori) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("id", data.id.toString())
        values.put("nama_tukar", data.nama_tukar)
        values.put("tanggal", data.tanggal)
        values.put("status", data.status)
        values.put("tanggal_aksi", data.tanggal_aksi)
        db.insert("HISTORI_TUKAR", null, values)
        db.close()
        Log.v("@@@WWe ", " Record Inserted Sucessfully")
    }

    fun getHistori(): List<Histori> {
        val db = this.writableDatabase
        val list = ArrayList<Histori>()
        val cusrsor: Cursor
        cusrsor = db.rawQuery("SELECT * FROM HISTORI_TUKAR", null)
        if (cusrsor != null) {
            if (cusrsor.count > 0) {
                cusrsor.moveToFirst()
                do {
                    val id = cusrsor.getString(cusrsor.getColumnIndex("id"))
                    val nama_tukar = cusrsor.getString(cusrsor.getColumnIndex("nama_tukar"))
                    val tanggal = cusrsor.getString(cusrsor.getColumnIndex("tanggal"))
                    val status = cusrsor.getString(cusrsor.getColumnIndex("status"))
                    val tanggal_aksi = cusrsor.getString(cusrsor.getColumnIndex("tanggal_aksi"))
                    val data = Histori(id, nama_tukar, tanggal, status,tanggal_aksi)
                    list.add(data)
                } while (cusrsor.moveToNext())
            }
        }
        return list
    }

    fun getDetailHistori(idSelected : String): List<Histori> {
        val db = this.writableDatabase
        val list = ArrayList<Histori>()
        val cusrsor: Cursor
        cusrsor = db.rawQuery("SELECT * FROM HISTORI_TUKAR WHERE id = '"+ idSelected +"'", null)
        if (cusrsor != null) {
            if (cusrsor.count > 0) {
                cusrsor.moveToFirst()
                do {
                    val id = cusrsor.getString(cusrsor.getColumnIndex("id"))
                    val nama_tukar = cusrsor.getString(cusrsor.getColumnIndex("nama_tukar"))
                    val tanggal = cusrsor.getString(cusrsor.getColumnIndex("tanggal"))
                    val status = cusrsor.getString(cusrsor.getColumnIndex("status"))
                    val tanggal_aksi = cusrsor.getString(cusrsor.getColumnIndex("tanggal_aksi"))
                    val data = Histori(id, nama_tukar, tanggal, status,tanggal_aksi)
                    list.add(data)
                } while (cusrsor.moveToNext())
            }
        }
        return list
    }



    fun destroyApp(){
        val query = "DELETE FROM ACCOUNT"
        val query2 = "DELETE FROM HISTORI_TUKAR"
        val db = this.writableDatabase
        db.execSQL(query)
        db.execSQL(query2)
    }


}