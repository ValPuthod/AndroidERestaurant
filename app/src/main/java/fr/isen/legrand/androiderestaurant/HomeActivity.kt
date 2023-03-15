package fr.isen.legrand.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import fr.isen.legrand.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrees.setOnClickListener {
            val intent = Intent(this, entrees::class.java)
            intent.putExtra("categorie", entrees.CATEGORY_ENTREES)
            startActivity(intent)
        }

        binding.plats.setOnClickListener {
            val intent = Intent(this, entrees::class.java)
            intent.putExtra("categorie", entrees.CATEGORY_PLATS)
            startActivity(intent)
        }

        binding.desserts.setOnClickListener {
            val intent = Intent(this, entrees::class.java)
            intent.putExtra("categorie", entrees.CATEGORY_DESSERTS)
            startActivity(intent)
        }
    }
}