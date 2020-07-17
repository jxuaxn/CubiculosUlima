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
                    else Toast.makeText(requireContext(), "Ingrese un hora correcta", Toast.LENGTH_SHORT).show()
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
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, month)
                        cal.set(Calendar.DAY_OF_MONTH, day)
                        eteDate?.setText(SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(cal.time))
                    }
                    else Toast.makeText(requireContext(), "Ingrese una fecha correcta", Toast.LENGTH_SHORT).show()
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
            val pabellon = spinnerPabellones?.selectedItem
            val codes = ArrayList<String>()
            for (i in 0..codesContainer!!.childCount) {
                val v = codesContainer?.getChildAt(i) as EditText
                codes.add(v.text.toString())
            }
            val date = eteDate?.text
            val time = eteTime?.text
        }
    }

    private fun validateDate(year: Int, month: Int, dayOfMonth: Int) : Boolean {
        if (year != cal.get(Calendar.YEAR) ||
            month != cal.get(Calendar.MONTH) ||
            dayOfMonth < cal.get(Calendar.DAY_OF_MONTH)) {
            return false
        }
        return true
    }

    private fun validateTime(hour : Int, minute : Int) : Boolean {
        if (eteDate?.text!!.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese una fecha primero", Toast.LENGTH_SHORT).show()
        }
        if (hour < cal.get(Calendar.HOUR_OF_DAY) || minute != 0) {
            return false
        }
        return true
    }

}