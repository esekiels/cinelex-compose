/*
 * Cinelex
 * MovieStubs
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.testing

import co.esekiels.cinelex.core.model.Movie

val MovieStub = Movie(
    id = 278,
    title = "The Shawshank Redemption",
    backdropPath = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
    posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
)

val MovieStubs = listOf(
    MovieStub,
    Movie(
        id = 238,
        title = "The Godfather",
        backdropPath = "/tSPT36ZKlP2WVHJLM4cQPLSzv3b.jpg",
        posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    ),
    Movie(
        id = 240,
        title = "The Godfather Part II",
        backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
        posterPath = "/hek3koDUyRQk7FIhPXsa6mT2Zc3.jpg",
    ),
    Movie(
        id = 424,
        title = "Schindler's List",
        backdropPath = "/zb6fM1CX41D9rF9hdgclu0peUmy.jpg",
        posterPath = "/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg",
    ),
    Movie(
        id = 155,
        title = "The Dark Knight",
        backdropPath = "/dqK9Hag1054tghRQSqLSfrkvQnA.jpg",
        posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
    ),
)
