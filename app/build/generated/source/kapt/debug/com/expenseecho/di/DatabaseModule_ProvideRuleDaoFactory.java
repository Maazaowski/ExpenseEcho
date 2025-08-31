package com.expenseecho.di;

import com.expenseecho.data.dao.RuleDao;
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
public final class DatabaseModule_ProvideRuleDaoFactory implements Factory<RuleDao> {
  private final Provider<ExpenseEchoDatabase> databaseProvider;

  public DatabaseModule_ProvideRuleDaoFactory(Provider<ExpenseEchoDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RuleDao get() {
    return provideRuleDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideRuleDaoFactory create(
      Provider<ExpenseEchoDatabase> databaseProvider) {
    return new DatabaseModule_ProvideRuleDaoFactory(databaseProvider);
  }

  public static RuleDao provideRuleDao(ExpenseEchoDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRuleDao(database));
  }
}
