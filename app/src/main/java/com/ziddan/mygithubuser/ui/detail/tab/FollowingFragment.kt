package com.ziddan.mygithubuser.ui.detail.tab

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziddan.mygithubuser.R
import com.ziddan.mygithubuser.data.general.UserItem
import com.ziddan.mygithubuser.databinding.FragmentFollowgroupBinding
import com.ziddan.mygithubuser.ui.detail.UserDetailActivity
import com.ziddan.mygithubuser.ui.main.UserAdapter

class FollowingFragment : Fragment(R.layout.fragment_followgroup) {

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var following: String
    private var _binding: FragmentFollowgroupBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        following = args?.getString(UserDetailActivity.EXTRA_USER).toString()
        _binding = FragmentFollowgroupBinding.bind(view)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding?.rvFollowGroup?.setHasFixedSize(true)
        binding?.rvFollowGroup?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFollowGroup?.adapter = userAdapter

        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        showLoading(true)
        followingViewModel.setUserFollowing(following)
        followingViewModel.getUserFollowing().observe(viewLifecycleOwner, { userItem ->
            if (userItem != null) {
                userAdapter.setData(userItem)
                showLoading(false)
            }
        })

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val showDetail = Intent(activity, UserDetailActivity::class.java)
                showDetail.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                showDetail.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                showDetail.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                startActivity(showDetail)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBarDetail?.visibility = View.VISIBLE
        } else {
            binding?.progressBarDetail?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}