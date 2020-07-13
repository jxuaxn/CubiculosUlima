package pe.edu.ulima.reservacubiculos.model.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class usuarioDAO{
    fun obtenerReservas(email:String, block:(ArrayList<Reservas>)-> Unit){
        val databaseReference = FirebaseFirestore.getInstance()
        val listaUsuarios = ArrayList<Usuario>()
        val listaReservas = ArrayList<Reservas>()

        var collection = databaseReference?.collection("Usuarios")
        collection!!.get().addOnSuccessListener {
            for(doc in it){
                listaUsuarios.add(Usuario(
                    doc.id,
                    doc["nombre"] as String,
                    doc["reserva"] as String))
            }
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
            }
            block(listaReservas)
        }
    }
}