package com.ziddan.mygithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziddan.mygithubuser.R
import com.ziddan.mygithubuser.data.general.UserItem
import com.ziddan.mygithubuser.databinding.ActivityMainBinding
import com.ziddan.mygithubuser.ui.detail.UserDetailActivity
import com.ziddan.mygithubuser.ui.favorite.FavoriteActivity
import com.ziddan.mygithubuser.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.btnSearch.setOnClickListener {
            searchUser()
        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        viewModel.getUsers().observe(this, { userItem ->
            if (userItem != null) {
                adapter.setData(userItem)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val showDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                showDetail.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                showDetail.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                showDetail.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                startActivity(showDetail)
            }
        })

        supportActionBar?.title = "GitHub User"
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showPicture(state: Boolean) {
        if (state) {
            binding.ivWait.visibility = View.GONE
            binding.tvPictureCaption.visibility = View.GONE
        } else {
            binding.ivWait.visibility = View.GONE
            binding.tvPictureCaption.visibility = View.GONE
        }
    }

    private fun searchUser() {
        val query = binding.etSearch.text.toString()
        if (query.isEmpty()) return
        showLoading(true)
        showPicture(true)
        viewModel.setUsers(query)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}