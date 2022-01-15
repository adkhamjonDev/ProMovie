package uz.adkhamjon.promovie.models.Similar

import uz.adkhamjon.promovie.models.Similar.Results
import java.io.Serializable

data class SimilarClass(
    val page: Int,
    val results: List<Results>,
    val total_pages: Int,
    val total_results: Int
): Serializable