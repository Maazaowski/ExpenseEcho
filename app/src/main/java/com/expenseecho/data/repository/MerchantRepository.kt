package com.expenseecho.data.repository

import com.expenseecho.data.dao.MerchantDao
import com.expenseecho.data.entity.Merchant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MerchantRepository @Inject constructor(
    private val merchantDao: MerchantDao
) {
    
    fun getAllMerchants(): Flow<List<Merchant>> = merchantDao.getAll()
    
    fun getUncategorizedMerchants(): Flow<List<Merchant>> = merchantDao.getUncategorized()
    
    fun getMerchantsByCategory(categoryId: String): Flow<List<Merchant>> = 
        merchantDao.getByCategory(categoryId)
    
    suspend fun getMerchantById(id: String): Merchant? = merchantDao.getById(id)
    
    suspend fun getMerchantByName(name: String): Merchant? = merchantDao.getByName(name)
    
    suspend fun getMerchantByNormalizedName(normalizedName: String): Merchant? = 
        merchantDao.getByNormalizedName(normalizedName)
    
    suspend fun searchMerchants(searchTerm: String): List<Merchant> = 
        merchantDao.searchByNormalizedName(searchTerm.lowercase())
    
    suspend fun insertMerchant(merchant: Merchant) {
        merchantDao.insert(merchant)
    }
    
    suspend fun updateMerchant(merchant: Merchant) {
        merchantDao.update(merchant.copy(updatedAt = System.currentTimeMillis()))
    }
    
    suspend fun deleteMerchant(merchant: Merchant) {
        merchantDao.delete(merchant)
    }
    
    suspend fun deleteAllMerchants() {
        merchantDao.deleteAll()
    }
    
    suspend fun updateMerchantCategory(merchantId: String, categoryId: String?) {
        merchantDao.updateCategory(merchantId, categoryId, System.currentTimeMillis())
    }
    
    suspend fun incrementTransactionCount(merchantId: String) {
        merchantDao.incrementTransactionCount(merchantId, System.currentTimeMillis())
    }
    
    suspend fun getUncategorizedCount(): Int = merchantDao.getUncategorizedCount()
    
    suspend fun getTotalMerchantCount(): Int = merchantDao.getTotalCount()
    
    /**
     * Find or create a merchant based on the merchant name from SMS
     * Returns the merchant ID and category ID (if mapped)
     */
    suspend fun findOrCreateMerchant(merchantName: String): Pair<String, String?> {
        val normalizedName = Merchant.normalizeName(merchantName)
        
        // First try to find by exact name
        var merchant = merchantDao.getByName(merchantName)
        
        // If not found, try by normalized name
        if (merchant == null) {
            merchant = merchantDao.getByNormalizedName(normalizedName)
        }
        
        // If still not found, create a new merchant
        if (merchant == null) {
            merchant = Merchant.createFromName(merchantName)
            merchantDao.insert(merchant)
        } else {
            // Update transaction count and last seen time
            merchantDao.incrementTransactionCount(merchant.id, System.currentTimeMillis())
        }
        
        return Pair(merchant.id, merchant.categoryId)
    }
    
    /**
     * Find the best matching merchant for categorization
     * Uses fuzzy matching on normalized names
     */
    suspend fun findMerchantForCategorization(merchantName: String): Merchant? {
        val normalizedName = Merchant.normalizeName(merchantName)
        
        // Try exact normalized match first
        var merchant = merchantDao.getByNormalizedName(normalizedName)
        if (merchant != null) return merchant
        
        // Try fuzzy search
        val searchResults = merchantDao.searchByNormalizedName(normalizedName)
        return searchResults.firstOrNull { it.categoryId != null }
    }
}
