package pe.edu.ulima.reservacubiculos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_home.*
import pe.edu.ulima.reservacubiculos.adapters.ListadoReservasAdapter
import pe.edu.ulima.reservacubiculos.model.dao.Reservas
import pe.edu.ulima.reservacubiculos.model.dao.usuarioDAO


class HomeActivity : AppCompatActivity() {
    private var mListaReservas : ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle : Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val dao = usuarioDAO()
        setup()
        setSupportActionBar(toolbar)

        Log.i(javaClass.canonicalName, "$email")

        dao.obtenerIdsReservas(email!!) { reservasUsuario ->
            Log.i(javaClass.canonicalName, reservasUsuario.toString())
            dao.obtenerReservas(reservasUsuario) {
                for(doc in it) {
                    mListaReservas = findViewById(R.id.lviReservas)
                    mListaReservas?.adapter = ListadoReservasAdapter(this, it)
                }
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