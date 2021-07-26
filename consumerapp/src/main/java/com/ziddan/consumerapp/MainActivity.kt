package com.ziddan.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziddan.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = userAdapter

        favoriteViewModel = ViewModelProvider(
            this
        ).get(FavoriteViewModel::class.java)
        favoriteViewModel.setFavorite(this)

        favoriteViewModel.getFavorite()?.observe(this, { userFav ->
            if (userFav != null) {
                userAdapter.setData(userFav)
            }
        })

        supportActionBar?.title = resources.getString(R.string.favorite_title)
    }
}