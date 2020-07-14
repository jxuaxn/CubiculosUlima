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
        var reserva : String? = null
        setup()
        setSupportActionBar(toolbar)


        dao.obtenerUsuarios {
            for (doc in it){
                if (doc.nombre==email){
                    reserva = doc.reserva
                    break
                }
            }
        }
        dao.obtenerReservas {
            for(doc in it){
                if (doc.id == reserva){
                    Log.i(javaClass.canonicalName, "cubiculo ${doc.cubiculo}")
                    Log.i(javaClass.canonicalName, "Fecha ${doc.fecha}")
                    Log.i(javaClass.canonicalName, "hora ${doc.hora}")
                    break
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