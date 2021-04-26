package ru.carwash.ui.main.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import ru.carwash.dto.Service
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import java.util.*
import kotlin.collections.ArrayList

class OrderingStep3Fragment : Fragment() {

    private var rvServices: RecyclerView? = null
    private var tvServicesLoadingStatus: TextView? = null

    private var services: ArrayList<Service> = ArrayList()
    private var selectedServices: MutableLiveData<ArrayList<Service>> = MutableLiveData()

    private lateinit var parentFragment: CreateOrderFragment // для досутпа к объекту order

    private val viewModel: CreateOrderViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CreateOrderViewModel::class.java)
    }

    private val selectedServicesObserver = Observer<ArrayList<Service>> {
        parentFragment.order.services = it
        parentFragment.order.price = it.fold(0.0) { acc: Double, service -> acc + service.price } // складываем цены за каждую выбранную услугу
    }
    private val servicesObserver = Observer<Resource<ArrayList<Service>>> {
        when (it.status) {
            Status.SUCCESS -> {
                services = it.data!!
                val servicesAdapter = ServicesAdapter(requireActivity(), services, selectedServices)
                rvServices?.adapter = servicesAdapter
                tvServicesLoadingStatus?.visibility = View.GONE
            }
            Status.LOADING -> {
                tvServicesLoadingStatus?.visibility = View.VISIBLE
                tvServicesLoadingStatus?.text = "Загрузка доступных услуг..."
            }
            Status.ERROR -> {
                tvServicesLoadingStatus?.visibility = View.VISIBLE
                tvServicesLoadingStatus?.text = "Ошибка при загрузке доступных услуг. Нажмите, чтобы попробовать снова"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragment = requireParentFragment() as CreateOrderFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ordering_step3, container, false)
        rvServices = view.findViewById(R.id.rvServices)
        //rvServices?.isNestedScrollingEnabled = true
        tvServicesLoadingStatus = view.findViewById(R.id.tvServicesLoadingStatus)
        tvServicesLoadingStatus?.setOnClickListener {
            viewModel.getAvailableServices()
        }
        rvServices?.layoutManager = LinearLayoutManager(context)

        viewModel.services.observe(viewLifecycleOwner,servicesObserver)
        selectedServices.observe(viewLifecycleOwner,selectedServicesObserver)
        return view
    }
}