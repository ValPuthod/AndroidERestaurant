package fr.isen.legrand.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import fr.isen.legrand.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var blinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        blinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(blinding.root)

       blinding.entrees.setOnClickListener {
            val intent = Intent(this,entrees::class.java)
            startActivity(intent)
            // Gérer le clic du premier bouton ici
        }

        blinding.plats.setOnClickListener {
            val intent = Intent(this,entrees::class.java)
            startActivity(intent)
            // Gérer le clic du deuxième bouton ici
        }

        blinding.desserts.setOnClickListener {
            val intent = Intent(this,entrees::class.java)
            startActivity(intent)
            // Gérer le clic du troisième bouton ici
        }
    }
}