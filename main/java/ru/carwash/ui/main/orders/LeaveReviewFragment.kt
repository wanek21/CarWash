package ru.carwash.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.carwash.carwash.R
import ru.carwash.dto.Review
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import java.lang.Float.parseFloat
import kotlin.math.ceil

/**
 * A simple [Fragment] subclass.
 * Use the [LeaveReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaveReviewFragment : Fragment() {

    private var ratingBar: RatingBar? = null
    private var imgSpeed: ImageView? = null
    private var imgCost: ImageView? = null
    private var imgService: ImageView? = null
    private var imgCleanliness: ImageView? = null
    private var etMessage: EditText? = null
    private var progressBar: ProgressBar? = null
    private var btnSendReview: Button? = null

    private val SPEED_ITEM = "speed"
    private val SERVICE_ITEM = "service"
    private val COST_ITEM = "cost"
    private val CLEAN_ITEM = "clean"
    private var selectedItems: ArrayList<String> = ArrayList(4)

    private val viewModel: OrdersViewModel by lazy {
        ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)
    }

    private val successSendingReview = Observer<Any> {
        findNavController().navigate(R.id.action_leaveReview_to_ordersList)
    }
    private val sendingStatusObserver = Observer<Resource<String>> {
        when (it.status) {
            Status.SUCCESS -> {
                progressBar?.visibility = View.GONE
            }
            Status.LOADING -> {
                progressBar?.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                progressBar?.visibility = View.INVISIBLE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_leave_review, container, false)

        ratingBar = view.findViewById(R.id.ratingBar)
        ratingBar?.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = if(rating < 0.5F) 0.5F else rating
        }
        imgSpeed = view.findViewById(R.id.imgSpeed)
        imgSpeed?.setOnClickListener {
            clickItem(SPEED_ITEM,it)
        }
        imgCost = view.findViewById(R.id.imgCost)
        imgCost?.setOnClickListener {
            clickItem(COST_ITEM,it)
        }
        imgService = view.findViewById(R.id.imgService)
        imgService?.setOnClickListener {
            clickItem(SERVICE_ITEM,it)
        }
        imgCleanliness = view.findViewById(R.id.imgCleanliness)
        imgCleanliness?.setOnClickListener {
            clickItem(CLEAN_ITEM,it)
        }

        etMessage = view.findViewById(R.id.etComment)
        progressBar = view.findViewById(R.id.progressBar3)
        btnSendReview = view.findViewById(R.id.btnLeaveReview)
        btnSendReview?.setOnClickListener {
            val comment = etMessage?.text.toString()
            val score = ratingBar?.rating

            // TODO("add selected items")
            val review = Review(comment,score)
            viewModel.sendReview(review)
        }
        viewModel.sendingReviewStatus.observe(viewLifecycleOwner,sendingStatusObserver)
        viewModel.successSendingReview.observe(viewLifecycleOwner,successSendingReview)

        return view
    }

    private fun clickItem(item: String, view: View) {
        if(selectedItems.contains(item)) {
            selectedItems.remove(item)
            view.isSelected = false
        } else {
            view.isSelected = true
            selectedItems.add(item)
        }
    }

    companion object {
        fun newInstance(): LeaveReviewFragment {
            return LeaveReviewFragment()
        }
    }
}