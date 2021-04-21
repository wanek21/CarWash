package ru.carwash.ui.main.cars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import ru.carwash.dto.Car
import ru.carwash.ui.main.MainActivity
import java.util.*
import javax.inject.Inject

class CarsAdapter constructor(
        private val activity: FragmentActivity,
        private val cars: ArrayList<Car>) : RecyclerView.Adapter<CarsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parent: ConstraintLayout = view.findViewById(R.id.clCarItem)
        val imgCar: ImageView = view.findViewById(R.id.imgCar)
        val tvCarName: TextView = view.findViewById(R.id.tvCarName)
        val imgCarNumber: TextView = view.findViewById(R.id.imgNumber)
        val imgRegion: TextView = view.findViewById(R.id.imgRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.parent.setOnClickListener { // обработчик нажатия на элемент в списке
            val bundleCar = Bundle()
            bundleCar.putParcelable("car", cars[position])
            Navigation.findNavController(activity, R.id.fragment_container).navigate(R.id.carsList_to_viewCar, bundleCar)
        }
        holder.imgCar.setImageResource(R.drawable.ic_car_default)
        holder.tvCarName.text = cars[position].brand + " " + cars[position].model
        holder.imgCarNumber.text = cars[position].carNumber
        holder.imgRegion.text = cars[position].region
        Log.d(MainActivity.TAG, "bind")
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}