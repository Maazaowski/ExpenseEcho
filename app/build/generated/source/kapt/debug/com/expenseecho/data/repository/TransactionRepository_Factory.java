package com.expenseecho.data.repository;

import com.expenseecho.data.dao.RuleDao;
import com.expenseecho.data.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class TransactionRepository_Factory implements Factory<TransactionRepository> {
  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<RuleDao> ruleDaoProvider;

  public TransactionRepository_Factory(Provider<TransactionDao> transactionDaoProvider,
      Provider<RuleDao> ruleDaoProvider) {
    this.transactionDaoProvider = transactionDaoProvider;
    this.ruleDaoProvider = ruleDaoProvider;
  }

  @Override
  public TransactionRepository get() {
    return newInstance(transactionDaoProvider.get(), ruleDaoProvider.get());
  }

  public static TransactionRepository_Factory create(
      Provider<TransactionDao> transactionDaoProvider, Provider<RuleDao> ruleDaoProvider) {
    return new TransactionRepository_Factory(transactionDaoProvider, ruleDaoProvider);
  }

  public static TransactionRepository newInstance(TransactionDao transactionDao, RuleDao ruleDao) {
    return new TransactionRepository(transactionDao, ruleDao);
  }
}
