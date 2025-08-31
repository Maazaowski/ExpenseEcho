package com.expenseecho.data.repository

import com.expenseecho.data.dao.RuleDao
import com.expenseecho.data.entity.Rule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuleRepository @Inject constructor(
    private val ruleDao: RuleDao
) {
    
    fun getAllRules(): Flow<List<Rule>> = ruleDao.getAll()
    
    fun getActiveRules(): Flow<List<Rule>> = ruleDao.getAllActive()
    
    fun getRulesByCategory(categoryId: String): Flow<List<Rule>> = ruleDao.getByCategory(categoryId)
    
    suspend fun getRuleById(id: String): Rule? = ruleDao.getById(id)
    
    suspend fun getRuleByKeyword(keyword: String): Rule? = ruleDao.getByKeyword(keyword)
    
    suspend fun insertRule(rule: Rule) {
        ruleDao.insert(rule)
    }
    
    suspend fun insertOrUpdateRule(keyword: String, categoryId: String, priority: Int = 0) {
        val normalizedKeyword = keyword.lowercase().trim()
        val id = normalizedKeyword.replace(Regex("[^a-z0-9]"), "_")
        
        val existing = getRuleByKeyword(normalizedKeyword)
        
        if (existing != null) {
            val updated = existing.copy(
                categoryId = categoryId,
                priority = priority
            )
            ruleDao.update(updated)
        } else {
            val newRule = Rule(
                id = id,
                keyword = normalizedKeyword,
                categoryId = categoryId,
                priority = priority
            )
            ruleDao.insert(newRule)
        }
    }
    
    suspend fun updateRule(rule: Rule) {
        ruleDao.update(rule)
    }
    
    suspend fun deleteRule(rule: Rule) {
        ruleDao.delete(rule)
    }
    
    suspend fun deleteRuleById(id: String) {
        ruleDao.deleteById(id)
    }
    
    suspend fun deactivateRule(id: String) {
        ruleDao.deactivate(id)
    }
}
