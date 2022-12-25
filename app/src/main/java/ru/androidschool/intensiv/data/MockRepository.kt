package ru.androidschool.intensiv.data



object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }
        return moviesList
    }

    fun getActors(): List<Actor> {
        val actorList = mutableListOf<Actor>()
        for (x in 0..10) {
            val actor = Actor(
                title = "Sabina S"
            )
            actorList.add(actor)
        }
        return actorList
    }
}
