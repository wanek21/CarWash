package ru.carwash.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.carwash.carwash.R
import ru.carwash.dto.User
import ru.carwash.ui.auth.WelcomeActivity
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import kotlin.math.log

class ProfileFragment : Fragment() {

    private var btnEdit: ImageButton? = null
    private var tvName: TextView? = null
    private var tvCity: TextView? = null
    private var tvEmail: TextView? = null
    private var btnChangePass: TextView? = null
    private var btnLogout: TextView? = null

    private var user: User? = null

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
    }

    private val usersInfoObserver = Observer<Resource<User>> {
        when (it.status) {
            Status.SUCCESS -> {
                user = it.data
                fullUserInfo()
            }
            Status.LOADING -> {

            }
            Status.ERROR -> {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnEdit?.setOnClickListener {
            if(user != null) // если инормация о клиенте загрузилась
                Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(R.id.action_profile_to_editProfile)
            else Toast.makeText(requireContext(),"Информация еще не загрузилась",Toast.LENGTH_LONG).show()
        }
        tvName = view.findViewById(R.id.tvName)
        tvCity = view.findViewById(R.id.tvCity)
        tvEmail = view.findViewById(R.id.tvEmail)
        btnChangePass = view.findViewById(R.id.btnChangePass)
        btnLogout = view.findViewById(R.id.btnLogout)
        btnLogout?.setOnClickListener {
            viewModel.logout()
            val loginIntent = Intent(requireActivity(),WelcomeActivity::class.java)
            startActivity(loginIntent)
        }

        viewModel.userInfo.observe(viewLifecycleOwner,usersInfoObserver)
        return view
    }

    private fun fullUserInfo() {
        tvName?.text = "${user?.firstName} ${user?.lastName}"
        tvCity?.text = "${user?.city}"
        tvEmail?.text = "${user?.email}"
    }
}