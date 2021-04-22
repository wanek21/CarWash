package ru.carwash.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwash.carwash.R
import kotlinx.android.synthetic.main.fragment_car_view.*
import ru.carwash.dto.Order
import ru.carwash.dto.Service
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ViewOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewOrderFragment : Fragment {

    private var mParam1: String? = null
    private var mParam2: String? = null

    // view's objects
    private var btnBack: ImageButton? = null
    private var btnEdit: ImageButton? = null
    private var rvServices: RecyclerView? = null
    private var btnLeaveReview: Button? = null
    private var tvCarWashName: TextView? = null
    private var tvCarWashAddress: TextView? = null
    private var tvStatus: TextView? = null
    private var tvStatusInfo: TextView? = null
    private var tvCarName: TextView? = null
    private var tvCarNumber: TextView? = null
    private var tvCarRegion: TextView? = null
    private var tvDate: TextView? = null
    private var tvTime: TextView? = null
    private var tvPrice: TextView? = null
    private var imgStatus: ImageView? = null

    constructor() {
        // Required empty public constructor
    }

    private var order: Order? = null

    constructor(order: Order?) {
        this.order = order
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        order = requireArguments().getParcelable("order")
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_order, container, false)
        btnBack = view.findViewById(R.id.btnBack)
        btnBack?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.action_viewOrder_to_orderList)
        }
        btnEdit = view.findViewById(R.id.btnEdit)
        tvCarWashName = view.findViewById(R.id.tvCarWash)
        tvCarWashAddress = view.findViewById(R.id.tvAddress)
        imgStatus = view.findViewById(R.id.imgStatus)
        tvStatus = view.findViewById(R.id.tvStatusValue)
        tvStatusInfo = view.findViewById(R.id.tvStatusInfo)
        tvCarName = view.findViewById(R.id.tvCarName)
        tvCarNumber = view.findViewById(R.id.imgNumber)
        tvCarRegion = view.findViewById(R.id.imgRegion)
        tvDate = view.findViewById(R.id.tvDateValue)
        tvTime = view.findViewById(R.id.tvTimeValue)
        tvPrice = view.findViewById(R.id.tvCost)


        btnLeaveReview = view.findViewById(R.id.btnLeaveReview)
        btnLeaveReview?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.action_viewOrder_to_leaveReview)
        }
        rvServices = view.findViewById(R.id.rvServices)

        rvServices?.layoutManager = LinearLayoutManager(context)
        val servicesAdapter = ServicesAdapter(activity, order!!.services, false)
        rvServices?.adapter = servicesAdapter

        fullOrderInfo()

        return view
    }

    private fun fullOrderInfo() {
        // инициализируем некотрые view должным образом для разных статусов заказа
        when(order!!.status) {
            Order.CANCELED_STATUS -> {
                btnEdit?.visibility = View.INVISIBLE
                btnLeaveReview?.visibility = View.INVISIBLE
                imgStatus?.setImageResource(R.drawable.ic_canceled)
                tvStatus?.setText(R.string.canceled_status)
                tvStatusInfo?.setText(R.string.canceled_status_info)
            }
            Order.ACCEPTED_STATUS -> {
                btnLeaveReview?.visibility = View.INVISIBLE
                imgStatus?.setImageResource(R.drawable.ic_perfoming)
                tvStatus?.setText(R.string.accepted_status)
                tvStatusInfo?.setText(R.string.accepted_status_info)
            }
            Order.COMPLETED_STATUS -> {
                btnEdit?.visibility = View.INVISIBLE
                imgStatus?.setImageResource(R.drawable.ic_completed)
                tvStatus?.setText(R.string.completed_status)
                tvStatusInfo?.setText(R.string.complited_status_info)
            }
        }

        tvCarName?.text = order?.carWash?.name
        tvCarWashAddress?.text = order?.carWash?.address
        tvCarName?.text = order?.car?.brand + " " + order?.car?.model
        tvCarNumber?.text = order?.car?.carNumber
        tvCarRegion?.text = order?.car?.region
        tvDate?.text = order?.date
        tvTime?.text = order?.time
        tvPrice?.text = order?.price.toString() + "Р"
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewOrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): ViewOrderFragment {
            val fragment = ViewOrderFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}