package pe.edu.ulima.reservacubiculos

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bug.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.toolbar
import java.lang.Exception

class BugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bug)
        setSupportActionBar(toolbar)
        butSendEmail.setOnClickListener {
            var mensaje = eteMensaje.text.toString().trim()

            sendEmail(mensaje)
        }

    }

    private fun sendEmail(mensaje : String){
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type="text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("20153324@aloe.ulima.edu.pe"))
        mIntent.putExtra(Intent.EXTRA_SUBJECT,"Error CubiculosUlima")
        mIntent.putExtra(Intent.EXTRA_TEXT, mensaje)

        try {
            startActivity(Intent.createChooser(mIntent, "Elige Correo"))
            Toast.makeText(this, "Mensaje Enviado", Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
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
}