// Importation des classes nécessaires pour le fonctionnement de l'activité
package fr.isen.legrand.androiderestaurant

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.legrand.androiderestaurant.model.ApiParameterData
import fr.isen.legrand.androiderestaurant.model.RequestItems
import fr.isen.legrand.androiderestaurant.model.RequestObject
import org.json.JSONObject

// Définition de la classe "entrees" qui étend la classe "AppCompatActivity"
class CategoryActivity : AppCompatActivity() {

    // Cette méthode est appelée lorsque l'activité est créée

    private var sharedPref: SharedPreferences? = null
    private var mySwipeRefreshLayout: SwipeRefreshLayout? = null
    lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Définition du layout de l'activité
        setContentView(R.layout.menu_activity)

        // Définition du titre de l'activité en fonction de la catégorie choisie
        when (intent.getStringExtra("categorie")) {
            CATEGORY_ENTREES -> category = getString(R.string.entrees)
            CATEGORY_PLATS -> category = getString(R.string.plats)
            CATEGORY_DESSERTS -> category = getString(R.string.desserts)
        }

        title = category

        /*
        * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
        * performs a swipe-to-refresh gesture.
        */
        mySwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        mySwipeRefreshLayout?.setOnRefreshListener {
            Log.i("OnRefresh", "onRefresh called from SwipeRefreshLayout")

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            fetchItems()
        }

        // Getting data from local stored

        sharedPref = getPreferences(Context.MODE_PRIVATE)

        val data = sharedPref?.getString("data", "")

        if (data.isNullOrEmpty()) {

            // Initialisation de la vue du RecyclerView

            fetchItems()

        }
        else {

            // setting data from local

            fetchItemsLocally(data)
        }

    }

    private fun fetchItemsLocally(localData: String) {

        val data = Gson().fromJson(localData, ApiParameterData::class.java)
        val menu = data.data.firstOrNull {

            it.name_fr == category

        }?.items

        Log.e("CategoryResponse", menu.toString())
        try {

            (setupRecyclerView(menu!!))
        } catch (e: java.lang.Exception) {

            Toast.makeText(this, "No details were found", Toast.LENGTH_SHORT).show()
        }

    }
    private fun fetchItems() {

        mySwipeRefreshLayout?.isRefreshing = true
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val requestBody = JSONObject().apply {
            put("id_shop", 1)
        }
        val request = JsonObjectRequest(Request.Method.POST, url, requestBody,
            { response ->

                // Adding data for offline use

                sharedPref?.edit()?.putString("data", response.toString())?.apply()

                mySwipeRefreshLayout?.isRefreshing = false
                val data = Gson().fromJson(response.toString(), ApiParameterData::class.java)
                val menu = data.data.firstOrNull {

                    it.name_fr == category

                }?.items

                Log.e("CategoryResponse", menu.toString())
                try {

                    (setupRecyclerView(menu!!))
                } catch (e: java.lang.Exception) {

                    Toast.makeText(this, "No details were found", Toast.LENGTH_SHORT).show()
                }


            },
            { error ->

                mySwipeRefreshLayout?.isRefreshing = false
                Log.e("CategoryError", error.toString())
                Toast.makeText(this, "No details were found", Toast.LENGTH_SHORT).show()

            })

        Volley.newRequestQueue(this).add(request)

    }

    // Cette méthode retourne une liste de chaînes de caractères qui représentent les éléments du menu en fonction de la catégorie choisie
    private fun getMenuList(): List<String> {
        return when (intent.getStringExtra("categorie")) {
            CATEGORY_ENTREES -> resources.getStringArray(R.array.entrees).toList()
            CATEGORY_PLATS -> resources.getStringArray(R.array.plats).toList()
            CATEGORY_DESSERTS -> resources.getStringArray(R.array.desserts).toList()
            else -> emptyList() // Si la catégorie est inconnue, retourner une liste vide
        }
    }

    // Cette méthode initialise la vue du RecyclerView
    private fun setupRecyclerView(items: List<RequestItems>) {
        val recyclerView = findViewById<RecyclerView>(R.id.menu_recycler_view)

        // Définition du layoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Définition de l'adapter avec l'instance de la classe "MenuAdapter"
        recyclerView.adapter = MenuAdapter(this, items)
    }

    // Constantes pour les différentes catégories possibles
    companion object {
        const val CATEGORY_ENTREES = "entrees"
        const val CATEGORY_PLATS = "plats"
        const val CATEGORY_DESSERTS = "desserts"
    }
}