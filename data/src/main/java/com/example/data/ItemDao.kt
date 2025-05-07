package com.example.data

import androidx.room.*
import com.example.domain.Item
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItems(items: List<Item>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItems(items: List<Item>)

    @Query("SELECT * FROM items WHERE type = :itemType")
    fun getAllItemsType(itemType: Int): Flow<List<Item>>

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("DELETE FROM items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM items")
    fun getCount(): Flow<Int>

}