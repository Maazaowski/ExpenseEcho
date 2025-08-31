# ExpenseEcho - Local Android Expense Tracker

ExpenseEcho is a privacy-focused Android expense tracking app that automatically captures bank SMS notifications and categorizes transactions locally on your device.

## Features

### 🔒 Privacy First
- **100% Local Storage**: All data stays on your device, encrypted with SQLCipher
- **No Cloud Dependencies**: Works completely offline
- **SMS Notification Parsing**: Uses NotificationListenerService (Play Store compliant)
- **Encrypted Backups**: Local encrypted export/import functionality

### 💰 Smart Transaction Management
- **Automatic SMS Parsing**: Captures amount, merchant, date, and account info from bank SMS
- **Auto-Categorization**: Rule-based keyword matching (e.g., "KFC" → Food)
- **Account Filtering**: Only processes transactions for your specified account (`****0030-1******6809`)
- **Manual Entry**: Add/edit transactions with quick FAB interface

### 📊 Financial Insights
- **Dashboard**: Monthly income, expenses, net savings, and savings rate
- **Category Breakdown**: Visual spending analysis by category
- **Budget Tracking**: Set and monitor monthly category budgets
- **Debt Management**: Track credit cards and loans with debt avalanche strategy

### 🎯 Modern Tech Stack
- **Kotlin + Jetpack Compose**: Modern Android UI
- **Room + SQLCipher**: Encrypted local database
- **Hilt**: Dependency injection
- **Material 3**: Beautiful, adaptive design
- **WorkManager**: Background backup operations

## Setup Instructions

### 1. Clone and Build
```bash
git clone https://github.com/yourusername/ExpenseEcho.git
cd ExpenseEcho
./gradlew assembleDebug
```

### 2. Enable SMS Notification Access
1. Install the app on your Android device (API 26+)
2. Open the app and go to Settings
3. Tap "Open Notification Settings"
4. Enable notification access for ExpenseEcho
5. Grant permission to read notifications

### 3. Configure Account Filter
The app is pre-configured to filter SMS for account mask `****0030-1******6809`. To change this:
1. Edit `SmsNotificationListenerService.kt`
2. Update the `TARGET_ACCOUNT_MASK` constant
3. Rebuild and reinstall

## How It Works

### SMS Parsing
The app uses `NotificationListenerService` to capture SMS notifications from messaging apps and extracts:
- **Amount**: Supports PKR and Rs. formats with commas
- **Transaction Type**: Credit, Debit, or Funds Transfer
- **Merchant**: Location/merchant name using "at", "from", "to" patterns
- **Account**: Account mask from SMS content
- **Reference**: Transaction reference numbers

### Auto-Categorization
Pre-loaded rules automatically categorize transactions:
- KFC, McDonald's → Food & Dining
- Daraz → Shopping
- Uber, Careem → Transportation
- PSO, Shell → Fuel
- And many more...

### Example SMS Formats Supported
```
PKR 1,500.00 debited from Account ****0030-1******6809 at KFC DHA on 15-Jan-2025. Ref# ABC123

Rs. 250 spent using Debit Card ****0030-1******6809 at Metro Cash & Carry. Available balance PKR 45,000

Credit of PKR 50,000 received in Account ****0030-1******6809 from Salary Transfer. Balance: PKR 95,000
```

## Project Structure

```
app/src/main/java/com/expenseecho/
├── data/
│   ├── entity/          # Room entities (Transaction, Category, etc.)
│   ├── dao/             # Database access objects
│   ├── repository/      # Repository pattern implementation
│   ├── database/        # Database setup and migrations
│   └── converter/       # Type converters for Room
├── service/
│   ├── SmsNotificationListenerService.kt  # SMS capture service
│   └── SmsParser.kt     # SMS parsing logic
├── ui/
│   ├── screen/          # Compose screens (Dashboard, Transactions, etc.)
│   ├── viewmodel/       # ViewModels for UI state management
│   ├── navigation/      # Navigation setup
│   └── theme/           # Material 3 theming
├── di/                  # Hilt dependency injection modules
└── util/                # Utility functions (currency formatting, etc.)
```

## Database Schema

### Core Entities
- **Account**: Bank accounts to track
- **Transaction**: Individual transactions with amount, date, category
- **Category**: Expense categories (Food, Transport, etc.)
- **Budget**: Monthly budget targets per category
- **Debt**: Credit cards and loans for debt tracking
- **Rule**: Auto-categorization rules (keyword → category)

### Security
- SQLCipher encryption with randomly generated passphrase
- Passphrase stored in Android EncryptedSharedPreferences
- No plaintext sensitive data in app storage

## Privacy & Security

### Data Protection
- All financial data encrypted at rest using SQLCipher
- Database passphrase secured with Android Keystore
- No network permissions except for future backup features
- SMS content processed locally, never transmitted

### Permissions
- `BIND_NOTIFICATION_LISTENER_SERVICE`: Required for SMS notification access
- `INTERNET`: Future Google Drive backup (user-initiated only)
- `WRITE_EXTERNAL_STORAGE`: Local backup exports

### Play Store Compliance
- Uses NotificationListenerService instead of READ_SMS permission
- No automatic SMS reading - only processes notifications
- Clear data usage disclosure in Data Safety form
- Local processing only, no server communication

## Roadmap

### Phase 1 (Current - MVP)
- [x] Core database structure with Room + SQLCipher
- [x] SMS notification parsing and filtering
- [x] Basic Compose UI with Dashboard, Transactions, Budget screens
- [x] Auto-categorization rules system
- [x] Material 3 theming

### Phase 2 (Enhanced Features)
- [ ] Advanced budget management (50-20-30 rule, zero-based)
- [ ] Debt avalanche calculator with payoff projections
- [ ] Transaction search and advanced filtering
- [ ] Category management and custom rules UI
- [ ] Local data export/import with encryption

### Phase 3 (Polish & Extensions)
- [ ] Charts and analytics (spending trends, category insights)
- [ ] Home screen widgets for quick transaction entry
- [ ] Quick settings tile for manual entry
- [ ] Dark mode and dynamic color theming
- [ ] Split transaction support
- [ ] Recurring transaction detection

### Phase 4 (Advanced)
- [ ] Google Drive backup integration (user-initiated)
- [ ] Multi-account support
- [ ] Receipt photo attachment (local storage)
- [ ] Financial goal tracking
- [ ] Bill reminder notifications

## Development

### Prerequisites
- Android Studio Hedgehog or later
- Kotlin 1.9.20+
- Android API 26+ (Android 8.0)

### Key Dependencies
```kotlin
// Core Android
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.activity:activity-compose:1.8.2")

// Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.7.5")

// Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("net.sqlcipher:android-database-sqlcipher:4.5.4")

// Dependency Injection
implementation("com.google.dagger:hilt-android:2.48")

// Security
implementation("androidx.security:security-crypto:1.1.0-alpha06")
```

### Building
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- SQLCipher for database encryption
- Jetpack Compose for modern Android UI
- Material Design 3 for beautiful theming
- Pakistani banking SMS formats for parsing patterns

---

**Note**: This app is designed for Pakistani banking SMS formats. The SMS parser can be adapted for other countries by modifying the regex patterns in `SmsParser.kt`.
