package com.milano.twitterclone

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.PropertyName
import com.milano.twitterclone.models.Post

class PostsAdapter(private val context: Context, private val posts: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            val tvUsername = itemView.findViewById<TextView>(R.id.textViewUserName)
            val tvUploadTime = itemView.findViewById<TextView>(R.id.textViewUploadTime)
            val tvTweetPost = itemView.findViewById<TextView>(R.id.textViewTweet)

            tvUsername.text = post.user?.username
            tvUploadTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
            tvTweetPost.text = post.description
        }
    }
}