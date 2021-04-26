package ru.carwash.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import ru.carwash.dto.Car
import ru.carwash.dto.CarWash
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import java.util.*

class OrderingStep1Fragment : Fragment() {

    // TODO("add choice of car wash")

    private var carWashGroup: Group? = null
    private var recyclerViewCars: RecyclerView? = null

    private var tvCarsWashesLoadingStatus: TextView? = null
    private var tvClientsCarsLoadingStatus: TextView? = null
    private lateinit var clientsCars: ArrayList<Car>
    private val selectedCar: MutableLiveData<Car> = MutableLiveData()

    private lateinit var parentFragment: CreateOrderFragment

    private val viewModel: CreateOrderViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CreateOrderViewModel::class.java)
    }

    private val carWashesObserver = Observer<Resource<ArrayList<CarWash>>> {
        when(it.status) {
            Status.SUCCESS -> {
                carWashGroup?.visibility = View.VISIBLE
                tvCarsWashesLoadingStatus?.visibility = View.GONE
            }
            Status.LOADING -> {
                carWashGroup?.visibility = View.INVISIBLE
                tvCarsWashesLoadingStatus?.visibility = View.VISIBLE
                tvCarsWashesLoadingStatus?.text = "Загрузка автомоек..."
            }
            Status.ERROR -> {
                carWashGroup?.visibility = View.INVISIBLE
                tvCarsWashesLoadingStatus?.visibility = View.VISIBLE
                tvCarsWashesLoadingStatus?.text = "Ошибка при загрузке автомоек. Нажмите сюда, чтобы загрузить снова"
            }
        }
    }
    private val clientsCarsObserver = Observer<Resource<ArrayList<Car>>> {
        when(it.status) {
            Status.SUCCESS -> {
                clientsCars = it.data!!
                val carsInOrderAdapter = CarsInOrderAdapter(requireActivity(), clientsCars, selectedCar)
                recyclerViewCars?.adapter = carsInOrderAdapter
                tvClientsCarsLoadingStatus?.visibility = View.GONE
            }
            Status.LOADING -> {
                tvClientsCarsLoadingStatus?.visibility = View.VISIBLE
                tvClientsCarsLoadingStatus?.text = "Загрузка ваших машин..."
            }
            Status.ERROR -> {
                tvClientsCarsLoadingStatus?.visibility = View.VISIBLE
                tvClientsCarsLoadingStatus?.text = "Ошибка при загрузке авто. Нажмите сюда, чтобы загрузить снова"
            }
        }
    }
    private val selectedCarObserver = Observer<Car> {
        parentFragment.order.car = it
        parentFragment.order.carWash = CarWash("Автомойка \"МИР\"","Лейтенанта Шмидта, 1") // TODO
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragment = requireParentFragment() as CreateOrderFragment
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ordering_step1, container, false)

        carWashGroup = view.findViewById(R.id.carWashGroup)
        tvCarsWashesLoadingStatus = view.findViewById(R.id.tvCarWashesLoadingStatus)
        tvCarsWashesLoadingStatus?.setOnClickListener {
            viewModel.getAvailableCarWashes()
        }
        tvClientsCarsLoadingStatus = view.findViewById(R.id.tvCarsLoadingStatus)
        tvClientsCarsLoadingStatus?.setOnClickListener {
            viewModel.getClientsCars()
        }
        recyclerViewCars = view.findViewById(R.id.rvCars)
        recyclerViewCars?.layoutManager = LinearLayoutManager(context)

        viewModel.carWashes.observe(viewLifecycleOwner,carWashesObserver)
        viewModel.clientsCars.observe(viewLifecycleOwner,clientsCarsObserver)
        selectedCar.observe(viewLifecycleOwner,selectedCarObserver)
        return view
    }
}