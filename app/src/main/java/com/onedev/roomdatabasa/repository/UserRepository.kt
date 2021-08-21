package com.onedev.roomdatabasa.repository

import androidx.lifecycle.LiveData
import com.onedev.roomdatabasa.dao.UserDao
import com.onedev.roomdatabasa.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

}