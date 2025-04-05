package com.himanshu.whatsapp.ui.theme.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.data.repository.UserDataStore
import com.himanshu.whatsapp.di.DeviceIdProvider
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.OnboardingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (
    private val chatRepository: ChatRepository,
    private val userDataStore: UserDataStore,
    private val deviceIdProvider: DeviceIdProvider,
) :  ViewModel() {

    private val _uiState = mutableStateOf(OnboardingUIState())
    val uiState: State<OnboardingUIState> = _uiState

    init {
        getProfilePictures()
    }

    fun saveUser(){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                loading = true
            )
            val user = User(
                deviceId = deviceIdProvider.getDeviceId(),
                name = uiState.value.userName,
                gender = uiState.value.selectedGender,
                photoId = uiState.value.selectedImage,
                status = "online"
            )


            try {
                val res = chatRepository.saveUser(user).body()
                _uiState.value = uiState.value.copy(
                    user = res
                )
                println("User post success $res")

            }catch (e : Exception){
                _uiState.value = uiState.value.copy(
                    error = e
                )
                println("User post failed $e")
            }
        }
    }

    private fun getUser(userId : String){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                loading = true
            )


            try {
                val res = chatRepository.getUser(userId).body()
                _uiState.value = uiState.value.copy(
                    user = res,
                    loading = false

                )
                userDataStore.saveUser(res)
                println("User get success $res")

            }catch (e : Exception){
                _uiState.value = uiState.value.copy(
                    error = e,
                    loading = false
                )
                println("User post failed $e")
            }
        }
    }


    private fun getProfilePictures(){

        viewModelScope.launch {
            try {
                val res = chatRepository.getProfilePictures().body()
                _uiState.value = uiState.value.copy(
                    allPictures = res?: arrayListOf(),
                    filteredPictures = res?.filter { it.contains(uiState.value.selectedGender,true)  } as ArrayList
                )
            }catch (e :Exception){
                _uiState.value = uiState.value.copy(
                    error = e
                )
            }
        }
    }

    fun onImageSelected(url : String){
        _uiState.value = uiState.value.copy(
            selectedImage = url
        )
    }

    fun onGenderSelected(gender : String){
        _uiState.value = uiState.value.copy(
            selectedGender = gender,
            selectedImage = "",
            filteredPictures = uiState.value.allPictures.filter { it.contains(gender,true)  } as ArrayList
        )
    }

    fun onUserNameUpdate(userName : String){
        _uiState.value = uiState.value.copy(
            userName = userName
        )
    }

    fun checkUserStatus(){
        viewModelScope.launch {
            getUser(deviceIdProvider.getDeviceId())
//            val user = userDataStore.getUser()
//
//            if(user == null){
//                val deviceId = deviceIdProvider.getDeviceId()
//                getUser(deviceId)
//            }
//            else{
//                _uiState.value = uiState.value.copy(
//                    user = user
//                )
//            }
        }
    }
}