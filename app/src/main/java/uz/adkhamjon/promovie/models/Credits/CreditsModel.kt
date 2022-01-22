package uz.adkhamjon.promovie.models.Credits

data class CreditsModel(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)