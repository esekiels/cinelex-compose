/*
 * Cinelex
 * MovieDetailsEntityMapper
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.database.entity.mapper

import co.esekiels.cinelex.core.database.entity.MovieDetailsEntity
import co.esekiels.cinelex.core.model.MovieDetails

fun MovieDetails.toEntities(): MovieDetailsEntity = MovieDetailsEntity(
    id = id,
    title = title,
    backdropPath = backdropPath,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    runtime = runtime,
    genres = genres,
    credits = credits,
    videos = videos,
)

fun MovieDetailsEntity.toDomain(): MovieDetails = MovieDetails(
    id = id,
    title = title,
    backdropPath = backdropPath,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    runtime = runtime,
    genres = genres,
    credits = credits,
    videos = videos,
)
