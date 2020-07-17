package pe.edu.ulima.reservacubiculos

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ListView
import androidx.fragment.app.Fragment
import pe.edu.ulima.reservacubiculos.adapters.ListadoReservasAdapter
import pe.edu.ulima.reservacubiculos.model.dao.usuarioDAO

class VisualizarFragment : Fragment() {
    private var mListaReservas : ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_visualizar, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = getString(R.string.view)

        val bundle : Bundle? = activity?.intent?.extras
        val email:String? = bundle?.getString("email")

        val dao = usuarioDAO()
        dao.obtenerIdsReservas(email!!) { reservasUsuario ->
            Log.i(javaClass.canonicalName, reservasUsuario.toString())
            dao.obtenerReservas(reservasUsuario) {
                for(doc in it) {
                    mListaReservas = activity?.findViewById(R.id.lviReservas)
                    mListaReservas?.adapter = ListadoReservasAdapter(activity!!.applicationContext, it)
                }
            }
        }
    }
}