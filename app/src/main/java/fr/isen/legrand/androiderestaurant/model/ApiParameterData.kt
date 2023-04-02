package fr.isen.legrand.androiderestaurant.model

class ApiParameterData(
        val data: List<RequestObject>
)

class RequestObject(
        val name_fr: String,
        val name_en: String,
        val items: List<RequestItems>,

)

class RequestItems(
        val name_fr: String,
        val name_en: String,
        val categ_name_fr: String,
        val categ_name_en: String,
        val images: List<String>,
        val ingredients: List<RequestIngredients>,
        val prices: List<RequestPrices>

)
class RequestIngredients(
        val name_fr: String,
        val name_en: String,
        val categ_name_fr: String,
        val categ_name_en: String,
        val images: List<String>

)

class RequestPrices(
        val price: String,
        val size: String

)