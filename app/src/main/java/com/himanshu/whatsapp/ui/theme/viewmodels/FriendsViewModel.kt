package com.himanshu.whatsapp.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.data.repository.FriendsRepository
import com.himanshu.whatsapp.data.repository.UserDataStore
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.FriendsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val userDataStore: UserDataStore,
    ) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            _user.value = userDataStore.getUser()
        }
    }

    private val _uiState = MutableStateFlow(FriendsUIState())
    val uiState: StateFlow<FriendsUIState> = _uiState

//    fun getFriends(userId: String) {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true, exception = null) }
//
//            try {
//                val response = friendsRepository.getFriends(userId)
//                _uiState.update {
//                    it.copy(friends = response.body() ?: emptyList())
//                }
//            } catch (e: Exception) {
//                _uiState.update {
//                    it.copy(exception = e)
//                }
//            } finally {
//                _uiState.update {
//                    it.copy(isLoading = false)
//                }
//            }
//        }
//    }

    fun getFriendsConversations(userId: String) {
        viewModelScope.launch {

            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            try {
                val res = friendsRepository.getFriendConversations(userId).body()
                _uiState.update { state ->
                    state.copy(
                        friends = res ?: arrayListOf(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getPendingFriendRequests(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, exception = null) }

            try {
                val response = friendsRepository.getPendingFriendRequests(userId)
                _uiState.update {
                    it.copy(pendingRequests = response.body() ?: emptyList())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(exception = e)
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun acceptFriendRequest(userId: String, friendId: String) {
        viewModelScope.launch {
            try {
                val response = friendsRepository.acceptFriendRequest(userId, friendId)
                println("accepting friend request: $response")
            } catch (e: Exception) {
                println("Exception in accepting friend request: ${e.message}")

            } finally {
            }
        }
    }
}
