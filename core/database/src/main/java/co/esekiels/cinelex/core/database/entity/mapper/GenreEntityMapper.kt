/*
 * Cinelex
 * GenreEntityMapper
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.database.entity.mapper

import co.esekiels.cinelex.core.database.entity.GenreEntity
import co.esekiels.cinelex.core.model.Genre

fun List<Genre>.toEntities(): List<GenreEntity> = map { genre ->
	GenreEntity(
		id = genre.id,
		name = genre.name
	)
}


fun List<GenreEntity>.toDomain(): List<Genre> = map { entity ->
	Genre(
		id = entity.id,
		name = entity.name
	)
}

