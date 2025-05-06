package com.example.bookshop.ui.auth.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bookshop.R
import com.example.bookshop.data.api.RetrofitClient
import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.response.auth.AuthResponse
import com.example.bookshop.databinding.FragmentLogInBinding
import com.example.bookshop.ui.auth.signup.SignUpFragment
import com.example.bookshop.ui.main.MainMenuFragment
import com.example.bookshop.utils.AlertMessageViewer
import com.example.bookshop.utils.LoadingProgressBar
import com.example.bookshop.utils.MySharedPreferences

class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
    }
    private lateinit var viewModel: LoginViewModel
    private var binding: FragmentLogInBinding? = null
    private var checkVisible = false
    private lateinit var loadingProgressBar: LoadingProgressBar
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val accessToken = MySharedPreferences.getAccessToken(null)
        val expiresIn = MySharedPreferences.getLogInTime("ExpiresIn", 0)
        val loginTimeFirst = MySharedPreferences.getLogInTime("FirstTime", 0)
        if (accessToken != null) {
            val loginTime = System.currentTimeMillis()
            navToMainScreen()
            RetrofitClient.updateAccessToken(accessToken)
            if ((loginTime - loginTimeFirst) > expiresIn) {
                MySharedPreferences.clearPreferences()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, ProfileSignInFragment()).commit()
                AlertMessageViewer.showAlertDialogMessage(
                    requireContext(),
                    resources.getString(R.string.messageExpiresIn)
                )
            }
        }
        loadingProgressBar = LoadingProgressBar(requireContext())
        binding?.apply {
            layoutLogin.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    val event =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    event.hideSoftInputFromWindow(requireView().windowToken, 0)
                }
                false
            }
            buttonLogin.setOnClickListener {
                val username = editUsername.text.toString()
                val password = editPassword.text.toString()
                val customer = Customer(email = username, password = password)
                val user = AuthResponse(customer = customer)
                viewModel.checkFields(user)
                loadingProgressBar.show()
            }
            textRegister.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, SignUpFragment())
                    .addToBackStack("Login")
                    .commit()
            }
            imageEye.setOnClickListener {
                val cursorPosition = editPassword.selectionEnd
                if (!checkVisible) {
                    editPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    checkVisible = true
                    imageEye.setImageResource(R.drawable.ic_hide_eye)
                } else {
                    editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    checkVisible = false
                    imageEye.setImageResource(R.drawable.ic_visible_eye)
                }
                if (cursorPosition >= 0) {
                    editPassword.setSelection(cursorPosition)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (doubleBackToExitPressedOnce) {
                requireActivity().finish()
            } else {
                doubleBackToExitPressedOnce = true
                Toast.makeText(requireContext(), "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT)
                    .show()
                Handler().postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2500)
            }
        }
    }

    fun initViewModel() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            loadingProgressBar.cancel()
            if (it?.loginResponse == null) {
                it.error.message.let { it1 ->
                    AlertMessageViewer.showAlertDialogMessage(
                        requireContext(),
                        it1
                    )
                }
            } else if (it?.loginResponse.customer.status.equals("inactive")) {
                AlertMessageViewer.showAlertDialogMessage(
                    requireContext(),
                    "Tài khoản của bạn đã bị khóa!"
                )
            } else {
                navToMainScreen()
                MySharedPreferences.putAccessToken(it.loginResponse.accessToken)
                val expiresIn = it.loginResponse.expiresIn.split(" ")[0].toLong()
                MySharedPreferences.putLogInTime("ExpiresIn", expiresIn * 60 * 60 * 1000)
                MySharedPreferences.putLogInTime("FirstTime", System.currentTimeMillis())
                RetrofitClient.updateAccessToken(it.loginResponse.accessToken)
                it.loginResponse.customer.customerId?.let { idCustomer ->
                    MySharedPreferences.putInt(
                        "idCustomer",
                        idCustomer
                    )
                }
            }
        }
    }

    fun navToMainScreen() {
        parentFragmentManager.beginTransaction().replace(R.id.container, MainMenuFragment())
            .commit()
    }
}