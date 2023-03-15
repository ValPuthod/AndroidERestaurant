// Importation des classes nécessaires pour le fonctionnement de l'activity
package fr.isen.legrand.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import fr.isen.legrand.androiderestaurant.databinding.ActivityHomeBinding

// Définition de la classe "HomeActivity" qui étend la classe "AppCompatActivity"
class HomeActivity : AppCompatActivity() {

    // Initialisation de la variable de liaison
    private lateinit var binding: ActivityHomeBinding

    // Cette méthode est appelée lorsque l'activity est créée
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Liaison de l'activity à son layout correspondant
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Définition des actions à effectuer lorsqu'on clique sur chaque bouton de catégorie
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
