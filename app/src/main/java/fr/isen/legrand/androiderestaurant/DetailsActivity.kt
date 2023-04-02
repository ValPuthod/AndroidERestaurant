package fr.isen.legrand.androiderestaurant

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import fr.isen.legrand.androiderestaurant.model.ApiParameterData
import fr.isen.legrand.androiderestaurant.model.CartAllItems
import fr.isen.legrand.androiderestaurant.model.CartItemData
import fr.isen.legrand.androiderestaurant.model.RequestItems

class DetailsActivity : AppCompatActivity() {
    private var sharedPref: SharedPreferences? = null
    private var positionInCart: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val item = intent.getStringExtra("item")

        val jsonItem = Gson().fromJson(item, RequestItems::class.java)

        title = jsonItem.name_fr


        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        viewPager.adapter = CarouselAdapter(jsonItem.images as ArrayList<String>)

        val ingredients = findViewById<TextView>(R.id.tv_ingredients)

        val commaSeparatedString = jsonItem.ingredients.joinToString (separator = ",") { " "+it.name_fr}

        ingredients.text = commaSeparatedString



        // init widgets
        val ivMinus = findViewById<ImageView>(R.id.iv_minus)

        val ivPlus = findViewById<ImageView>(R.id.iv_plus)

        val btnTotal = findViewById<Button>(R.id.btn_total)

        val tvCount = findViewById<TextView>(R.id.tv_count)



        // setting cart data
        sharedPref = getPreferences(Context.MODE_PRIVATE)

        var cartItemsData: CartItemData = CartItemData(arrayListOf(CartAllItems(0, jsonItem)))

        val cartLocal = sharedPref?.getString("cart", "")

        if (cartLocal.isNullOrEmpty()) {

            tvCount.text = "0"
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()

        }
        else {

            cartItemsData = Gson().fromJson(cartLocal, CartItemData::class.java)
            for ((i, d) in cartItemsData.allItems.withIndex()) {

                if (d.item.name_fr == jsonItem.name_fr) {
                    tvCount.text = d.count.toString()

                    val price = jsonItem.prices[0].price
                    val totalPrice = price.toFloat() * d.count
                    btnTotal.text = "Total €$totalPrice"
                    positionInCart = i
                    break
                }

            }

        }

        var count = tvCount.text.toString().toInt()

        ivMinus.setOnClickListener {

            if (count != 0) {

                count -= 1
                tvCount.text = count.toString()

                val price = jsonItem.prices[0].price
                val totalPrice = price.toFloat() * count
                btnTotal.text = "Total €$totalPrice"
            }
        }

        ivPlus.setOnClickListener {

            count += 1
            tvCount.text = count.toString()

            val price = jsonItem.prices[0].price
            val totalPrice = price.toFloat() * count
            btnTotal.text = "Total €$totalPrice"
        }

        btnTotal.setOnClickListener {

            val cartAllItems = CartAllItems(count, jsonItem)

            if (positionInCart!=null) {

                cartItemsData.allItems[positionInCart!!] = cartAllItems

            }
            else {

                cartItemsData.allItems.add(cartAllItems)
            }

            val jsonn = Gson().toJson(cartItemsData)

            sharedPref?.edit()?.putString("cart", jsonn)?.apply()

            Toast.makeText(this, "Cart Updated", Toast.LENGTH_SHORT).show()
            finish()

        }



    }
}