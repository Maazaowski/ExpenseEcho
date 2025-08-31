package com.expenseecho.data.repository;

import com.expenseecho.data.dao.RuleDao;
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
public final class RuleRepository_Factory implements Factory<RuleRepository> {
  private final Provider<RuleDao> ruleDaoProvider;

  public RuleRepository_Factory(Provider<RuleDao> ruleDaoProvider) {
    this.ruleDaoProvider = ruleDaoProvider;
  }

  @Override
  public RuleRepository get() {
    return newInstance(ruleDaoProvider.get());
  }

  public static RuleRepository_Factory create(Provider<RuleDao> ruleDaoProvider) {
    return new RuleRepository_Factory(ruleDaoProvider);
  }

  public static RuleRepository newInstance(RuleDao ruleDao) {
    return new RuleRepository(ruleDao);
  }
}
