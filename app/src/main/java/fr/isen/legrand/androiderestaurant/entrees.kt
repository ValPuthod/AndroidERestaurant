package fr.isen.legrand.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class entrees : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        when (intent.getStringExtra("categorie")) {
            CATEGORY_ENTREES -> setTitle(getString(R.string.entrees))
            CATEGORY_PLATS -> setTitle(getString(R.string.plats))
            CATEGORY_DESSERTS -> setTitle(getString(R.string.desserts))
        }

        setupRecyclerView()
    }

    private fun getMenuList(): List<String> {
        return when (intent.getStringExtra("categorie")) {
            CATEGORY_ENTREES -> resources.getStringArray(R.array.entrees).toList()
            CATEGORY_PLATS -> resources.getStringArray(R.array.plats).toList()
            CATEGORY_DESSERTS -> resources.getStringArray(R.array.desserts).toList()
            else -> emptyList()
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.menu_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MenuAdapter(this, getMenuList())
    }

    companion object {
        const val CATEGORY_ENTREES = "entrees"
        const val CATEGORY_PLATS = "plats"
        const val CATEGORY_DESSERTS = "desserts"
    }
}
