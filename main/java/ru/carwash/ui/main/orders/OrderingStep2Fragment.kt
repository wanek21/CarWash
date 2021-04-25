package ru.carwash.ui.main.orders

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.carwash.carwash.R
import java.util.*

class OrderingStep2Fragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var currentMinute = 0
    private var currentHour = 0
    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0

    private var savedMinute = 0
    private var savedHour = 0
    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0

    private var tvDate: TextView? = null
    private var tvTime: TextView? = null

    private lateinit var parentFragment: CreateOrderFragment // для досутпа к объекту order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragment = requireParentFragment() as CreateOrderFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ordering_step2, container, false)

        getDateTimeCalendar()
        tvDate = view.findViewById(R.id.tvDateValue)
        tvDate?.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, currentYear, currentMonth, currentDay).show()
        }
        tvTime = view.findViewById(R.id.tvTimeValue)
        tvTime?.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(requireContext(), this, currentHour, currentMinute, true).show()
        }
        setDefaultDateTime()
        return view
    }

    private fun setDefaultDateTime() {
        getDateTimeCalendar()
        val currentDate = "$currentDay.$currentMonth.$currentYear"
        tvDate?.text = currentDate
        val minuteStr = if (currentMinute < 10) {
            "0$currentMinute" // 5 -> 05
        } else currentMinute.toString()
        val currentTime = "${currentHour + 1}:$minuteStr" // по дефолту ставим время на час больше текущего
        tvTime?.text = currentTime
        parentFragment.order.date = currentDate
        parentFragment.order.time = currentTime
    }

    private fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        currentMinute = calendar.get(Calendar.MINUTE)
        currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        getDateTimeCalendar()
        val currentDate = Calendar.getInstance()
        currentDate.set(Calendar.YEAR, currentYear)
        currentDate.set(Calendar.MONTH, currentMonth)
        currentDate.set(Calendar.DAY_OF_MONTH, currentDay)

        val pickedDate = Calendar.getInstance()
        pickedDate.set(Calendar.YEAR, year)
        pickedDate.set(Calendar.MONTH, month)
        pickedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        if (pickedDate >= currentDate) { // проверка на то, что клиент поставил дату НЕ в прошлом
            savedDay = dayOfMonth
            savedMonth = month
            savedYear = year

            parentFragment.order?.date = "$savedDay.$savedMonth.$savedYear"
            tvDate?.text = "$savedDay.$savedMonth.$savedYear"
            getDateTimeCalendar()
        } else {
            Toast.makeText(requireContext(), "Невозможно поставить дату в прошлом", Toast.LENGTH_LONG).show()
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        getDateTimeCalendar()
        val currentTime = Calendar.getInstance()
        currentTime.set(Calendar.YEAR, currentYear)
        currentTime.set(Calendar.MONTH, currentMonth)
        currentTime.set(Calendar.DAY_OF_MONTH, currentDay)
        currentTime.set(Calendar.HOUR_OF_DAY, currentHour)
        currentTime.set(Calendar.MINUTE, currentMinute)

        val pickedTime = Calendar.getInstance()
        pickedTime.set(Calendar.YEAR, if (savedYear == 0) currentYear else savedYear)
        pickedTime.set(Calendar.MONTH, if(savedMonth == 0) currentMonth else savedMonth)
        pickedTime.set(Calendar.DAY_OF_MONTH, if(savedDay == 0) currentDay else savedDay)
        pickedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        pickedTime.set(Calendar.MINUTE, minute)

        if (pickedTime >= currentTime) { // проверка на то, что клиент поставил время НЕ в прошлом
            savedHour = hourOfDay
            savedMinute = minute

            val minuteStr = if (minute < 10) {
                "0$savedMinute" // 5 -> 05
            } else savedMinute.toString()
            tvTime?.text = "${savedHour}:$minuteStr"
            parentFragment.order?.time = "${savedHour}:$minuteStr"
        } else {
            Toast.makeText(requireContext(), "Невозможно поставить время в прошлом", Toast.LENGTH_LONG).show()
        }
    }
}