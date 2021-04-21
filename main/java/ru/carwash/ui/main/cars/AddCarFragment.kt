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
import androidx.navigation.Navigation.findNavController
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.dto.AddEditCar
import ru.carwash.utils.Resource
import ru.carwash.utils.Status

/**
 * A simple [Fragment] subclass.
 * Use the [AddCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AddCarFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var toolbar: Toolbar? = null
    private var btnNext: ImageButton? = null
    private var etBrand: EditText? = null
    private var etModel: EditText? = null
    private var etNumber: EditText? = null
    private var etRegion: EditText? = null
    private var spinnerCategory: Spinner? = null

    private val viewModel: CarsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)
    }


    private val successAdding = Observer<Any> {
        findNavController(requireActivity(), R.id.fragment_container).navigateUp()
    }
    private val addingCarObserver = Observer<Resource<String>> {
        if (it.status == Status.ERROR) {
            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
        Log.d("my","onCreateAddCar")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        btnNext = view.findViewById(R.id.btnNext)
        btnNext?.setOnClickListener {
            val car = AddEditCar(
                    brand = etBrand?.text.toString(),
                    model = etModel?.text.toString(),
                    carNumber = etNumber?.text.toString(),
                    region = etRegion?.text.toString(),
                    category = spinnerCategory?.selectedItem.toString(),

            )
            viewModel.addCar(car)
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
        ).also {adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCategory?.adapter = adapter
        }
        viewModel.addingCarStatus.observe(viewLifecycleOwner,addingCarObserver)
        viewModel.successAddingCar.observe(viewLifecycleOwner,successAdding)
        requireActivity().setActionBar(toolbar)
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): AddCarFragment {
            val fragment = AddCarFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}