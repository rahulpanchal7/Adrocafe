package com.adrosonic.adrocafe.adrocafe.repository.local.dao

import androidx.room.*
import com.adrosonic.adrocafe.adrocafe.data.User
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flowable<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun findById(id: Int): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>): Completable

    @Delete
    fun delete(user: User)

    @Update
    fun updateUser(vararg user: User): Completable
}