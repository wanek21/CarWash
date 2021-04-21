package ru.carwash.ui.main.cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.carwash.carwash.R
import ru.carwash.dto.Car

/**
 * A simple [Fragment] subclass.
 * Use the [CarViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarViewFragment : Fragment {
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var btnBack: ImageButton? = null
    private var btnEditCar: ImageButton? = null
    private var tvCarName: TextView? = null
    private var tvCarNumber: TextView? = null
    private var tvCarRegion: TextView? = null
    private var tvCarCategory: TextView? = null
    private val imgCar: ImageView? = null
    private var car: Car? = null

    constructor() {
        // Required empty public constructor
    }

    constructor(car: Car?) {
        this.car = car
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        car = requireArguments().getParcelable("car")
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_car_view, container, false)
        btnBack = view.findViewById(R.id.btnBack)
        btnEditCar = view.findViewById(R.id.btnEdit)
        tvCarName = view.findViewById(R.id.tvCarName)
        tvCarNumber = view.findViewById(R.id.tvNumber)
        tvCarRegion = view.findViewById(R.id.tvRegion)
        tvCarCategory = view.findViewById(R.id.tvCategory)
        btnBack?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack()
        }
        btnEditCar?.setOnClickListener {
            val bundleCar = Bundle()
            bundleCar.putParcelable("car", car)
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.viewCar_to_editCar, bundleCar)
        }
        tvCarName?.text = car!!.brand + " " + car!!.model
        tvCarNumber?.text = car!!.carNumber
        tvCarRegion?.text = car!!.region
        tvCarCategory?.text = car!!.category
        return view
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): CarViewFragment {
            val fragment = CarViewFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}