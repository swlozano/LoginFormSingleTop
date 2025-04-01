package com.swlozano.creatingloginform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

const val USER_NAME_KEY = "USER_NAME_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"

const val IS_LOGGED_IN = "IS_LOGGED_IN"
const val LOGGED_IN_USERNAME = "LOGGED_IN_USERNAME"

//This is done as an example for simplicity and user/pwd credentials should never be stored in an app
const val USER_NAME_CORRECT_VALUE = "someusername"
const val PASSWORD_CORRECT_VALUE = "somepassword"

class MainActivity : AppCompatActivity() {

    private var isLoggedIn = false
    private var loggedInUser = ""

    private val submitButton: Button
        get() = findViewById(R.id.submit_button)

    private val userName: EditText
        get() = findViewById(R.id.user_name)

    private val password: EditText
        get() = findViewById(R.id.password)

    private val header: TextView
        get() = findViewById(R.id.header)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submitButton.setOnClickListener {
            val userNameForm = userName.text.toString().trim()
            val passwordForm = password.text.toString().trim()
            hideKeyboard()
            if (userNameForm.isNotEmpty() && passwordForm.isNotEmpty() && submitButton.isVisible) {
                Intent(this, MainActivity::class.java).also { loginIntent ->
                    //Add the data
                    loginIntent.putExtra(USER_NAME_KEY, userNameForm)
                    loginIntent.putExtra(PASSWORD_KEY, passwordForm)
                    //Launch
                    startActivity(loginIntent)
                }
            } else {
                val toast = Toast.makeText(
                    this,
                    getString(R.string.login_form_entry_error),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }

        }

    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}