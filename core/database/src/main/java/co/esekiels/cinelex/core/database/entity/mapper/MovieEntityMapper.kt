/*
 * Cinelex
 * MovieEntityMapper
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.database.entity.mapper

import co.esekiels.cinelex.core.database.entity.MovieEntity
import co.esekiels.cinelex.core.model.Movie

fun List<Movie>.toEntities(category: String): List<MovieEntity> = map { movie ->
    MovieEntity(
        id = movie.id,
        title = movie.title,
        posterPath = movie.posterPath ?: "",
        backdropPath = movie.backdropPath ?: "",
        category = category,
    )
}

fun List<MovieEntity>.toDomain(): List<Movie> = map { entity ->
    Movie(
        id = entity.id,
        title = entity.title,
        posterPath = entity.posterPath,
        backdropPath = entity.backdropPath,
    )
}
