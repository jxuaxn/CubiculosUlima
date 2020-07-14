package pe.edu.ulima.reservacubiculos.model.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class usuarioDAO{
    fun obtenerUsuarios(block:(List<Usuario>)-> Unit){
        val databaseReference = FirebaseFirestore.getInstance()
        val listaUsuarios = ArrayList<Usuario>()


        var collection = databaseReference?.collection("Usuarios")
        collection!!.get().addOnSuccessListener {
            for(doc in it){
                listaUsuarios.add(Usuario(
                    doc.id,
                    doc["nombre"] as String,
                    doc["reserva"] as String))
            }
            block(listaUsuarios)
        }
    }

    fun obtenerReservas(block:(List<Reservas>)->Unit){
        val databaseReference = FirebaseFirestore.getInstance()
        val listaReservas = ArrayList<Reservas>()
        var collection = databaseReference?.collection("Reservas")
        collection!!.get().addOnSuccessListener {
            for (doc in it){
                listaReservas.add(Reservas(
                    doc.id,
                    doc["cubiculo"] as String,
                    doc["fecha"] as String,
                    doc["hora"] as String)
                )
            }
            block(listaReservas)
        }
    }
    /*val listaReservas = ArrayList<Reservas>()
    for (doc in listaUsuarios){
        if (doc.nombre == email ){
            var reserva = doc.reserva
            collection=databaseReference?.collection("Reservas")
            collection!!
                .get().addOnSuccessListener {
                    for (doc in it){
                        if (doc.id == reserva)
                            listaReservas.add(Reservas(
                                doc.id,
                                doc["cubiculo"] as String,
                                doc["fecha"] as String,
                                doc["hora"] as String))
                    }
                }
        }
    }*/
}