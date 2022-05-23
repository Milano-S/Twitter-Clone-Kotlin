package com.milano.twitterclone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.milano.twitterclone.models.Post
import com.milano.twitterclone.models.User

private const val TAG = "CreateFragment"

class CreateFragment : Fragment() {

    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var etPost: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etPost = view.findViewById<EditText>(R.id.etTweet)
        val postBtn = view.findViewById<Button>(R.id.buttonPost)
        postBtn.setOnClickListener {
            postBtn?.isEnabled = false
            handleSubmitButtonClick()
        }

    }

    private fun handleSubmitButtonClick() {

        etPost = requireView().findViewById(R.id.etTweet)

        if (etPost.text?.isBlank() == true) {
            Toast.makeText(requireContext(), "Description cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (signedInUser == null) {
            Toast.makeText(requireContext(), "No signed in user, please wait", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val post = Post(
            signedInUser,
            etPost.text.toString(),
            System.currentTimeMillis()
        )
        firestoreDb.collection("posts").add(post)
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
        etPost.text?.clear()
        findNavController().navigate(R.id.action_createFragment_to_postsFragment)
    }
}