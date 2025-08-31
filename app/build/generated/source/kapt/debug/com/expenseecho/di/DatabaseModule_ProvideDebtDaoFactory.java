package com.expenseecho.di;

import com.expenseecho.data.dao.DebtDao;
import com.expenseecho.data.database.ExpenseEchoDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideDebtDaoFactory implements Factory<DebtDao> {
  private final Provider<ExpenseEchoDatabase> databaseProvider;

  public DatabaseModule_ProvideDebtDaoFactory(Provider<ExpenseEchoDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DebtDao get() {
    return provideDebtDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideDebtDaoFactory create(
      Provider<ExpenseEchoDatabase> databaseProvider) {
    return new DatabaseModule_ProvideDebtDaoFactory(databaseProvider);
  }

  public static DebtDao provideDebtDao(ExpenseEchoDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDebtDao(database));
  }
}
