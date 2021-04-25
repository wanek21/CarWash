package ru.carwash.ui.main.orders

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import ru.carwash.dto.Car
import java.util.*

/* Адаптер для RecycleView списка машин, который находится во фрагменте создания заказа */
class CarsInOrderAdapter(
        activity: FragmentActivity,
        cars: ArrayList<Car>,
        selectedCar: MutableLiveData<Car>) : RecyclerView.Adapter<CarsInOrderAdapter.ViewHolder>() {

    private val context // для доступа к FragmentManager
            : Context
    private val cars: ArrayList<Car>
    private var selectedCarIndex = RecyclerView.NO_POSITION
    private val selectedCar by lazy {
        selectedCar
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parent: ConstraintLayout = view.findViewById(R.id.clCarItem)
        val tvCarName: TextView = view.findViewById(R.id.tvCarName)
        val imgCarNumber: TextView = view.findViewById(R.id.imgNumber)
        val imgRegion: TextView = view.findViewById(R.id.imgRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.car_in_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.isSelected = selectedCarIndex == position

        // изменяем цвет текста названия машины, если машина подсвечена/не подсвечена
        if (selectedCarIndex == position) holder.tvCarName.setTextColor(Color.WHITE) else holder.tvCarName.setTextColor(Color.BLACK)
        holder.parent.setOnClickListener { v: View? ->  // обработчик нажатия на элемент в списке для подсветки выделенного авто
            notifyItemChanged(selectedCarIndex)
            selectedCarIndex = holder.layoutPosition
            notifyItemChanged(selectedCarIndex)
            selectedCar.value = cars[selectedCarIndex]
        }
        holder.tvCarName.text = cars[position].brand + " " + cars[position].model
        holder.imgCarNumber.text = cars[position].carNumber
        holder.imgRegion.text = cars[position].region
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    init {
        context = activity
        this.cars = cars
    }
}