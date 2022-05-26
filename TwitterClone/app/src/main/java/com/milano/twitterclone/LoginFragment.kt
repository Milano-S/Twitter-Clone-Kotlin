package com.milano.twitterclone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goPostsActivity()
        }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin = view.findViewById<Button>(R.id.buttonLogin)
        val email = view.findViewById<EditText>(R.id.editTextEmail)
        val password = view.findViewById<EditText>(R.id.editTextPassword)

        buttonLogin.setOnClickListener {
            buttonLogin.isEnabled = true
            if (email.text.toString().isBlank() || password.text.toString().isBlank()) {
                Toast.makeText(requireContext(), "Email or password is blank", Toast.LENGTH_SHORT)
                    .show()
            }
            //Firebase Authentication
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { task ->
                    buttonLogin.isEnabled
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        goPostsActivity()
                    } else {
                        Log.e(TAG, "sign in failed", task.exception)
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun goPostsActivity() {
        findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
    }
}