package pe.edu.ulima.reservacubiculos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_reservar.*
import pe.edu.ulima.reservacubiculos.model.dao.Reservas
import pe.edu.ulima.reservacubiculos.model.dao.usuarioDAO
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList


class ReservarFragment : Fragment() {
    private var eteTime : EditText? = null
    private var eteDate : EditText? = null
    private var btnAddCode : Button? = null
    private var codesContainer : LinearLayout? = null
    private var btnSubmit : Button? = null
    private var spinnerPabellones : Spinner? = null
    private val cal : Calendar = Calendar.getInstance()
    private var dia = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.title = getString(R.string.reserve)


        val bundle : Bundle? = activity?.intent?.extras
        val email : String? = bundle?.getString("email")

        eteTime = activity?.findViewById(R.id.eteTime)
        eteDate = activity?.findViewById(R.id.eteDate)
        btnAddCode = activity?.findViewById(R.id.btnAddCode)
        codesContainer = activity?.findViewById(R.id.codesContainer)
        btnSubmit = activity?.findViewById(R.id.btnSubmit)
        spinnerPabellones = activity?.findViewById(R.id.pabellones_spinner)

        eteTime!!.setOnClickListener {
            TimePickerDialog(
                requireActivity(),
                OnTimeSetListener { timePicker, hour, minute ->
                    if(validateTime(hour, minute)) {
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        eteTime?.setText(SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(cal.time))
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true).show()
        }

        eteDate!!.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    if (validateDate(year, month, day)) {
                        dia = day

                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, month)
                        cal.set(Calendar.DAY_OF_MONTH, day)
                        eteDate?.setText(SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(cal.time))
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnAddCode?.setOnClickListener {
            if (codesContainer?.childCount!! <= 2) {
                val eteCode = EditText(requireContext())
                eteCode.hint = getString(R.string.code)
                eteCode.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                codesContainer?.addView(eteCode)
            } else {
                Toast.makeText(requireContext(), "No se pueden agregar más códigos", Toast.LENGTH_SHORT).show()
            }
        }

        btnSubmit?.setOnClickListener {
            val pabellon = spinnerPabellones?.selectedItem as String
            val date = eteDate?.text.toString()
            val time = eteTime?.text.toString()

            if (date.trim().isEmpty() || time.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val reserva = Reservas("0", pabellon, date, time)
                val dao = usuarioDAO()
                dao.crearReserva(reserva) { id : String ->
                    dao.crearUsuarioAReserva(email!!, id) {
                        Toast.makeText(requireContext(), "Reserva creada", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun validateDate(year: Int, month: Int, dayOfMonth: Int) : Boolean {
        if (year != cal.get(Calendar.YEAR) ||
            month != cal.get(Calendar.MONTH) ||
            dayOfMonth < cal.get(Calendar.DAY_OF_MONTH)) {
            Toast.makeText(requireContext(), "Ingrese un fecha correcta", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateTime(hour : Int, minute : Int) : Boolean {
        if (dia == Calendar.DAY_OF_MONTH) {
            if (hour < cal.get(Calendar.HOUR_OF_DAY)) {
                Toast.makeText(requireContext(), "Ingrese una hora correcta", Toast.LENGTH_SHORT).show()
                return false
            }
            else if (minute < cal.get(Calendar.MINUTE)) {
                    Toast.makeText(requireContext(), "Ingrese una hora correcta", Toast.LENGTH_SHORT).show()
                    return false
            }
        }
        return true
    }

}