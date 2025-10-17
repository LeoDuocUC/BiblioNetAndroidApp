package com.example.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.login.data.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Represents the different states of the login UI
enum class LoginUiState {
    IDLE, SUCCESS, ERROR
}

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState.IDLE)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userDao.findUserByEmail(email)
            if (user != null && user.passwordHash == password) {
                // In a real app, you would hash the password before comparing.
                _loginState.value = LoginUiState.SUCCESS
            } else {
                _loginState.value = LoginUiState.ERROR
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginUiState.IDLE
    }
}

class LoginViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}