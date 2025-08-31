package com.expenseecho.ui.viewmodel;

import com.expenseecho.data.repository.BudgetRepository;
import com.expenseecho.data.repository.CategoryRepository;
import com.expenseecho.data.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<BudgetRepository> budgetRepositoryProvider;

  public DashboardViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.budgetRepositoryProvider = budgetRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), budgetRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider) {
    return new DashboardViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider, budgetRepositoryProvider);
  }

  public static DashboardViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository, BudgetRepository budgetRepository) {
    return new DashboardViewModel(transactionRepository, categoryRepository, budgetRepository);
  }
}
