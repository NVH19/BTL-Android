package com.example.bookshop.ui.productdetail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bookshop.R
import com.example.bookshop.data.model.response.product.ProductInfoList
import com.example.bookshop.databinding.FragmentProductDetailBinding
import com.example.bookshop.ui.main.wishlist.WishlistViewModel
import com.example.bookshop.utils.AlertMessageViewer
import com.example.bookshop.utils.format.FormatMoney
import com.example.bookshop.utils.LoadingProgressBar
import com.example.bookshop.utils.MySharedPreferences

class ProductdetailFragment : Fragment() {
    private var binding: FragmentProductDetailBinding? = null
    private lateinit var viewModel: ProductdetailViewModel
    private lateinit var viewModelWishList: WishlistViewModel
    private var wishlist: Int = 0
    private val formatMoney = FormatMoney()
    private var sizeWishList = 0
    private lateinit var loadingProgressBar: LoadingProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductdetailViewModel::class.java]
        viewModelWishList = ViewModelProvider(this)[WishlistViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        loadingProgressBar = LoadingProgressBar(requireContext())
        loadingProgressBar.show()
        viewModelWishList.getWishList(10, 1, 100)
        val productId = arguments?.getString("bookId")?.toInt()
        productId?.let {
            viewModel.getProductInfo(it)
        }
        activity?.let { MySharedPreferences.init(it.applicationContext) }
        binding?.apply {
            // Xử lý thêm sản phẩm vào giỏ hàng
            textAdditemtocart.setOnClickListener {
                val str = textNum.text.toString().split(" ")
                val quantityRemaining = str[str.size - 1].toInt()
                if (quantityRemaining > 0) {
                    productId?.let { productId ->
                        viewModel.addItemToCart(productId)
                        Handler().postDelayed({
                            viewModel.getProductInfo(productId)
                        }, 500)
                        AlertMessageViewer.showAlertDialogMessage(
                            requireContext(),
                            "Đã thêm sản phẩm vào giỏ hàng!"
                        )
                    }
                } else {
                    AlertMessageViewer.showAlertDialogMessage(
                        requireContext(),
                        "Sản phẩm này tạm hết"
                    )
                }
            }

            // Xử lý thêm/xóa sản phẩm khỏi wishlist
            imageFavorite.setOnClickListener {
                productId?.let { productId -> itemWishList(productId) }
            }
        }
    }

    private fun initViewModel() {
        viewModel.productInfo.observe(viewLifecycleOwner) { productInfoList ->
            productInfoList?.let {
                bindData(it)
                wishlist = it.product.wishlist
            }
        }
        viewModelWishList.wishList.observe(viewLifecycleOwner) { wishlist ->
            sizeWishList = wishlist.wishlist.size
        }
    }

    private fun itemWishList(productId: Int) {
        MySharedPreferences.putInt("productId", productId)
        if (wishlist == 0) {
            if (sizeWishList >= 10) {
                AlertMessageViewer.showAlertDialogMessage(
                    requireContext(),
                    "Chỉ lưu được 10 sản phẩm trong wishlist"
                )
            } else {
                viewModel.addItemToWishList(productId)
                wishlist = 1
                Toast.makeText(context, "Đã thêm vào wishlist của bạn!", Toast.LENGTH_SHORT).show()
                MySharedPreferences.putInt("wishlist", 1)
                binding?.imageFavorite?.setImageResource(R.drawable.ic_favorite)
                binding?.imageFavorite?.setBackgroundResource(R.drawable.bg_ellipse_favor)
            }
        } else {
            viewModel.removeItemInWishList(productId)
            wishlist = 0
            Toast.makeText(context, "Đã xóa khỏi wishlist của bạn!", Toast.LENGTH_SHORT).show()
            MySharedPreferences.putInt("wishlist", 0)
            binding?.imageFavorite?.setImageResource(R.drawable.ic_favor_white)
            binding?.imageFavorite?.setBackgroundResource(R.drawable.bg_ellipse)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(productInfoList: ProductInfoList) {
        binding?.apply {

            textNum.text =
                resources.getString(R.string.quantity) + " " + (productInfoList.product.quantity - productInfoList.product.quantitySold)
            textPrice.text =
                formatMoney.formatMoney(productInfoList.product.price.toDouble().toLong())


            val wishListPre = MySharedPreferences.getInt("wishlist", -1)
            val productIdPre = MySharedPreferences.getInt("productId", -1)
            if (wishListPre != -1 && productIdPre == productInfoList.product.productId) {
                if (MySharedPreferences.getInt("wishlist", -1) == 1) {
                    imageFavorite.setBackgroundResource(R.drawable.bg_ellipse_favor)
                    imageFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding?.imageFavorite?.setImageResource(R.drawable.ic_favor_white)
                    binding?.imageFavorite?.setBackgroundResource(R.drawable.bg_ellipse)
                }
            } else {
                if (productInfoList.product.wishlist == 1) {
                    imageFavorite.setBackgroundResource(R.drawable.bg_ellipse_favor)
                    imageFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding?.imageFavorite?.setImageResource(R.drawable.ic_favor_white)
                    binding?.imageFavorite?.setBackgroundResource(R.drawable.bg_ellipse)
                }
            }
            loadingProgressBar.cancel()
        }
    }
}