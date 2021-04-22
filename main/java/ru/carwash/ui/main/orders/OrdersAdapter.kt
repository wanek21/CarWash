package ru.carwash.ui.main.orders

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import com.carwash.carwash.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import ru.carwash.dto.Order
import java.util.ArrayList

class OrdersAdapter(private val activity: FragmentActivity,
                    private val orders: ArrayList<Order>)
    : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parent: ConstraintLayout = view.findViewById(R.id.clOrderItem)
        val tvStatus: TextView = view.findViewById(R.id.tvStatusValue)
        val tvCarWash: TextView = view.findViewById(R.id.tvCarWash)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.parent.setOnClickListener {
            val bundleOrder = Bundle()
            bundleOrder.putParcelable("order", orders[position])
            findNavController(activity, R.id.fragment_container).navigate(R.id.action_ordersList_to_viewOrder, bundleOrder)
        }

        when (orders[position].status) {
            Order.ACCEPTED_STATUS -> {
                holder.tvStatus.setText(R.string.accepted_status) // ставим текст "Принят"
                holder.tvStatus.setTextColor(activity.resources.getColor(R.color.accepted_status)) // задаем соответствующий цвет текста
            }
            Order.COMPLETED_STATUS -> {
                holder.tvStatus.setText(R.string.completed_status)
                holder.tvStatus.setTextColor(activity.resources.getColor(R.color.completed_status))
            }
            Order.CANCELED_STATUS -> {
                holder.tvStatus.setText(R.string.canceled_status)
                holder.tvStatus.setTextColor(activity.resources.getColor(R.color.canceled_status))
            }
        }
        holder.tvCarWash.text = orders[position].carWash?.name
        holder.tvDate.text = orders[position].date.toString()
        holder.tvTime.text = orders[position].time.toString()
        holder.tvPrice.text = orders[position].price.toString() + "Р"
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}