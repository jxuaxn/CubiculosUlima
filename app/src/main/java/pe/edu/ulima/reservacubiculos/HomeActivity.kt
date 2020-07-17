package pe.edu.ulima.reservacubiculos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private var mDrawerLayout : DrawerLayout? = null
    private var mNavigationView : NavigationView? = null
    private var mToolbar : Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setup()
        setSupportActionBar(toolbar)

        mDrawerLayout = findViewById(R.id.drawerLayout)
        mNavigationView = findViewById(R.id.navigation)
        mToolbar = findViewById(R.id.toolbar)

        setSupportActionBar(mToolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mNavigationView?.setNavigationItemSelectedListener { mu: MenuItem ->
            when(mu.itemId) {
                R.id.btnVisualizar -> loadFragment("Visualizar")
                R.id.btnReservar -> loadFragment("Reservar")
            }
            mDrawerLayout?.closeDrawers()
            true
        }

        mNavigationView?.setCheckedItem(R.id.btnVisualizar)

        supportFragmentManager.beginTransaction().replace(R.id.flContent, VisualizarFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> mDrawerLayout?.openDrawer(GravityCompat.START)
            R.id.bug -> {
                val homeIntent = Intent(this, BugActivity::class.java)
                startActivityForResult(homeIntent,100)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(type : String) {
        var fragment : Fragment? = null
        if (type == "Visualizar") {
            fragment = VisualizarFragment()
        } else if (type == "Reservar") {
            fragment = ReservarFragment()
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flContent, fragment!!)
        ft.commit()
    }

    private fun setup() {}
}