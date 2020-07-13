package pe.edu.ulima.reservacubiculos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_home.*
import pe.edu.ulima.reservacubiculos.model.dao.Reservas
import pe.edu.ulima.reservacubiculos.model.dao.usuarioDAO


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle : Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val dao = usuarioDAO()
        setup()
        setSupportActionBar(toolbar)

        if (email != null) {
            dao.obtenerReservas(email){
                //Log.i(javaClass.canonicalName,"cubiculo: ${it[0].cubiculo}")
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.bug){
            val homeIntent = Intent(this, BugActivity::class.java).apply {
            }
            startActivity(homeIntent)
        }
        return false
    }
    private fun setup(){

    }
}