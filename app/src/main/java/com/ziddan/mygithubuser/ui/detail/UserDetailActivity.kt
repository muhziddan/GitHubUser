package com.ziddan.mygithubuser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.ziddan.mygithubuser.R
import com.ziddan.mygithubuser.databinding.ActivityUserDetailBinding
import com.ziddan.mygithubuser.ui.detail.tab.SectionsPagerAdapter
import com.ziddan.mygithubuser.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataUser = intent.getStringExtra(EXTRA_USER)
        val userId = intent.getIntExtra(EXTRA_ID, 0)
        val userAvatar = intent.getStringExtra(EXTRA_AVATAR)

        viewModel = ViewModelProvider(
            this
        ).get(UserDetailViewModel::class.java)
        if (dataUser != null) {
            viewModel.setUserDetail(dataUser)
        }
        viewModel.getUserDetail().observe(this, { userItem ->
            if (userItem != null) {

                val repositoryText = resources.getString(R.string.repository, userItem.public_repos)
                val followersText = resources.getString(R.string.followers, userItem.followers)
                val followingText = resources.getString(R.string.following, userItem.following)
                Glide.with(this@UserDetailActivity)
                    .load(userItem.avatar_url)
                    .apply(RequestOptions().override(100, 100))
                    .into(binding.ivDetailAvatar)
                binding.tvDetailName.text = userItem.name
                binding.tvDetailCompany.text = userItem.company
                binding.tvDetailLocation.text = userItem.location
                binding.tvDetailRepository.text = repositoryText
                binding.tvDetailFollowers.text = followersText
                binding.tvDetailFollowing.text = followingText

                supportActionBar?.title = userItem.login
            }
        })

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, dataUser)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.vpDetail.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabDetail, binding.vpDetail) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavorite(userId)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.togFavorite.isChecked = true
                        checked = true
                    } else {
                        binding.togFavorite.isChecked = false
                        checked = false
                    }
                }
            }
        }

        binding.togFavorite.setOnClickListener {
            checked = !checked
            if (checked) {
                if (dataUser != null) {
                    if (userAvatar != null) {
                        viewModel.addFavorite(dataUser, userId, userAvatar)
                        Toast.makeText(
                            this@UserDetailActivity,
                            "Added to Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                viewModel.removeFavorite(userId)
                Toast.makeText(this@UserDetailActivity, "Removed from Favorite", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.togFavorite.isChecked = checked
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}