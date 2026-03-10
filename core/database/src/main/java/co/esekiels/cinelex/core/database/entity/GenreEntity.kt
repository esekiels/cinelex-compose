/*
 * Cinelex
 * GenreEntity
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
	@PrimaryKey
	val id: Int,
	val name: String
)
