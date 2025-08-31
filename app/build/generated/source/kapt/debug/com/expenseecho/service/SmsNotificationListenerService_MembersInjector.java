package com.expenseecho.service;

import com.expenseecho.data.repository.TransactionRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SmsNotificationListenerService_MembersInjector implements MembersInjector<SmsNotificationListenerService> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public SmsNotificationListenerService_MembersInjector(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  public static MembersInjector<SmsNotificationListenerService> create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new SmsNotificationListenerService_MembersInjector(transactionRepositoryProvider);
  }

  @Override
  public void injectMembers(SmsNotificationListenerService instance) {
    injectTransactionRepository(instance, transactionRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.expenseecho.service.SmsNotificationListenerService.transactionRepository")
  public static void injectTransactionRepository(SmsNotificationListenerService instance,
      TransactionRepository transactionRepository) {
    instance.transactionRepository = transactionRepository;
  }
}
