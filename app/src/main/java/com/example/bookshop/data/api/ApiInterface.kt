package com.example.bookshop.data.api

import com.example.bookshop.data.model.*
import com.example.bookshop.data.model.response.*
import com.example.bookshop.data.model.response.author.AuthorFamousList
import com.example.bookshop.data.model.response.author.AuthorInfor
import com.example.bookshop.data.model.response.product.*
import com.example.bookshop.data.model.request.RatingRequest
import com.example.bookshop.data.model.response.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @FormUrlEncoded
    @POST("customers/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("customers/forgotPass")
    suspend fun fotgotPass(
        @Field("email") email: String,
    ): Response<Message>

    @FormUrlEncoded
    @POST("customers")
    suspend fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

    @GET("customers")
    suspend fun getCustomer(): Response<Customer>

    @GET("products/rating")
    suspend fun getAllRatingByBook(
        @Query("bookId") bookId: Int,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): Response<RatingResponse>
    @POST("products/rating")
    suspend fun createRatingOrder(
        @Body ratingRequest: List<RatingRequest>,
    ): Response<Message>


    @GET("products/banner")
    suspend fun getProductBanner(): Response<BannerList>

    @GET("products/{product_id}")
    suspend fun getProductInfo(@Path("product_id") product_id: Int): Response<ProductInfoList>

    @GET("products/author")
    suspend fun getProductsByAuthor(
        @Query("author_id") author_id: Int,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("description_length") description_length: Int,
    ): Response<ProductsByAuthor>

    @GET("products/incategory/{categoryId}")
    suspend fun getProductsByCategory(
        @Path("categoryId") categoryId: Int,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("description_length") description_length: Int,
    ): Response<ProductList>

    @GET("products/supplier")
    suspend fun getProductsBySupplier(
        @Query("supplier_id") supplierId: Int,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("description_length") description_length: Int,
    ): Response<ProductList>

    @GET("category")
    suspend fun getAllCategory(): Response<CategoryList>

    @GET("category/hot")
    suspend fun getHotCategory(): Response<CategoryList>

    @GET("products/new")
    suspend fun getNewBook(): Response<BookInHomeList>

    @GET("products/hot")
    suspend fun getHotBook(): Response<BookInHomeList>

    @GET("author/hot")
    suspend fun getHotAuthor(): Response<AuthorFamousList>

    @GET("author/{authorId}")
    suspend fun getAuthor(@Path("authorId") authorId: Int): Response<AuthorInfor>

    @DELETE("wishlist/remove/{product_id}")
    suspend fun removeItemInWishList(@Path("product_id") productId: Int): Response<Message>

    @FormUrlEncoded
    @POST("wishlist/add")
    suspend fun addItemToWishList(@Field("product_id") productId: Int): Response<Message>

    @GET("wishlist")
    suspend fun getWishList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("description_length") description_length: Int,
    ): Response<WishlistResponse>?

    @POST("shoppingCart/add/wishlist")
    suspend fun addAllItem2Cart(): Response<Message>

    @DELETE("shoppingCart/empty")
    suspend fun deleteAllItemCart(): Response<Message>

    @FormUrlEncoded
    @POST("shoppingCart/update")
    suspend fun changeProductQuantityInCart(
        @Field("item_id") itemId: Int,
        @Field("quantity") quantity: Int,
    ): Response<Message>?

    @DELETE("shoppingCart/removeProduct/{item_id}")
    suspend fun removeItemInCart(
        @Path("item_id") itemId: Int,
    ): Response<Message>?

    @GET("shoppingCart")
    suspend fun getAllCart(): Response<Cart>?

    @GET("products/new")
    suspend fun getSearchNewProduct(): Response<ProductNewList>

    @GET("products/search")
    suspend fun getSearchProducts(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("description_length") descriptionLength: Int,
        @Query("query_string") queryString: String,
        @Query("filter_type") filterType: Int,
        @Query("price_sort_order") priceSortOrder: String,
    ): Response<ProductList>

    @GET("products/category/search")
    suspend fun getSearchCategoryProducts(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("description_length") descriptionLength: Int,
        @Query("query_string") queryString: String,
        @Query("category_id") categoryId: Int,
    ): Response<ProductList>

    @FormUrlEncoded
    @POST("shoppingCart/add")
    suspend fun addProduct2Cart(@Field("product_id") productId: Int): Response<List<CartItem>>

    @GET("products/search")
    suspend fun getSearchHistory(
        @Query("query_string") queryString: String,
    ): Response<ProductList>
}