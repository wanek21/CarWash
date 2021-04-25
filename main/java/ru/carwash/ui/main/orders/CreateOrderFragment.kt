package ru.carwash.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.dto.Order
import ru.carwash.utils.Resource
import ru.carwash.utils.Status

/* Фрагмент для процесса создания\заполнения заказа
*  Представляет из себя Toolbar и ViewPager, в котором свайпаются\переключаются шаги заказа */
@AndroidEntryPoint
class CreateOrderFragment : Fragment() {

    // view's objects
    private var btnBack: ImageButton? = null
    private var tvNext: TextView? = null
    private var tvStep: TextView? = null
    private var viewPager: ViewPager? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null

    val order: Order = Order(Order.ACCEPTED_STATUS)

    private val viewModel: CreateOrderViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CreateOrderViewModel::class.java)
    }

    private val successValidation = Observer<Any> {
        val bundleOrder = Bundle()
        bundleOrder.putParcelable("order", order)
        findNavController().navigate(R.id.action_createOrder_to_acceptNewOrder, bundleOrder)
    }
    private val orderValidityObserver = Observer<Resource<String>> {
        when (it.status) {
            Status.ERROR -> {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private val ORDERING_STEPS_COUNT = 3 // кол-во шагов (фрагментов) при заполнении заказа

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_order, container, false)
        btnBack = view.findViewById(R.id.btnBack)
        btnBack?.setOnClickListener {
            // TODO("back to ordersList screen")
            viewPager!!.currentItem = viewPager!!.currentItem - 1
        }
        tvNext = view.findViewById(R.id.tvNext)
        tvNext?.setOnClickListener {
            if (viewPager!!.currentItem + 1 == ORDERING_STEPS_COUNT)
                viewModel.checkOrderValidity(order)
            viewPager!!.currentItem = viewPager!!.currentItem + 1
        }
        tvStep = view.findViewById(R.id.tvStep)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager?.offscreenPageLimit = 3 // делаем так, чтобы состояние фрагментов сохранялось при свайпах
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPager?.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                var position = position
                tvStep?.text = "Шаг " + ++position + "/3"
            }
        })
        viewPager?.adapter = viewPagerAdapter
        viewModel.orderValidity.observe(viewLifecycleOwner,orderValidityObserver)
        viewModel.successValidatingOrder.observe(viewLifecycleOwner,successValidation)
        return view
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> OrderingStep1Fragment()
                1 -> OrderingStep2Fragment()
                2 -> OrderingStep3Fragment()
                else -> OrderingStep1Fragment()
            }
        }

        override fun getCount(): Int {
            return ORDERING_STEPS_COUNT
        }
    }
}