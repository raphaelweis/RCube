package com.raphaelweis.rcube.ui.destinations.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphaelweis.rcube.data.PreferencesHelper
import com.raphaelweis.rcube.data.entities.User
import com.raphaelweis.rcube.data.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val usersRepository: UsersRepository, context: Context) :
    ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _birthdate = MutableStateFlow("")
    val birthdate: StateFlow<String> = _birthdate

    private val preferencesHelper = PreferencesHelper(context)

    init {
        val userId = preferencesHelper.getUserId()
        viewModelScope.launch(Dispatchers.IO) {
            userId?.let { loadUser(userId) }
        }
    }

    private val _showActivateDialog = MutableStateFlow(false)
    val showActivateDialog: StateFlow<Boolean> = _showActivateDialog

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog

    fun setShowActivateDialog(show: Boolean) {
        _showActivateDialog.value = show
    }

    fun setShowDeleteDialog(show: Boolean) {
        _showDeleteDialog.value = show
    }

    private suspend fun loadUser(userId: Long) {
        try {
            usersRepository.getUserStream(userId).collect { fetchedUser ->
                _user.value = fetchedUser
                _username.value = fetchedUser?.username ?: ""
                _birthdate.value = fetchedUser?.birthdate ?: ""
            }
        } catch (e: Exception) {
            _user.value = null
            _username.value = ""
            _birthdate.value = ""

            preferencesHelper.deleteUserId()
        }
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateBirthdate(newBirthdate: String) {
        _birthdate.value = newBirthdate
    }

    suspend fun saveUser(): Boolean {
        return try {
            val currentUser = _user.value
            if (currentUser != null) {
                val updatedUser = currentUser.copy(
                    username = _username.value,
                    birthdate = _birthdate.value
                )
                usersRepository.updateUser(updatedUser)
                _user.value = updatedUser
            } else {
                val newUser = User(
                    username = _username.value,
                    birthdate = _birthdate.value
                )
                val id = usersRepository.insertUser(newUser)
                preferencesHelper.saveUserId(id)
                val createdUser = newUser.copy(id = id)
                _user.value = createdUser
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = _user.value

            if (currentUser != null) {
                usersRepository.deleteUser(currentUser.id)
                preferencesHelper.deleteUserId()

                _user.value = null
                _username.value = ""
                _birthdate.value = ""
            }
        }
    }
}
