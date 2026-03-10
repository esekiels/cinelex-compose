/*
 * Cinelex
 * PreviewStubs
 *
 * Created by Esekiel Surbakti on 09/03/26
 */

package co.esekiels.cinelex.core.preview

import co.esekiels.cinelex.core.model.Cast
import co.esekiels.cinelex.core.model.Credits
import co.esekiels.cinelex.core.model.Crew
import co.esekiels.cinelex.core.model.Genre
import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.model.Video
import co.esekiels.cinelex.core.model.VideoResponse

val MovieStubs = listOf(
    Movie(
        id = 278,
        title = "The Shawshank Redemption",
        backdropPath = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
        posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
    ),
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

val MovieDetailsStub = MovieDetails(
    id = 278,
    title = "The Shawshank Redemption",
    backdropPath = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
    posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
    overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, " +
        "upstanding banker Andy Dufresne begins a new life at the Shawshank prison, " +
        "where he puts his accounting skills to work for an amoral warden. During his " +
        "long stretch in prison, Dufresne comes to be admired by the other inmates -- " +
        "including an older prisoner named Red -- for his integrity and unquenchable " +
        "sense of hope.",
    voteAverage = 8.7,
    releaseDate = "1994-09-23",
    runtime = 142,
    genres = listOf(
        Genre(id = 18, name = "Drama"),
        Genre(id = 80, name = "Crime"),
    ),
    credits = Credits(
        cast = listOf(
            Cast(id = 504, name = "Tim Robbins", character = "Andy Dufresne"),
            Cast(id = 192, name = "Morgan Freeman", character = "Ellis Boyd 'Red' Redding"),
            Cast(id = 4029, name = "Bob Gunton", character = "Warden Samuel Norton"),
            Cast(id = 6574, name = "William Sadler", character = "Heywood"),
            Cast(id = 9857, name = "Clancy Brown", character = "Captain Byron Hadley"),
        ),
        crew = listOf(
            Crew(id = 4027, name = "Frank Darabont", job = "Director"),
            Crew(id = 4027, name = "Frank Darabont", job = "Screenplay"),
            Crew(id = 4028, name = "Niki Marvin", job = "Producer"),
        ),
    ),
    videos = VideoResponse(
        results = listOf(
            Video(
                id = "1",
                key = "PLl99DlL6b4",
                name = "Official Trailer",
                site = "YouTube",
                type = "Trailer",
            ),
        ),
    ),
)
