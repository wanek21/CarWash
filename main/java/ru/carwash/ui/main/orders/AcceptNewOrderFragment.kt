package ru.carwash.ui.main.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
import ru.carwash.dto.Order
import ru.carwash.dto.Service
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import java.util.*

@AndroidEntryPoint
class AcceptNewOrderFragment : Fragment() {

    private var btnBack: ImageButton? = null
    private var tvCarWash: TextView? = null
    private var tvCarWashAddress: TextView? = null
    private var tvCarName: TextView? = null
    private var tvCarNumber: TextView? = null
    private var tvCarRegion: TextView? = null
    private var tvDate: TextView? = null
    private var tvTime: TextView? = null
    private var tvPrice: TextView? = null
    private var rvServices: RecyclerView? = null
    private var btnToOrder: Button? = null
    private var progressBar: ProgressBar? = null

    private var order: Order? = null

    private val viewModel: CreateOrderViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CreateOrderViewModel::class.java)
    }

    private val successCreating = Observer<Any> {
        Log.d("my","navigate up to list order")
        Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.action_acceptNewOrder_to_ordersList)
    }

    private val orderCreatingObserver = Observer<Resource<String>> {
        when(it.status) {
            Status.LOADING -> {
                progressBar?.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                btnToOrder?.isClickable = true
                progressBar?.visibility = View.INVISIBLE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        order = requireArguments().getParcelable("order")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_accept_new_order, container, false)

        btnBack = view.findViewById(R.id.btnBack)
        btnBack?.setOnClickListener {
            findNavController().popBackStack(R.id.createOrder,false)
        }
        tvCarWash = view.findViewById(R.id.tvCarWash)
        tvCarWashAddress = view.findViewById(R.id.tvAddress)
        tvCarName = view.findViewById(R.id.tvCarName)
        tvCarNumber = view.findViewById(R.id.imgNumber)
        tvCarRegion = view.findViewById(R.id.imgRegion)
        tvDate = view.findViewById(R.id.tvDateValue)
        tvTime = view.findViewById(R.id.tvTimeValue)
        tvPrice = view.findViewById(R.id.tvCost)
        btnToOrder = view.findViewById(R.id.btnToOrder)
        progressBar = view.findViewById(R.id.progressBar2)
        btnToOrder?.setOnClickListener {
            it.isClickable = false
            viewModel.createOrder(order)
        }
        rvServices = view.findViewById(R.id.rvServices)

        //order = viewModel.order.value
        fullOrderInfo()

        viewModel.creatingOrderComplete.observe(viewLifecycleOwner,orderCreatingObserver)
        viewModel.successCreatingOrder.observe(viewLifecycleOwner,successCreating)
        return view
    }

    private fun fullOrderInfo() {
        tvCarWash?.text = order?.carWash?.name
        tvCarWashAddress?.text = order?.carWash?.address
        tvCarName?.text = order?.car?.brand + " " + order?.car?.model
        tvCarNumber?.text = order?.car?.carNumber
        tvCarRegion?.text = order?.car?.region
        tvDate?.text = order?.date
        tvTime?.text = order?.time
        tvPrice?.text = order?.price.toString() + "Р"

        rvServices?.layoutManager = LinearLayoutManager(context)
        val servicesAdapter = ServicesAdapter(requireActivity(), order?.services!!, false)
        rvServices?.adapter = servicesAdapter
    }
}