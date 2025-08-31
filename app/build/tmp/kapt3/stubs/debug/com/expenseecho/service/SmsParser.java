package com.expenseecho.service;

import com.expenseecho.data.entity.Transaction;
import java.time.LocalDate;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001fB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0017\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u00a2\u0006\u0002\u0010\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u0015J\u0006\u0010\u001d\u001a\u00020\u001eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/expenseecho/service/SmsParser;", "", "()V", "accountRx", "Lkotlin/text/Regex;", "accountRx2", "amountRxs", "", "chargedRx", "fromPersonRx", "fundsTransferRx", "merchantAtRx", "receivedRx", "refRx", "sentRx", "spaceFix", "toPersonRx", "txIdRx", "extractAmount", "", "text", "", "(Ljava/lang/String;)Ljava/lang/Long;", "extractMerchantFromCharged", "extractPersonFrom", "extractPersonTo", "parse", "Lcom/expenseecho/service/SmsParser$ParsedSms;", "rawInput", "testParse", "", "ParsedSms", "app_debug"})
public final class SmsParser {
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex spaceFix = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.text.Regex> amountRxs = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex txIdRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex refRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex chargedRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex receivedRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex sentRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex fundsTransferRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex accountRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex accountRx2 = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex merchantAtRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex fromPersonRx = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex toPersonRx = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.expenseecho.service.SmsParser INSTANCE = null;
    
    private SmsParser() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.expenseecho.service.SmsParser.ParsedSms parse(@org.jetbrains.annotations.NotNull()
    java.lang.String rawInput) {
        return null;
    }
    
    private final java.lang.Long extractAmount(java.lang.String text) {
        return null;
    }
    
    /**
     * Extract merchant from "charged" transaction (debit card purchases)
     * Example: "at FOOD PANDA KARACHI PK."
     */
    private final java.lang.String extractMerchantFromCharged(java.lang.String text) {
        return null;
    }
    
    /**
     * Extract person name from "received" transaction
     * Example: "from SYED MUHAMMAD MAAZ BAF"
     */
    private final java.lang.String extractPersonFrom(java.lang.String text) {
        return null;
    }
    
    /**
     * Extract person name from "sent" transaction  
     * Example: "to MUHAMMAD ZAHID TMB"
     */
    private final java.lang.String extractPersonTo(java.lang.String text) {
        return null;
    }
    
    /**
     * Test method to validate SMS parsing with your bank's actual formats
     */
    public final void testParse() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Ba\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003Jw\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020,H\u00d6\u0001J\t\u0010-\u001a\u00020\u0007H\u00d6\u0001J\u0010\u0010.\u001a\u00020/2\b\b\u0002\u00100\u001a\u00020\u0007R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u00061"}, d2 = {"Lcom/expenseecho/service/SmsParser$ParsedSms;", "", "date", "Ljava/time/LocalDate;", "amount", "", "type", "", "paymentMethod", "categoryId", "accountMask", "merchant", "description", "reference", "raw", "(Ljava/time/LocalDate;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAccountMask", "()Ljava/lang/String;", "getAmount", "()J", "getCategoryId", "getDate", "()Ljava/time/LocalDate;", "getDescription", "getMerchant", "getPaymentMethod", "getRaw", "getReference", "getType", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "toTransaction", "Lcom/expenseecho/data/entity/Transaction;", "accountId", "app_debug"})
    public static final class ParsedSms {
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalDate date = null;
        private final long amount = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String type = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String paymentMethod = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String categoryId = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String accountMask = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String merchant = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String description = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String reference = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String raw = null;
        
        public ParsedSms(@org.jetbrains.annotations.NotNull()
        java.time.LocalDate date, long amount, @org.jetbrains.annotations.NotNull()
        java.lang.String type, @org.jetbrains.annotations.NotNull()
        java.lang.String paymentMethod, @org.jetbrains.annotations.Nullable()
        java.lang.String categoryId, @org.jetbrains.annotations.Nullable()
        java.lang.String accountMask, @org.jetbrains.annotations.Nullable()
        java.lang.String merchant, @org.jetbrains.annotations.Nullable()
        java.lang.String description, @org.jetbrains.annotations.Nullable()
        java.lang.String reference, @org.jetbrains.annotations.NotNull()
        java.lang.String raw) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate getDate() {
            return null;
        }
        
        public final long getAmount() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getType() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPaymentMethod() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getCategoryId() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getAccountMask() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getMerchant() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getReference() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getRaw() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.expenseecho.data.entity.Transaction toTransaction(@org.jetbrains.annotations.NotNull()
        java.lang.String accountId) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component10() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component8() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.expenseecho.service.SmsParser.ParsedSms copy(@org.jetbrains.annotations.NotNull()
        java.time.LocalDate date, long amount, @org.jetbrains.annotations.NotNull()
        java.lang.String type, @org.jetbrains.annotations.NotNull()
        java.lang.String paymentMethod, @org.jetbrains.annotations.Nullable()
        java.lang.String categoryId, @org.jetbrains.annotations.Nullable()
        java.lang.String accountMask, @org.jetbrains.annotations.Nullable()
        java.lang.String merchant, @org.jetbrains.annotations.Nullable()
        java.lang.String description, @org.jetbrains.annotations.Nullable()
        java.lang.String reference, @org.jetbrains.annotations.NotNull()
        java.lang.String raw) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}