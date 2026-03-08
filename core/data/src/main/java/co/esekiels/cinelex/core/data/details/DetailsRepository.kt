/*
 * Cinelex
 * DetailsRepository
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.data.details

import co.esekiels.cinelex.core.model.MovieDetails

interface DetailsRepository {
    suspend fun fetchMovieDetails(id: Int): MovieDetails
}
