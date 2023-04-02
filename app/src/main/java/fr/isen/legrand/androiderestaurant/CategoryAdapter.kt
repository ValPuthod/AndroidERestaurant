// Importation des classes nécessaires pour le fonctionnement de l'adapter
package fr.isen.legrand.androiderestaurant

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.isen.legrand.androiderestaurant.model.RequestItems
import fr.isen.legrand.androiderestaurant.model.RequestObject

// Définition de la classe "MenuAdapter" qui étend la classe "RecyclerView.Adapter"
class MenuAdapter(private val context: Context, private val menuList: List<RequestItems>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // Classe interne qui contient les éléments de la vue
    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menuTitle: TextView = view.findViewById(R.id.menu_title)
        val menuPrice: TextView = view.findViewById(R.id.menu_price)
        val menuPic: ImageView = view.findViewById(R.id.iv_menu)
    }

    // Cette méthode est appelée lorsque le RecyclerView a besoin d'un nouvel élément de vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    // Cette méthode est appelée pour remplir les éléments de la vue avec les données du menu
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.menuTitle.text = menuList[position].name_fr
        holder.menuPrice.text = menuList[position].prices[0].price

        val imageUrl = menuList[position].images[0]
        if(imageUrl.isNotEmpty()) {

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_placeholder)
                .into(holder.menuPic)
        }

        holder.itemView.setOnClickListener {

            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("item", Gson().toJson(menuList[position]))
            context.startActivity(intent)
        }
    }

    // Cette méthode retourne le nombre d'éléments de la liste de menus
    override fun getItemCount(): Int {
        return menuList.size
    }
}