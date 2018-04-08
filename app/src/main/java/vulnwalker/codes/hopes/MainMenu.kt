package vulnwalker.codes.hopes

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.app_bar_main_menu.*
import kotlinx.android.synthetic.main.content_main_menu.*
import vulnwalker.codes.hopes.base.config
import vulnwalker.codes.hopes.database.KotlinHelper
import vulnwalker.codes.hopes.fragment.refAccount.dataAccount
import vulnwalker.codes.hopes.fragment.refAccount.dataAccountAdapter
import vulnwalker.codes.hopes.fragment.refAccount.refAccount
import vulnwalker.codes.hopes.R.string.share






class MainMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, refAccount.OnListFragmentInteractionListener {

    private var whereAmI : String = "homePage"
    val configClass = config()
    val databaseHelper = KotlinHelper(this)

    lateinit var menuModified : Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        this.menuModified = menu
        updateMenuItems(this.menuModified)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun updateMenuItems(menu: Menu?) {
        if(menu !=null){
            if (whereAmI == "homePage") {
                menu.findItem(R.id.logoutButton).isVisible = true
                menu.findItem(R.id.addAccountButton).isVisible = false
            } else {
                menu.findItem(R.id.logoutButton).isVisible = true
                menu.findItem(R.id.addAccountButton).isVisible = true
            }
        }
        invalidateOptionsMenu()
    }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.logoutButton -> {
                configClass.alert(this@MainMenu,"Logout Sukses !")
                databaseHelper.sqlQuery(databaseHelper.sqlDelete("member","1=1"))
                val i = Intent(this@MainMenu, MainActivity::class.java)
                startActivity(i)
                finish()
                return true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.accountMenu -> {
                addFragment(refAccount(), "ACCOUNT ADS")

                if(parentMainMenu.visibility == View.VISIBLE){
                    parentMainMenu.visibility = View.GONE
                }
                this.title = "ACCOUNT ADS"
                whereAmI="accountAds"
                fab.visibility = View.GONE
                updateMenuItems(this.menuModified)
            }

            R.id.settingMenu -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addFragment(fragment: Fragment, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.addToBackStack(tag)
        ft.replace(R.id.mainLayout, fragment, tag)
        ft.commitAllowingStateLoss()
    }


//refAccount
    override fun onListFragmentInteraction(item: dataAccount.DummyItem) {
        configClass.alert(this,item.id)
    }

}
