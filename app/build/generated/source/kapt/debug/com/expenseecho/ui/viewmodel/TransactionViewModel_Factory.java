package com.expenseecho.ui.viewmodel;

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
public final class TransactionViewModel_Factory implements Factory<TransactionViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public TransactionViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public TransactionViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get());
  }

  public static TransactionViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new TransactionViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider);
  }

  public static TransactionViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository) {
    return new TransactionViewModel(transactionRepository, categoryRepository);
  }
}
