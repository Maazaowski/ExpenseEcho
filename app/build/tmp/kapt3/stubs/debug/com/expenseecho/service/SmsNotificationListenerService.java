package com.expenseecho.service;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.expenseecho.data.repository.TransactionRepository;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0019"}, d2 = {"Lcom/expenseecho/service/SmsNotificationListenerService;", "Landroid/service/notification/NotificationListenerService;", "()V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "transactionRepository", "Lcom/expenseecho/data/repository/TransactionRepository;", "getTransactionRepository", "()Lcom/expenseecho/data/repository/TransactionRepository;", "setTransactionRepository", "(Lcom/expenseecho/data/repository/TransactionRepository;)V", "isSmsApp", "", "packageName", "", "onCreate", "", "onDestroy", "onListenerConnected", "onListenerDisconnected", "onNotificationPosted", "sbn", "Landroid/service/notification/StatusBarNotification;", "onNotificationRemoved", "Companion", "app_debug"})
public final class SmsNotificationListenerService extends android.service.notification.NotificationListenerService {
    @javax.inject.Inject()
    public com.expenseecho.data.repository.TransactionRepository transactionRepository;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SmsNotifListener";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TARGET_ACCOUNT_MASK = "6809";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BANK_SMS_NUMBER = "8287";
    @org.jetbrains.annotations.NotNull()
    public static final com.expenseecho.service.SmsNotificationListenerService.Companion Companion = null;
    
    public SmsNotificationListenerService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.expenseecho.data.repository.TransactionRepository getTransactionRepository() {
        return null;
    }
    
    public final void setTransactionRepository(@org.jetbrains.annotations.NotNull()
    com.expenseecho.data.repository.TransactionRepository p0) {
    }
    
    @java.lang.Override()
    public void onNotificationPosted(@org.jetbrains.annotations.NotNull()
    android.service.notification.StatusBarNotification sbn) {
    }
    
    @java.lang.Override()
    public void onNotificationRemoved(@org.jetbrains.annotations.NotNull()
    android.service.notification.StatusBarNotification sbn) {
    }
    
    private final boolean isSmsApp(java.lang.String packageName) {
        return false;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    public void onListenerConnected() {
    }
    
    @java.lang.Override()
    public void onListenerDisconnected() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/expenseecho/service/SmsNotificationListenerService$Companion;", "", "()V", "BANK_SMS_NUMBER", "", "TAG", "TARGET_ACCOUNT_MASK", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}