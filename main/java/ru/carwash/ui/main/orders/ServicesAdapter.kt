package ru.carwash.ui.main.orders

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import ru.carwash.dto.Service
import kotlin.collections.ArrayList

class ServicesAdapter : RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private var context: Context
    private var services: ArrayList<Service>
    private var isClickable = true
    private var selectedIndexes = ArrayList<Int>()
    private var selectedServices = MutableLiveData<ArrayList<Service>>()

    constructor(activity: Activity, services: ArrayList<Service>, selectedServices: MutableLiveData<ArrayList<Service>>) {
        context = activity
        this.services = services
        this.selectedServices = selectedServices
    }

    constructor(activity: Activity, services: ArrayList<Service>, isClickable: Boolean) {
        context = activity
        this.services = services
        this.isClickable = isClickable
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent: ConstraintLayout = itemView.findViewById(R.id.clMain)
        val tvService: TextView = itemView.findViewById(R.id.tvService)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val line: View = itemView.findViewById(R.id.line)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.service_item, parent, false)
        return ViewHolder(view)
    }

    /*fun getSelectedServices(): ArrayList<Service> {
        val result = ArrayList<Service>()
        for (position in selectedServices) {
            result.add(services[position])
        }
        return result
    }*/
    private fun changeSelectedServices() {
        val result = ArrayList<Service>()
        for (position in selectedIndexes) {
            result.add(services[position])
        }
        selectedServices.value = result
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isClickable) {
            holder.parent.setOnClickListener { v: View ->  // обработчик нажатия на элемент в списке для подсветки выделенной услуги
                if (selectedIndexes.contains(position)) { // убираем подсветку
                    selectedIndexes.removeAt(selectedIndexes.indexOf(position)!!)
                    v.setBackgroundColor(Color.WHITE)
                    holder.line.visibility = View.VISIBLE
                    holder.tvService.setTextColor(context.resources.getColor(R.color.hint))
                } else { // ставим подстветку
                    selectedIndexes.add(position)
                    v.setBackgroundColor(context.resources.getColor(R.color.accent))
                    holder.line.visibility = View.INVISIBLE
                    holder.tvService.setTextColor(Color.WHITE)
                }
                changeSelectedServices()
            }
        }
        holder.tvService.text = services[position].name
        holder.tvPrice.text = services[position].price.toString() + "Р"
    }

    override fun getItemCount(): Int {
        return services.size
    }
}