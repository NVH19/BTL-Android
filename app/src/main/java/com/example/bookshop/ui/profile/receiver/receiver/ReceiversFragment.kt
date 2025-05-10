package com.example.BookShopApp.ui.profile.receiver.receiver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshop.R
import com.example.bookshop.data.model.Receiver
import com.example.bookshop.databinding.FragmentReceiversBinding
import com.example.bookshop.ui.adapter.OnItemClickListener
import com.example.bookshop.ui.adapter.ReceiversAdapter
import com.example.bookshop.ui.profile.receiver.receiverinfo.ReceiverInfoFragment
import com.example.bookshop.ui.profile.receiver.receiverinfo.ReceiverInfoViewModel
import com.example.bookshop.utils.AlertMessageViewer
import com.example.bookshop.utils.LoadingProgressBar

class ReceiversFragment : Fragment() {
    companion object {
        fun newInstance() = ReceiversFragment()
    }

    private lateinit var viewModel: ReceiversViewModel
    private lateinit var viewModelInfo: ReceiverInfoViewModel
    private var binding: FragmentReceiversBinding? = null
    private lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var adapter: ReceiversAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ReceiversViewModel::class.java]
        viewModelInfo = ViewModelProvider(this)[ReceiverInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentReceiversBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReceiversAdapter()
        loadingProgressBar = LoadingProgressBar(requireContext())
        loadingProgressBar.show()
        initViewModel()
        viewModel.getReceivers()
        binding?.apply {
            recyclerReceivers.layoutManager = LinearLayoutManager(context)
            recyclerReceivers.adapter = adapter
            imageLeft.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
        addReceiver()
        navToUpdateReceiver()
        removeReceiver()
    }

    private fun initViewModel() {
        viewModel.receivers.observe(viewLifecycleOwner) {
            adapter.setData(it)
            loadingProgressBar.cancel()
        }
        viewModel.message.observe(viewLifecycleOwner) {
            if (it.message != "") {
                AlertMessageViewer.showAlertDialogMessage(requireContext(), it.message)
            }
        }
    }


    private fun removeReceiver() {
        adapter.setOnItemRemoveListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val receiver = adapter.getReceiver(position)
                if (receiver.isDefault == 1) {
                    AlertMessageViewer.showAlertDialogMessage(
                        requireContext(),
                        "Không thể xóa địa chỉ mặc định!"
                    )
                } else {
                    if (receiver.isSelected == 1) {
                        adapter.setIsSelected()
                        val receiverDefault = adapter.getReceiver(0)
                        viewModelInfo.updateReceiverInfo(
                            receiverId = receiverDefault.receiverId!!,
                            receiverName = receiverDefault.receiverName,
                            receiverPhone = receiverDefault.receiverPhone,
                            receiverAddress = receiverDefault.receiverAddress,
                            isDefault = receiverDefault.isDefault!!,
                            isSelected = 1
                        )
                    }
                    adapter.removeData(position)
                    receiver.receiverId?.let { viewModel.removeReceiver(it) }
                }
            }
        })
    }

    private fun navToUpdateReceiver() {
        adapter.setOnItemUpdateistener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val receiver = adapter.getReceiver(position)
                val bundle = Bundle()
                bundle.putSerializable("receiver", receiver)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, ReceiverInfoFragment().apply { arguments = bundle })
                    .addToBackStack("Receivers")
                    .commit()
                viewModel.clearMessage()
            }
        })
    }

    private fun addReceiver() {
        adapter.setOnItemAddListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, ReceiverInfoFragment())
                    .addToBackStack("ReceiverFragment")
                    .commit()
                viewModel.clearMessage()
            }
        })
    }
}