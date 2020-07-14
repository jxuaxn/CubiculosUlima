package pe.edu.ulima.reservacubiculos.model.dao

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class usuarioDAO {
    fun obtenerIdsReservas(email : String, block:(ArrayList<String>)-> Unit) {
        val databaseReference = FirebaseFirestore.getInstance()
        val listaIdsReservas = ArrayList<String>()

        val collection = databaseReference.collection("Usuarios")
        collection.whereEqualTo("nombre", email).get().addOnSuccessListener {
            for(doc in it) {
                listaIdsReservas.add(doc["reserva"] as String)
            }
            block(listaIdsReservas)
        }
    }

    fun obtenerReservas(listaIdsReservas : ArrayList<String>, block:(List<Reservas>) -> Unit){
        val databaseReference = FirebaseFirestore.getInstance()
        val listaReservas = ArrayList<Reservas>()
        val collection = databaseReference.collection("Reservas")
        collection.whereIn(FieldPath.documentId(), listaIdsReservas).get().addOnSuccessListener {
            for (doc in it) {
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