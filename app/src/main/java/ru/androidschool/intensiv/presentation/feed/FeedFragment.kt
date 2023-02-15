package ru.androidschool.intensiv.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.dto.MovieDto
import ru.androidschool.intensiv.data.dto.MoviesResponseDto
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.domain.subObserve
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FeedFragment : Fragment(R.layout.feed_fragment){

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!
    private lateinit var db : MovieDatabase
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    val compositeDisposable = CompositeDisposable()

//    private val presenter: MoviesPresenter by lazy {
//        MoviesPresenter(PopularMovieUseCase(PopularMoviesRemoteRepository))
//    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        presenter.attachView(this)
//
//        presenter.getPopularMovies()

        searchBinding.searchToolbar.onTextChangedObservable
            .map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.toString().length > MIN_LENGTH }
            .observeOn(Schedulers.io())
            .flatMapSingle {
                MovieApiClient.apiClient.searchByQuery(API_KEY, "ru", it)
            }
            .subscribe({
                openSearch(it.results)
                Timber.tag(TAG).d(it.toString())
            }, {
                Timber.tag(TAG).e(it.toString())
            })

        val getNowPlayingMovie = MovieApiClient.apiClient.getNowPlayingMovie()

        val getUpcomingMovie = MovieApiClient.apiClient.getUpcomingMovies()

        val getMoviePopulars = MovieApiClient.apiClient.getPopularMovies()

        val hashMapMovie = Single.zip(getNowPlayingMovie, getUpcomingMovie, getMoviePopulars, Function3<
                MoviesResponseDto,
                MoviesResponseDto,
                MoviesResponseDto,
                HashMap<String, List<MovieDto>>> {
                t1: MoviesResponseDto, t2: MoviesResponseDto, t3: MoviesResponseDto ->
            hashMapOf(MovieEnumClass.NOWPLAYING.toString() to t1.results,
                MovieEnumClass.UPCOMING.toString() to t2.results,
                MovieEnumClass.POPULAR.toString() to t3.results)
        })

        compositeDisposable.add(hashMapMovie
            .subObserve()
            .subscribe({ movies ->

                val moviePopular = createMainCardContainer(movies.getValue(MovieEnumClass.POPULAR.toString()), R.string.popular)
                val movieUpcoming = createMainCardContainer(movies.getValue(MovieEnumClass.UPCOMING.toString()), R.string.upcoming)
                val movieNowPlaying = createMainCardContainer(movies.getValue(MovieEnumClass.NOWPLAYING.toString()), R.string.recommended)

                binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviePopular) }
                binding.progressBar.visibility = View.INVISIBLE

                binding.moviesRecyclerView.adapter = adapter.apply { addAll(movieUpcoming) }

                binding.moviesRecyclerView.adapter = adapter.apply { addAll(movieNowPlaying)}

            }, { error ->
                // Логируем ошибку
                Timber.tag(TAG).e(error.toString())
            })
        )
    }

    fun createMainCardContainer(listMovie: List<MovieDto>, typeMovie: Int): List<MainCardContainer> {
        val list = listOf(listMovie.map {
            MovieItem(it) { movie ->
                openMovieDetails(movie)
            }
        }.let {
            MainCardContainer(typeMovie, it.toList())
        })
        return list
    }

    private fun openMovieDetails(movie: MovieDto) {
        val bundle = Bundle()
        //добавить всю инфу через "bundle" для уаждого поля свой ключ(или чере зайти)
//        db.movieDao().save(listOf(MovieEntity(movie.id,movie.title)))
//            .subscribeOn(Schedulers.io())
//            .subscribe { }
      //  bundle.putString(KEY_TITLE, movie.id)
        bundle.putString(KEY_TITLE, movie.title)
        bundle.putDouble(RATING, movie.voteAverage)
        bundle.putString(OVERVIEW, movie.overview)
        bundle.putInt(ID, movie.id)
        bundle.putString(POSTERPATH, movie.posterPath)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: List<MovieDto>) {
        val bundle = Bundle()
        bundle.putParcelableArrayList("list", ArrayList(searchText))
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
        adapter.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }




    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
        private val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        const val TAG = "FeedFragment"
        const val LANGUAGE = "ru"
        const val RATING = "rating"
        const val OVERVIEW = "overview"
        const val POSTERPATH = "posterPath"
        const val ID = "id"
    }

}
