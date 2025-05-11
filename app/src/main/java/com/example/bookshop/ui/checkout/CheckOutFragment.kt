package com.example.bookshop.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshop.R
import com.example.bookshop.databinding.FragmentCheckOutBinding
import com.example.bookshop.ui.adapter.BookListAdapter
import com.example.bookshop.ui.main.cart.CartViewModel
import com.example.bookshop.ui.profile.ProfileViewModel
import com.example.bookshop.ui.profile.ProfileViewModelFactory
import com.example.bookshop.ui.profile.receiver.receiver.ReceiversFragment
import com.example.bookshop.ui.profile.receiver.receiverinfo.ReceiverInfoFragment
import com.example.bookshop.ui.profile.receiver.receiverinfo.ReceiverInfoViewModel
import com.example.bookshop.utils.AlertMessageViewer
import com.example.bookshop.utils.LoadingProgressBar
import com.example.bookshop.utils.format.FormatMoney

class CheckOutFragment : Fragment() {

    companion object {
        fun newInstance() = CheckOutFragment()
    }

    private lateinit var viewModelCheckOut: CheckOutViewModel
    private lateinit var viewModelCart: CartViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var viewModelReceiver: ReceiverInfoViewModel
    private lateinit var loadingProgressBar: LoadingProgressBar
    private var binding: FragmentCheckOutBinding? = null
    private lateinit var adapter: BookListAdapter
    private var formatMoney = FormatMoney()
    private var cartId = ""
    private var check = false
    private var shippingId = 1
    private var shippingPrice = 50000.00

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        binding = FragmentCheckOutBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelCheckOut = ViewModelProvider(this)[CheckOutViewModel::class.java]
        viewModelCart = ViewModelProvider(this)[CartViewModel::class.java]
        viewModelReceiver = ViewModelProvider(this)[ReceiverInfoViewModel::class.java]
        viewModelProfile = ViewModelProvider(
            this,
            ProfileViewModelFactory(requireActivity().application)
        )[ProfileViewModel::class.java]
        loadingProgressBar = LoadingProgressBar(requireContext())
        loadingProgressBar.show()
        adapter = BookListAdapter()
        initViewModel()
        viewModelCart.getCartId()
        viewModelCart.getAllCartItem()
        viewModelProfile.getCustomer()
        viewModelReceiver.getReceiverDefault()
        val selected = arguments?.getString("selected")
        selected?.let {
            viewModelReceiver.getReceiverSelected()
        }

        Handler().postDelayed({
            if (binding?.idReceiverInfo?.text.toString().isEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setMessage("Không có địa chỉ nhận hàng, vui lòng thêm địa chỉ nhận hàng")
                    .setCancelable(false)
                    .setPositiveButton("Thoát") { dialog, _ ->
                        parentFragmentManager.popBackStack()
                        dialog.cancel()
                    }
                    .setNegativeButton("Đồng ý") { dialog, _ ->
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, ReceiverInfoFragment())
                            .addToBackStack("CheckOut")
                            .commit()
                        dialog.cancel()
                    }
                    .show()
            }
        }, 500)
        val items =
            arrayOf("Thanh toán bằng tiền mặt")

        // Kết nối ArrayAdapter với Spinner
        val adapterSpinner = ArrayAdapter(requireContext(), R.layout.item_spinner, items)
        var idPayment = 0L
        binding?.apply {
            spinnerPayment.adapter = adapterSpinner
            spinnerPayment.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    idPayment = id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
            textChangeInfor.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, ReceiversFragment())
                    .addToBackStack("CheckOut")
                    .commit()
            }
            textPayment.setOnClickListener {
                when (idPayment) {
                    0L -> paymentCash()
                }

//                check = true
//                loadingProgressBar.show()
            }
            imageLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
                viewModelReceiver.updateReceiverDefaultIsSelected()
            }
            recyclerCartItem.layoutManager = LinearLayoutManager(context)
            recyclerCartItem.adapter = adapter
        }
    }

    private fun paymentCash() {
        check = true
        createOrder(1)
        loadingProgressBar.show()
    }

    private fun createOrder(paymentId: Int) {
        val receiverId = binding?.idReceiverInfo?.text.toString().toInt()
        viewModelCheckOut.createOrder(
            cartId,
            shippingId,
            receiverId,
            paymentId,
        )
    }

    private fun initViewModel() {
        viewModelCheckOut.message.observe(viewLifecycleOwner) {
            loadingProgressBar.cancel()
            if (check) {
                AlertDialog.Builder(requireContext())
                    .setMessage(it.message.message.capitalize())
                    .setCancelable(false)
                    .setPositiveButton("Close") { dialog, _ ->
                        if (it.isState) {
                            parentFragmentManager.popBackStack()
                            dialog.cancel()
                        } else {
                            dialog.cancel()
                        }
                    }
                    .show()
                check = false
            }
        }
        viewModelCart.cartItem.observe(viewLifecycleOwner) { cartItem ->
            loadingProgressBar.cancel()
            binding?.apply {
                adapter.setDataCart(cartItem)
                textTotalPriceProduct.text =
                    formatMoney.formatMoney(adapter.getTotalPrice().toLong())
                textPromotionPrice.text =
                    formatMoney.formatMoney(adapter.getTotalPromotionPrice().toLong())
                getTotalPrice()
            }
        }
        viewModelCart.cartId.observe(viewLifecycleOwner) {
            cartId = it
        }
        viewModelReceiver.receiverInfo.observe(viewLifecycleOwner) { receiverInfo ->
            loadingProgressBar.cancel()
            if (receiverInfo != null) {
                binding?.apply {
                    idReceiverInfo.text = receiverInfo.receiverId.toString()
                    textCustomerName.text = receiverInfo.receiverName
                    textCutomerPhone.text = receiverInfo.receiverPhone
                    textCutomerAddress.text = receiverInfo.receiverAddress
                }
            }
        }
    }

    private fun getTotalPrice() {
        binding?.apply {
            textShipPrice.text =
                formatMoney.formatMoney(shippingPrice.toLong())
            var totalPrice = textTotalPriceProduct.text.replace(Regex("[^\\d]"), "").toInt() +
                    shippingPrice.toInt() - textPromotionPrice.text
                .replace(Regex("[^\\d]"), "").toInt()
            textTotalPrice.text = formatMoney.formatMoney(totalPrice.toLong())
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radiobtn_express -> {
                        shippingId = 1
                        shippingPrice = 50000.00
                    }
                    R.id.radiobtn_fast -> {
                        shippingId = 2
                        shippingPrice = 30000.00
                    }
                    R.id.radiobtn_economical -> {
                        shippingId = 3
                        shippingPrice = 10000.00
                    }
                }
                textShipPrice.text =
                    formatMoney.formatMoney(shippingPrice.toLong())
                totalPrice = textTotalPriceProduct.text.replace(Regex("[^\\d]"), "").toInt() +
                        shippingPrice.toInt() - textPromotionPrice.text
                    .replace(Regex("[^\\d]"), "").toInt()
                textTotalPrice.text = formatMoney.formatMoney(totalPrice.toLong())
            }
        }
    }
}