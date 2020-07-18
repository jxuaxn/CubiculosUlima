package pe.edu.ulima.reservacubiculos.model.dao

data class Reservas(val id:String?, val cubiculo:String, val fecha:String, val hora:String)
data class Usuario(val id:String, val nombre:String, val reserva:String)