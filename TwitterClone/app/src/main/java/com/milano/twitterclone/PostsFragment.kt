package com.milano.twitterclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.milano.twitterclone.models.Post
import com.milano.twitterclone.models.User

private const val TAG = "PostsFragment"
const val EXTRA_USERNAME = "EXTRA_USERNAME"

open class PostsFragment : Fragment() {

    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_posts, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addBtn = view.findViewById<FloatingActionButton>(R.id.fabCreate)

        addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postsFragment_to_createFragment)
        }

        posts = mutableListOf()
        adapter = PostsAdapter(requireContext(), posts)
        val rvPosts: RecyclerView = view.findViewById(R.id.rvPosts)
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(requireContext())

        firestoreDb = FirebaseFirestore.getInstance()

        val postsReference = firestoreDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)


        postsReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post $post")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_postsFragment_to_profileFragment)
        return super.onOptionsItemSelected(item)
    }
}