package pe.edu.ulima.reservacubiculos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.edu.ulima.reservacubiculos.R
import pe.edu.ulima.reservacubiculos.model.dao.Reservas

class ListadoReservasAdapter : BaseAdapter {
    var reservas : ArrayList<Reservas>? = null
    var inflater :LayoutInflater? = null

    constructor(context : Context, lista : List<Reservas>) {
        reservas = lista as ArrayList<Reservas>
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = null
        var viewHolder : ViewHolder? = null

        if (convertView == null) {
            view = inflater!!.inflate(R.layout.item_reserva, null)

            viewHolder = ViewHolder()

            viewHolder.tviFechaReserva = view.findViewById<TextView>(R.id.tviFechaReserva)
            viewHolder.tviHoraReserva = view.findViewById<TextView>(R.id.tviHoraReserva)
            viewHolder.tviNumeroCubiculo = view.findViewById<TextView>(R.id.tviNumeroCubiculo)

            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val reserva = reservas!![position]
        viewHolder.tviFechaReserva!!.text = "Reserva ${reserva.fecha}"
        viewHolder.tviHoraReserva!!.text = reserva.hora
        viewHolder.tviNumeroCubiculo!!.text = "Pabellón ${reserva.cubiculo}"

        return view!!
    }

    override fun getItem(position: Int): Any {
        return reservas!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reservas!!.size
    }

    class ViewHolder {
        var tviFechaReserva : TextView? = null
        var tviNumeroCubiculo : TextView? = null
        var tviHoraReserva : TextView? = null
    }

}