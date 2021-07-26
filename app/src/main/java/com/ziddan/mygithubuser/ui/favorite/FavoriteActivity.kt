package com.ziddan.mygithubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziddan.mygithubuser.R
import com.ziddan.mygithubuser.data.general.UserItem
import com.ziddan.mygithubuser.data.local.FavoriteUser
import com.ziddan.mygithubuser.databinding.ActivityFavoriteBinding
import com.ziddan.mygithubuser.ui.detail.UserDetailActivity
import com.ziddan.mygithubuser.ui.main.MainActivity
import com.ziddan.mygithubuser.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = userAdapter

        favoriteViewModel = ViewModelProvider(
            this
        ).get(FavoriteViewModel::class.java)

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val showDetail = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                showDetail.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                showDetail.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                showDetail.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                startActivity(showDetail)
            }

        })

        favoriteViewModel.getFavorite()?.observe(this, { userFav ->
            if (userFav != null) {
                var data = convertData(userFav)
                userAdapter.setData(data)
            }
        })

        supportActionBar?.title = resources.getString(R.string.favorite_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun convertData(userFavorite: List<FavoriteUser>): ArrayList<UserItem> {
        val dataUser = ArrayList<UserItem>()
        for (user in userFavorite) {
            val converted = UserItem(
                user.login,
                user.id,
                user.avatar_url
            )
            dataUser.add(converted)
        }
        return dataUser
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}