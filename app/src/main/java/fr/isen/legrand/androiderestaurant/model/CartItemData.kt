package fr.isen.legrand.androiderestaurant.model

class CartItemData(

    val allItems: ArrayList<CartAllItems>
)
class CartAllItems(


    val count: Int,
    val item: RequestItems
)