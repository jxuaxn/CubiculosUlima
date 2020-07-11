package pe.edu.ulima.reservacubiculos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle : Bundle? = intent.extras
        val email:String? = bundle?.getString("email")

        setup()
    }
    private fun setup(){
        title = "Reserva de Cubículos"
    }
}