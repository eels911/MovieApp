package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.database.MovieEntity
import ru.androidschool.intensiv.data.dto.MovieDto
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.vo.MockRepository
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

private var _binding: MovieDetailsFragmentBinding? = null

    private val binding get() = _binding!!
    private var db : MovieDatabase? = null
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }





    fun convertMovie(dto: MovieDto): MovieEntity { return MovieEntity(dto.id ,dto.title) }
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    // Используя Мок-репозиторий получаем фэйковый список фильмов
    val actorList =
        MockRepository.getActors().map {
            ActorItem(it)
        }



    val getMovieDetails = MovieApiClient.apiClient.getMovieDetails(id)

//            val movieList =  listOf( MainCardContainer(
//                R.string.recommended,
//                movies.map {
//                    MovieItem(it){
//                            movie ->
//                        openMovieDetails(movie)
//                    }
//                }.toList())
//            )

    binding.listActor.adapter = adapter.apply { addAll(actorList) }
}

//    private fun setViews(movie: MovieDetailsResponse) {
//        binding.tvMovieName.text = movie.title
//    }
}
