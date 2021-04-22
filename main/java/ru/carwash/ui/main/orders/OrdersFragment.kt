package ru.carwash.ui.main.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.dto.Car
import ru.carwash.dto.Order
import ru.carwash.ui.main.cars.CarsAdapter
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import kotlin.collections.ArrayList

/* Фрагмент, содержащий список заказов и кнопку для создания нового заказа */
@AndroidEntryPoint
class OrdersFragment : Fragment() {

    // view's objects
    private var recyclerViewOrders: RecyclerView? = null
    private var btnNewOrder: Button? = null
    private var progressBar: ProgressBar? = null
    private var topLine: View? = null

    private lateinit var ordersAdapter: OrdersAdapter

    private var orders: ArrayList<Order> = ArrayList()

    private val viewModel: OrdersViewModel by lazy {
        ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)
    }

    /* recList очень редко обновляется,
     поэтому его инициализацию решил делать в обсервере */
    private val ordersObserver = Observer<Resource<ArrayList<Order>>> {
        Log.d("my","ordersObserver")
        when (it.status) {
            Status.SUCCESS -> {
                orders = it.data!!
                ordersAdapter = OrdersAdapter(requireActivity(),orders)
                recyclerViewOrders?.adapter = ordersAdapter
                progressBar?.visibility = View.INVISIBLE
                topLine?.visibility = View.INVISIBLE
                Log.d("my","orders size: ${orders.size}")
            }
            Status.ERROR -> {
                progressBar?.visibility = View.INVISIBLE
                Toast.makeText(context,"Ошибка при получении списка заказов", Toast.LENGTH_LONG).show()
            }
            Status.LOADING -> progressBar?.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        topLine = view.findViewById(R.id.view8)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerViewOrders = view.findViewById(R.id.rvOrders)
        btnNewOrder = view.findViewById(R.id.btnNewOrder)
        btnNewOrder?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.action_ordersList_to_createOrder)
        }
        recyclerViewOrders?.layoutManager = LinearLayoutManager(activity)

        viewModel.orders.observe(viewLifecycleOwner,ordersObserver)
        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.getOrders()
    }
}