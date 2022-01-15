package uz.adkhamjon.promovie.models.Details

import java.io.Serializable

data class ProductionCompany(
    val id: Int,
    val logo_path: Any,
    val name: String,
    val origin_country: String
): Serializable