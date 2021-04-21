package ru.carwash.ui.main.cars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.carwash.carwash.R
import ru.carwash.dto.AddEditCar
import ru.carwash.dto.Car
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import ru.carwash.utils.setSpinnerText

/**
 * A simple [Fragment] subclass.
 * Use the [EditCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditCarFragment : Fragment {

    private var mParam1: String? = null
    private var mParam2: String? = null

    // view's objects
    private var btnBack: ImageButton? = null
    private var btnSaveCar: Button? = null
    private var etBrand: EditText? = null
    private var etModel: EditText? = null
    private var etNumber: EditText? = null
    private var etRegion: EditText? = null
    private var spinnerCategory: Spinner? = null

    private var car: Car? = null

    private val viewModel: CarsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)
    }
    private val editingStatus = Observer<Resource<String>> {
        if (it.status == Status.ERROR)
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
    }
    private val successEditing = Observer<Any> {
        findNavController().navigateUp()
    }

    constructor() {
        // Required empty public constructor
    }

    constructor(car: Car?) {
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
        val view = inflater.inflate(R.layout.fragment_edit_car, container, false)
        btnBack = view.findViewById(R.id.btnBack)
        btnBack?.setOnClickListener {
            findNavController().navigateUp()
        }
        btnSaveCar = view.findViewById(R.id.btnSave)
        btnSaveCar?.setOnClickListener {
            val car = AddEditCar(
                    brand = etBrand?.text.toString(),
                    model = etModel?.text.toString(),
                    carNumber = etNumber?.text.toString(),
                    region = etRegion?.text.toString(),
                    category = spinnerCategory?.selectedItem.toString(),

                    )
            viewModel.editCar(car)
        }

        etBrand = view.findViewById(R.id.etBrand)
        etModel = view.findViewById(R.id.etModel)
        etNumber = view.findViewById(R.id.etAutoNumber)
        etRegion = view.findViewById(R.id.etRegion)
        spinnerCategory = view.findViewById(R.id.etCategory)
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories_array,
                R.layout.category_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCategory?.adapter = adapter
        }

        viewModel.editingCarStatus.observe(viewLifecycleOwner, editingStatus)
        viewModel.successEditingCar.observe(viewLifecycleOwner, successEditing)
        fillCarInfo()
        return view
    }

    private fun fillCarInfo() {
        etBrand?.setText(car?.brand)
        etModel?.setText(car?.model)
        etNumber?.setText(car?.carNumber)
        etRegion?.setText(car?.region)
        spinnerCategory?.setSpinnerText(car?.category.toString())
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): EditCarFragment {
            val fragment = EditCarFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}