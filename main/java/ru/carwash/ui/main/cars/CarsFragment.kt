package ru.carwash.ui.main.cars

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.dto.Car
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [CarsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CarsFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var progressBar: ProgressBar? = null
    private var btnNext: Button? = null
    private var recyclerViewCars: RecyclerView? = null
    private lateinit var carsAdapter: CarsAdapter

    private val viewModel: CarsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)
    }

    var cars: ArrayList<Car> = ArrayList()

    /* recList не будет обновляеться динамически, только инициализироваться,
     поэтому инициализацию решил делать в обсервере */
    private val carsObserver = Observer<Resource<ArrayList<Car>>> {
        Log.d("my","carsObserver")
        if(it.status == Status.SUCCESS) {
            cars = it.data!!
            carsAdapter = CarsAdapter(requireActivity(),cars)
            recyclerViewCars?.adapter = carsAdapter
            progressBar?.visibility = View.INVISIBLE
        } else if (it.status == Status.ERROR) {
            progressBar?.visibility = View.INVISIBLE
            Toast.makeText(context, "Ошибка при получении списка машин", Toast.LENGTH_LONG).show()
        } else if(it.status == Status.LOADING)
            progressBar?.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cars, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        btnNext = view.findViewById(R.id.btnNext)
        recyclerViewCars = view.findViewById(R.id.rvCars)
        recyclerViewCars?.layoutManager = LinearLayoutManager(context)
        btnNext?.setOnClickListener {
            findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.carsList_to_addCar)
        }
        viewModel.cars.observe(viewLifecycleOwner,carsObserver)

        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCars()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): CarsFragment {
            val fragment = CarsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}