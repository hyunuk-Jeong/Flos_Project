package com.hyunuk.flos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunuk.flos.model.UserInfoData
import com.hyunuk.flos.room.dao.UserDao
import com.hyunuk.flos.room.entity.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomUserViewModel @Inject constructor(
    private val dao:UserDao
):ViewModel() {
    val userData: StateFlow<UserData> = dao.getUser()
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(), UserData(userInfo = UserInfoData()))


    fun addUser(user: UserData) {
        viewModelScope.launch {
            dao.insertUser(user)
        }
    }

    fun updateUser(user:UserData){
        viewModelScope.launch {
            dao.updateUser(user)
        }
    }

    fun deleteUser(user: UserData) {
        viewModelScope.launch {
            dao.deleteUser(user)
        }
    }
}