package ru.carwash.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.carwash.carwash.R
import ru.carwash.dto.User
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {

    private var btnBack: TextView? = null
    private var btnSave: TextView? = null
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etCity: EditText? = null
    private var etEmail: EditText? = null
    private var etPhone: EditText? = null

    private var user: User? = null

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
    }

    private val successEditingUser = Observer<Any> {
        findNavController().navigate(R.id.action_editProfile_to_profile)
    }
    private val usersInfoObserver = Observer<Resource<User>> {
        when (it.status) {
            Status.SUCCESS -> {
                user = it.data
                fullUserInfo()
                btnSave?.isClickable = true
            }
            Status.LOADING -> {
                btnSave?.isClickable = false
            }
            Status.ERROR -> {
                btnSave?.isClickable = true
                Toast.makeText(requireContext(),"Информация еще не загружена",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        btnBack = view.findViewById(R.id.tvCancel)
        btnBack?.setOnClickListener {
            findNavController().popBackStack()
        }
        btnSave = view.findViewById(R.id.tvSave)
        btnSave?.setOnClickListener {
            val user = User(firstName = etFirstName?.text.toString(),
                    lastName = etLastName?.text.toString(),
                    city = etCity?.text.toString(),
                    phone = etPhone?.text.toString(),
                    email = etEmail?.text.toString()
            )
            btnSave?.isClickable = false
            viewModel.editUser(user)
        }
        etFirstName = view.findViewById(R.id.etFirstName)
        etLastName = view.findViewById(R.id.etLastName)
        etCity = view.findViewById(R.id.etCity)
        etEmail = view.findViewById(R.id.etEmail)
        etPhone = view.findViewById(R.id.etNumber)
        setMaskToEditText()

        viewModel.userInfo.observe(viewLifecycleOwner,usersInfoObserver)
        viewModel.successEditingUser.observe(viewLifecycleOwner,successEditingUser)

        return view
    }

    private fun fullUserInfo() {
        etFirstName?.setText(user?.firstName)
        etLastName?.setText(user?.lastName)
        etCity?.setText(user?.city)
        etEmail?.setText(user?.email)
        etPhone?.setText(user?.phone)
        etPhone?.setText("95033984598")
    }

    private fun setMaskToEditText() {
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(etPhone!!)
        //etPhone.setText(" "); // костылек, чтобы отображался регион (+7)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(): EditProfileFragment {
            return EditProfileFragment()
        }
    }
}