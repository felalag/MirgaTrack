# Sign-in and cloud storage: proposal

Status: proposal, not implemented. Research date: 2026-10-14. The project owner's answers of 2026-10-14 are in [Decisions](#decisions).

This document compares ways to add "Continue with Apple" and "Continue with Google" to MigraTrack and to store the data outside the device.
It recommends one option, gives an architecture sketch, lists the features that an account opens, and lists the decisions that are still open.

## Summary

**Recommendation: Supabase (Auth and Postgres) in an EU region, with a small delta sync that the project writes itself on top of the current local SQLite.**

- The app stays local-first. SQLite on the iPhone stays the source of truth for the UI. The cloud is a replica and a backup.
- Sign-in is optional. "Continue without an account" keeps today's behavior. App Store Guideline 5.1.1(v) asks for this when the account adds little.
- Sign in with Apple is necessary when Google sign-in is present (Guideline 4.8).
- The watch does not change. It talks only to the iPhone, and the iPhone is the only cloud client.
- CloudKit is not a good fit: Guideline 5.1.3(ii) says that apps "may not store personal health information in iCloud", and CloudKit has no Android client.
- Cost: $0 during development and the beta, then $25 per month from the public release up to 100,000 monthly active users. The data volume is too small to change the bill.
- No end-to-end encryption now. It blocks most of the features that an account opens. The payload format lets it come later as an option.
- Vendor lock-in is low. The cloud store is plain Postgres. Supabase is open source and can run on your own server.

New dependencies, which need your approval before step 2 of the plan:

| Where | Dependency | What it buys |
|---|---|---|
| `shared/cloud` (new module, iOS and later Android) | `io.github.jan-tennert.supabase:auth-kt` and `postgrest-kt` 3.8.0 (2026-08-26) | Sign-in with an Apple or Google ID token, session refresh, and table access from common Kotlin. |
| `shared/cloud` | Ktor client with the Darwin engine (iOS) and OkHttp (Android), in the version that supabase-kt 3.8.0 needs | The HTTP layer of supabase-kt. |
| `iosApp` (Swift Package) | `GoogleSignIn-iOS` 10.0.0 (2026-09-04) | The native Google account sheet on iOS. It pulls in AppAuth, GTMAppAuth and GTMSessionFetcher. |
| `composeApp` (optional) | `io.github.jan-tennert.supabase:compose-auth` | Ready Compose calls for native Apple sign-in on iOS and Google sign-in on Android and iOS. Without it, a Swift bridge of about 100 lines does the same. |
| Android, later | `androidx.credentials` and `com.google.android.libraries.identity.googleid` | Sign in with Google through Credential Manager. |
| Server | A Supabase project; two Edge Functions in TypeScript | Account deletion with Apple token revocation; later the Apple server-to-server notifications. |

No dependency enters `shared/domain`, `shared/data` or `shared/sync`, so the watch links nothing new.

## Decisions

The project owner answered the first open questions on 2026-10-14.

| Question | Answer | Effect on the design |
|---|---|---|
| Access from more than one device? | Yes. | Sync, not only backup. A second device that signs in downloads everything and skips onboarding. A pull runs at each return to the foreground. |
| Existing users? | None. The app is not released. | No migration of released installs. The first-sign-in merge stays only for users who start without an account. |
| Android? | Planned, but far away. | Sign in with Apple first. Google sign-in on iOS can come at the release or later. Allow both sign-in methods on one account, so a user can move to Android with Google later. |
| Web version? | Unknown. | Keep the payload as readable JSON, so SQL views or a web client can read it later. Do not encrypt it end to end now. |
| End-to-end encryption? | Asked why. | See [End-to-end encryption](#end-to-end-encryption). Not now. |
| Budget? | As low as possible. | See [Budget](#budget): $0 until the public release, then $25 per month. |
| Markets? | All possible markets. | One EU region for everyone, consent for health data, a lawyer check for Russia and China. See [Markets](#markets). |

## What the app has today

The facts below come from the code on 2026-10-14.

- **Storage.** `LocalStore` wraps one SQLDelight database (schema version 2). Tables: `episode`, `medication_intake`, `baseline_profile`, `user_profile`, `export_preferences`, `custom_trigger`, `custom_medication`, `sync_outbox`, `document`.
- **Ids.** Episodes and custom catalog items have random UUIDs (`LogEpisode`, `LocalStore.newId`). Two devices cannot make the same id, so a merge by id is safe. Debug sample data uses fixed ids `sample-N`.
- **Single-row records.** The baseline profile, the user profile and the export preferences are single rows with `id = 1`.
- **Deletes are hard deletes.** `EpisodeRepository.delete` removes the row. No table has a modification time or a deleted flag.
- **Catalog.** Custom triggers and medications can only be added. The app has no edit or delete for them.
- **Watch sync.** `shared/sync` holds the rules. The phone sends a `PhoneContext` (preset, episodes of the last 60 days, received ids). The watch sends a `WatchBatch` from its `sync_outbox`. `PhoneSync.receive` stores watch episodes through `EpisodeRepository.add`.
- **Root navigation.** `App.kt` shows onboarding while `observeBaseline()` emits null, and the main tabs after that.
- **Sign Out.** `SettingsViewModel.signOut` asks for confirmation and calls `LocalDataEraser.eraseAll()`. The baseline becomes null, so onboarding starts again.
- **HealthKit.** The Sleep card reads sleep stages from HealthKit. The app does not store them.
- **Data volume.** One episode is about 0.5 KB as JSON. A user with 8 episodes per month makes about 100 records per year: about 50 KB of payload, or about 150 KB with Postgres overhead and indexes.

## Requirements from Apple and Google

| Rule | Source | Effect on MigraTrack |
|---|---|---|
| An app with a third-party login (Google) must also offer a login that limits data to name and email, lets the user hide the email, and does not track for advertising. | [App Review Guideline 4.8](https://developer.apple.com/app-store/review/guidelines/#login-services) | Sign in with Apple meets this. Show it first or with equal weight. |
| "If your app doesn't include significant account-based features, let people use it without a login." | [Guideline 5.1.1(v)](https://developer.apple.com/app-store/review/guidelines/#data-collection-and-storage) | Keep a "Continue without an account" path, or make the account carry real features (backup, more devices, Android). |
| An app with account creation must offer account deletion in the app. Deactivation is not enough. A website link is acceptable only to finish the process. | [Guideline 5.1.1(v)](https://developer.apple.com/app-store/review/guidelines/#data-collection-and-storage), [Offering account deletion](https://developer.apple.com/support/offering-account-deletion-in-your-app/) | A Delete Account button in Settings that deletes the cloud data and the account record. |
| "Apps that support Sign in with Apple should use the Sign in with Apple REST API to revoke user tokens." | [Offering account deletion](https://developer.apple.com/support/offering-account-deletion-in-your-app/) | Account deletion needs a server step with the `.p8` key. It cannot run in the app. |
| Apps "may not store personal health information in iCloud." | [Guideline 5.1.3(ii)](https://developer.apple.com/app-store/review/guidelines/#health-and-health-research) | Episodes, symptoms and medications are personal health information. CloudKit and iCloud Drive are a review risk. |
| Health data must not go to third parties for advertising or data mining. | [Guideline 5.1.3(i)](https://developer.apple.com/app-store/review/guidelines/#health-and-health-research) | No analytics or ad SDK may see the records. |
| Apple sends the full name only at the first authorization. | [Supabase: Login with Apple](https://supabase.com/docs/guides/auth/social-login/auth-apple) | Save the name in the first sign-in, or keep the name the user types in Settings. |
| On Android, Sign in with Google uses Credential Manager. The older Google Sign-In for Android is in the legacy APIs. | [Android: Sign in with Google](https://developer.android.com/identity/sign-in/credential-manager-siwg) (updated 2026-10-12) | The Android sign-in is native code in `androidMain`. |

## Options

### 1. Identity only, no cloud data

The app signs in, but the data stays on the device.

- **For:** no server data, no data-protection work for health data in the cloud.
- **Against:** the account gives the user nothing. Guideline 5.1.1(v) then asks to let people use the app without a login, so a login screen without a skip is a review risk. Account deletion and Apple token revocation are still necessary, so a server is still necessary.
- **Verdict:** no. It has the cost of an account and none of the value.

### 2. CloudKit private database and Sign in with Apple

- **Cost:** $0 for the developer. Records in the private database "are counted towards the owner's iCloud account storage quota" ([CKDatabase.Scope.private](https://developer.apple.com/documentation/cloudkit/ckdatabase/scope/private)).
- **Offline and conflicts:** `CKSyncEngine` or `NSPersistentCloudKitContainer` give good sync, but only in Swift. The Kotlin store would need a Swift bridge for each change.
- **Identity:** CloudKit uses the iCloud account of the device, not the app account. A user who taps "Continue with Google" still writes to their iCloud. The login screen and the storage do not match.
- **Android:** only CloudKit JS or CloudKit Web Services, with an Apple Account sign-in in a web view ([CloudKit JS](https://developer.apple.com/documentation/cloudkitjs)). This is poor on Android.
- **Privacy:** Guideline 5.1.3(ii) forbids personal health information in iCloud. This is the main blocker.
- **Verdict:** no, because of 5.1.3(ii) and Android.

### 3. Firebase Auth and Cloud Firestore

- **Cost:** Auth is free up to 50,000 MAU. After that, Identity Platform prices apply: $0.0055 per MAU from 50,000 to 100,000, $0.0046 to 1 million, $0.0032 to 10 million ([Identity Platform pricing](https://cloud.google.com/identity-platform/pricing)). The no-cost Spark plan limits Identity Platform to 3,000 daily active users ([Auth limits](https://firebase.google.com/docs/auth/limits)), so production needs the Blaze plan. Firestore Standard in us-central1: $0.03 per 100,000 reads, $0.09 per 100,000 writes, $0.01 per 100,000 deletes. Free each day: 50,000 reads, 20,000 writes, 1 GiB stored ([Firestore pricing](https://cloud.google.com/firestore/pricing), [Firebase pricing](https://firebase.google.com/pricing)). Each query costs at least one read, also when it returns nothing.
- **Offline and conflicts:** Firestore has its own offline cache. MigraTrack already has SQLite, so the app would have two local copies, or it would use Firestore only as a remote and write the sync itself.
- **KMP:** the official SDKs are Swift and Java. The KMP wrapper is `dev.gitlive:firebase-*` 2.7.0 (2026-09-02), a third-party project. On iOS it does not link the Firebase SDK: the Xcode project must add it through Swift Package Manager or CocoaPods ([firebase-kotlin-sdk](https://github.com/GitLiveApp/firebase-kotlin-sdk)). Its own badge shows 23% coverage of the Firestore API.
- **Android:** first-class.
- **Privacy:** Firestore and Identity Platform are in the list of products under the Google Cloud HIPAA BAA ([HIPAA compliance](https://cloud.google.com/security/compliance/hipaa)). Google tells you not to keep health data in Identity Platform user attributes.
- **Lock-in:** high. Firestore is a document store with its own query model and security rules language.
- **Verdict:** a good second choice. It costs more after 50,000 MAU, and the KMP path depends on a third-party wrapper over two native SDKs.

### 4. Supabase Auth and Postgres (recommended)

- **Cost:** the Free plan pauses a project after one week without activity, so production needs Pro: from $25 per month with 100,000 MAU, 8 GB of database per project, then $0.00325 per MAU and $0.125 per GB ([Supabase pricing](https://supabase.com/pricing)). The HIPAA add-on is only for Team (from $599 per month) and Enterprise.
- **Offline and conflicts:** Supabase does not sync. The app writes a small delta sync, see [Sync design](#sync-design). The project already has the same pattern for the watch.
- **KMP:** `supabase-kt` 3.8.0 (2026-08-26) runs in common Kotlin on iOS, Android, JVM and JS ([supabase-kt](https://github.com/supabase-community/supabase-kt)). It is a community project in the `supabase-community` organization, and the Supabase documentation uses it as its Kotlin reference. The `compose-auth` plugin gives native Apple sign-in on iOS, and native Google sign-in on Android (Credential Manager) and on iOS from version 3.3.0. On iOS, Google sign-in needs `GoogleSignIn-iOS` linked in Xcode ([compose-auth](https://github.com/supabase-community/supabase-kt-plugins/tree/main/ComposeAuth)).
- **Sign-in:** the app gets an ID token from Apple or Google and gives it to `signInWithIdToken`. A native-only Apple setup needs no client secret and no 6-month key rotation ([Login with Apple](https://supabase.com/docs/guides/auth/social-login/auth-apple)). Google on iOS needs "Skip nonce check" in the provider settings ([Login with Google](https://supabase.com/docs/guides/auth/social-login/auth-google)).
- **Gaps:** Supabase Auth does not handle Apple server-to-server notifications, and it does not revoke Apple tokens. An Edge Function does both.
- **Privacy:** row level security keeps each user to their own rows. EU regions exist. Confirm encryption at rest and the DPA in the Supabase trust documents before launch.
- **Lock-in:** low. The data is in Postgres tables, `pg_dump` exports them, and Supabase is open source.
- **Verdict:** yes. It is the cheapest managed option up to 100,000 MAU, it fits common Kotlin, and an exit is easy.

### 5. Own backend (Ktor server and Postgres)

- **Cost:** a server and a managed Postgres cost about the same at 1,000 and at 1,000,000 users. The cost is your time.
- **For:** full control. The server shares the Kotlin models. At a very large scale it is the cheapest, because nobody bills per MAU.
- **Against:** you write and run sessions, refresh tokens, rate limits, backups, monitoring, security updates and incident response for health data. Checking an Apple or Google ID token against their public keys is the small part.
- **Verdict:** not now. Because the recommended option is plain Postgres, a move to an own backend later keeps the data and the client sync code. Only the gateway class changes.

### 6. Other options

- **PowerSync on top of Supabase.** A sync engine with a KMP SDK (1.15.2, 2026-09-28) and a SQLDelight integration ([powersync-kotlin](https://github.com/powersync-ja/powersync-kotlin)). It owns the local SQLite file, so `LocalStore` would move to it. Pro costs from $49 per month on top of Supabase ([PowerSync pricing](https://www.powersync.com/pricing)). Too much for about 100 records per user per year. Keep it in mind if the app gets shared data or live updates between many devices.
- **MongoDB Atlas Device Sync (Realm).** MongoDB deprecated it in September 2024 and ended it on 2025-09-30. Do not use.
- **Backup file without an account.** Export an encrypted JSON file to Files, and import it on a new device. No server and no account. This is a good addition for users who choose "Continue without an account". It is not a replacement for sign-in.
- **End-to-end encryption on top of option 4.** The client encrypts each record. The server stores only ciphertext. The key needs recovery: for example in the iCloud Keychain on iOS and Block Store on Android. This blocks any server-side feature, such as a web report. The proposed schema keeps the payload opaque, so this can come later without a new table.

### Comparison

Monthly cost estimates assume 8 episodes per user per month, two app openings per day, and two queries per opening. They exclude taxes and larger compute sizes.

| | Identity only | CloudKit | Firebase | Supabase | Own backend |
|---|---|---|---|---|---|
| 1,000 MAU | server for deletion | $0 | $0 | $25 | ~$20–50 |
| 10,000 MAU | same | $0 | $0–1 | $25 | ~$20–50 |
| 100,000 MAU | same | $0 | ~$280 (Auth $275) | ~$27 | ~$50–100 |
| 1,000,000 MAU | same | $0 | ~$4,470 (Auth $4,415) | ~$2,970 (MAU $2,925) | ~$100–300 |
| Fits current SQLite | yes | Swift bridge | two caches, or own sync | own sync | own sync |
| Android | yes | poor | yes | yes | yes |
| Watch | no change | no change | no change | no change | no change |
| Health data rules | no data in the cloud | 5.1.3(ii) risk | BAA possible | BAA on Team | all on you |
| KMP support | — | none | third-party wrapper | community, common code | Ktor client |
| Lock-in | low | high | high | low | none |

At 1,000,000 MAU both managed options bill mostly for sign-in, not for data. At that point an own auth server, or a self-hosted Supabase, removes most of the bill.

## Features that an account opens

An account with cloud storage opens features in four groups. Some features need no server; the table says which, so that the server does not grow for work that the device can do.

| Feature | What the user gets | Needs the server? | Works with end-to-end encryption? |
|---|---|---|---|
| **Data and devices** | | | |
| Sync between devices | iPhone and iPad show the same diary. A new phone or a reinstall restores everything. | Yes | Yes, if the key syncs (iCloud Keychain) |
| Move between iOS and Android | The diary follows the account, not the phone. | Yes | Hard: the key must cross platforms |
| iPad and Mac app | Insights and the calendar on a large screen. Compose Multiplatform has iPad and desktop targets. | Yes, for the data | Yes |
| Web dashboard and data export | Read the history and download a full export in a browser. | Yes | Only if the browser gets the key |
| **Medical** | | | |
| Share with a doctor | A read-only link to the report or the diary, which expires after some days. The doctor sees the data before the visit. | Yes: a share table and a function that renders the report | Only with the key in the link |
| Caregiver access | A parent follows a child's diary, or a partner helps during an attack. Roles: read, or read and write. | Yes | Hard |
| Clinical formats | Export in FHIR for clinics; diaries for clinical studies. Studies need consent and an ethics board approval (Guideline 5.1.3(iii), (iv)). | Yes | No |
| **Insights** | | | |
| Weather and air pressure | Pressure and weather for each episode, as a possible trigger. The server can fill in the history for old episodes and works the same on Android. | Server or device (WeatherKit on iOS) | Device variant only |
| AI summaries | "What changed this month", a summary before a doctor visit. The server keeps the model API key. Health data goes to the model provider only with consent. | Yes | No |
| Comparison with similar users | "Your frequency against users with the same diagnosis", from anonymized and aggregated data, only with consent. | Yes | No |
| **Business and support** | | | |
| Premium subscription on all platforms | One purchase unlocks premium on iPhone, iPad and later Android. The server stores the entitlement from App Store Server Notifications. | Yes | Yes |
| Support | Support can see whether sync works and can restore deleted data from a backup. | Yes | Only sync status |

These features do **not** need an account. They can come before the cloud, on the device:

- Reminders: "Did the medication help?" two hours after an intake, and a daily check-in, with local notifications.
- A medication-overuse warning: triptans on 10 or more days per month, or simple painkillers on 15 or more days (ICHD-3, 8.2).
- Correlations with HealthKit data: sleep, and the menstrual cycle for menstrual migraine.
- Widgets, Siri and Shortcuts.

### Choices to make now, because they are expensive later

1. **The time zone of each episode.** Store the IANA time zone in the episode payload. A server report, a web page and a reminder need the local day of the attack, and the device time zone changes when the user travels. Old episodes cannot get it back later.
2. **The device of each change.** Store a device id with each record. It helps when you debug sync between devices, and it can show "logged on Apple Watch".
3. **More than one sign-in method on one account.** Supabase can link identities. A user who signs in with Apple on the iPhone can add Google before moving to Android.
4. **A consent table.** Store each consent with its type, text version, time of grant and time of withdrawal: cloud storage of health data, later AI and research. GDPR and US health-data laws ask for proof of consent.
5. **Readable payloads with a version.** Keep JSON with `schema_version`. A server feature or a web client can read it, and end-to-end encryption can still come later.

## End-to-end encryption

With end-to-end encryption, the app encrypts each record on the device with a key that only the user's devices have. The server stores ciphertext. Nobody without the key can read it: not the provider, not the developer, not an attacker who copies the database.

**What it protects against:**

- A leak of the database, or of a backup.
- A wrong row level security policy. With Supabase this is the most frequent mistake: one policy error opens every user's rows.
- Access by the provider's staff or by the developer.
- A legal request to the provider for the data.

It also gives a strong privacy message for a health app. Under GDPR Article 34(3)(a), a breach of data that is unintelligible to the reader needs no notice to each user.

**What it costs:**

- **Key recovery.** If the user loses all devices and the key, the data is lost. A recovery phrase helps, but many users lose it.
- **More devices.** Each new device needs the key. On Apple devices the iCloud Keychain can carry it. Between iOS and Android it needs a recovery phrase or a passkey with the PRF extension.
- **Server features stop.** The server cannot read the data, so it cannot make doctor links, reports, AI summaries, weather back-fill, comparisons or a simple web dashboard. Most rows of the feature table above say "no" or "hard".
- **Support.** Support cannot see or restore the data.
- **Work.** Key management is the hardest part of the design, and a mistake loses user data.

**Recommendation:** not now. Use these cheaper measures:

- Row level security, and tests that one user cannot read another user's rows.
- No health data in auth user attributes, logs or analytics.
- An EU region, TLS, the provider's encryption at rest, and backups.
- A short list of people with access to the production database.

If the market asks for it later, add end-to-end encryption as a per-user option, like Advanced Data Protection in iCloud. The user chooses privacy and loses the server features. Each payload gets an `encrypted` flag, so both kinds of record can live in the same table.

## Architecture sketch

### Modules

```
shared/domain   + account/: AuthService, Account, AuthState, RemoteSyncGateway, RemoteRecord, CloudOutbox
shared/data     + migration 3: cloud_outbox; repositories record each local change; RemoteApplier
shared/sync     + CloudSync: push and pull rules, first sign-in merge, record codecs   (no platform code)
shared/cloud    new: SupabaseAuthService, SupabaseSyncGateway, Keychain session storage (iOS, later Android)
composeApp      + WelcomeScreen, account section in Settings, Sign Out and Delete Account flows
iosApp          + Sign in with Apple entitlement, Google URL scheme, native sign-in bridge
```

`shared/cloud` is the only module with supabase-kt and Ktor. The watch frameworks do not link it.

### Domain abstractions

```kotlin
// shared/domain/.../account/Account.kt
enum class AuthProvider { Apple, Google }

data class Account(val id: String, val email: String?, val provider: AuthProvider)

sealed interface AuthState {
    data object SignedOut : AuthState
    data class SignedIn(val account: Account) : AuthState
}

interface AuthService {
    val state: StateFlow<AuthState>
    suspend fun signIn(provider: AuthProvider): Result<Account>
    /** Ends the session on this device. The cloud data stays. */
    suspend fun signOut()
    /** Deletes the account and all cloud data, and revokes the Apple tokens. */
    suspend fun deleteAccount(): Result<Unit>
}

// shared/domain/.../account/RemoteSync.kt
enum class RecordKind { Episode, Baseline, User, ExportPreferences, CustomTrigger, CustomMedication }

/** One synced record. A null payload marks a deleted record. */
data class RemoteRecord(val kind: RecordKind, val id: String, val modifiedAt: Instant, val payload: String?)

data class PullPage(val records: List<RemoteRecord>, val cursor: Long, val hasMore: Boolean)

interface RemoteSyncGateway {
    suspend fun push(records: List<RemoteRecord>)
    suspend fun pull(after: Long, limit: Int): PullPage
    suspend fun isEmpty(): Boolean
}

/** Local changes that the cloud has not received yet. Separate from SyncOutbox, which serves the watch. */
interface CloudOutbox {
    suspend fun record(kind: RecordKind, id: String, at: Instant)
    suspend fun pending(): List<PendingChange>
    suspend fun remove(changes: Collection<PendingChange>)
}
```

`AuthService.signIn` needs native UI. The iOS implementation calls a Swift bridge, or `compose-auth`, for the ID token, and then `signInWithIdToken`.

### Local schema change (migration 3)

```sql
-- Local changes that the cloud has not confirmed. One row per record; a newer change replaces the older one.
CREATE TABLE cloud_outbox (
    kind TEXT NOT NULL,
    record_id TEXT NOT NULL,
    changed_at INTEGER NOT NULL,
    PRIMARY KEY (kind, record_id)
);
```

- Each repository write also writes `cloud_outbox` in the same transaction. A delete writes it too. When the push reads the record and finds no row, it sends a tombstone.
- The domain tables need no `updated_at` and no deleted flag. The outbox keeps the change time until the push.
- `RemoteApplier` writes pulled records without a `cloud_outbox` row, so a pull does not echo back to the cloud.
- The pull cursor goes in the existing `document` table.
- Records are not written to `cloud_outbox` while the user is signed out. The first sign-in uploads a full snapshot.
- Sample data (`sample-N` ids) stays local. Exclude it from the snapshot and the outbox.

### Cloud schema

One table holds every kind of record. Today only the app reads it, so typed columns give nothing now. The payload is readable JSON: Postgres can query `jsonb`, so a SQL view, a server function or a web client can read it later.

```sql
create sequence public.record_seq;

create table public.records (
  user_id        uuid        not null references auth.users (id) on delete cascade,
  kind           text        not null,
  id             text        not null,
  payload        jsonb,                         -- null: the record was deleted
  schema_version int         not null default 1,
  modified_at    timestamptz not null,          -- device time of the change; last write wins
  device_id      text        not null,          -- the device that made the change
  server_seq     bigint      not null default nextval('public.record_seq'),
  primary key (user_id, kind, id)
);

create index records_pull on public.records (user_id, server_seq);

alter table public.records enable row level security;
create policy records_owner on public.records
  for all using ((select auth.uid()) = user_id) with check ((select auth.uid()) = user_id);

-- The only write path. A change replaces the stored one only when it is newer.
create function public.push_records(changes jsonb) returns void
language plpgsql security invoker as $$
begin
  -- One writer per user at a time, so server_seq order is commit order and a pull cannot skip a row.
  perform pg_advisory_xact_lock(hashtext((select auth.uid())::text));
  insert into public.records (user_id, kind, id, payload, schema_version, modified_at, device_id)
  select (select auth.uid()), c.kind, c.id, c.payload, c.schema_version, c.modified_at, c.device_id
  from jsonb_to_recordset(changes)
       as c(kind text, id text, payload jsonb, schema_version int, modified_at timestamptz, device_id text)
  on conflict (user_id, kind, id) do update
    set payload = excluded.payload,
        schema_version = excluded.schema_version,
        modified_at = excluded.modified_at,
        device_id = excluded.device_id,
        server_seq = nextval('public.record_seq')
    where excluded.modified_at > public.records.modified_at;
end $$;

-- Proof of consent. A new text version needs a new consent.
create table public.consents (
  user_id      uuid        not null references auth.users (id) on delete cascade,
  kind         text        not null,               -- for example 'cloud_health_data'
  text_version text        not null,
  granted_at   timestamptz not null,
  withdrawn_at timestamptz,
  primary key (user_id, kind, text_version)
);
alter table public.consents enable row level security;
create policy consents_owner on public.consents
  for all using ((select auth.uid()) = user_id) with check ((select auth.uid()) = user_id);
```

Payloads are the `@Serializable` record classes in `shared/sync`, like `SyncEpisode` today. An episode carries its medication intakes and the IANA time zone of the device when the attack started. Fields only grow, with defaults, as in the watch payloads. `schema_version` lets an old app skip a record that it cannot read.

Tombstones stay. A deleted record is about 100 bytes, and a device that comes back after months still learns about the delete.

### Sync design

`CloudSync` in `shared/sync` runs one round: push, then pull.

1. **Push.** Read `cloud_outbox`. For each change, read the current local record, or make a tombstone. Send batches of up to 200 records to `push_records`. Remove the sent changes from the outbox only if their `changed_at` did not change during the call.
2. **Pull.** Ask for records with `server_seq` after the cursor, 500 per page. For each record:
   - no pending local change: apply it;
   - a pending local change that is older: apply the remote record and drop the local change;
   - a pending local change that is newer: keep the local record; the next push sends it.
   Then store the new cursor.
3. **Triggers.** App start, return to the foreground, and 3 seconds after the last local change. A failure waits and tries again with backoff. No background task is necessary at first.

With more than one device, a change on the iPhone reaches the iPad when the iPad comes to the foreground. That is enough at first. Two later steps make it faster without new dependencies:

- While the app is open, subscribe to the user's rows with Supabase Realtime. It is in the plan: 200 peak connections on Free, 500 on Pro.
- After a push, an Edge Function sends a silent push notification to the user's other devices, so they pull in the background.

Conflicts use last write wins per record. This is enough for one person with a few devices:

- Episodes and catalog items have UUIDs, so two devices never write the same new record.
- Two edits of the same episode on two offline devices: the later edit wins.
- An edit against a delete: the later change wins.
- The baseline, the user profile and the export preferences are single records. The later save wins as a whole.

The device clock decides "later". The server sequence decides only the pull order. A wrong device clock can let an older edit win. This risk is acceptable for single-user data.

### The watch

Nothing changes on the watch or in `shared/sync` watch rules.

- An episode from the watch arrives through `PhoneSync.receive`, which calls `EpisodeRepository.add`. The repository writes `cloud_outbox`, so the episode goes to the cloud with the next push.
- A pulled change updates the phone database. `PhoneSync.contexts()` observes the repositories and sends a new context to the watch.
- The watch never sees a token or a server address.

### First sign-in

| On this iPhone | In the account | What happens |
|---|---|---|
| No data (new install) | No data | Normal onboarding. The first save goes to the cloud. |
| No data | Data | Pull everything. The baseline arrives, so onboarding is skipped. The watch gets a context. |
| Data (the user started without an account) | No data | Upload a full snapshot of every local record. |
| Data | Data | Ask: "Merge this iPhone's data with your account" (default) or "Replace this iPhone's data with your account". A merge uploads the snapshot and pulls. Episodes join by id. For the single records, the newer one wins. |

The snapshot uses the time of the sign-in as `modified_at` for single records. Ask the user if this is not the intended winner, see the open questions.

### Sign Out

Today Sign Out erases the device data, because it is the only copy. With an account, the cloud keeps a copy:

1. Push the pending changes, with a short timeout.
2. If changes stay unsent, say how many and ask before you continue: they are lost.
3. End the session (`signOut`, which revokes the refresh token) and remove the session from the Keychain.
4. Erase the local data with `LocalDataEraser`, the `cloud_outbox` and the cursor.
5. The phone sends an empty context, so the watch clears its mirror.
6. Show the welcome screen.

For a user without an account, the row keeps today's text and behavior: "Delete Data" with the warning that this iPhone has the only copy.

### Delete Account

1. Settings shows "Delete Account" with what goes away and a confirmation.
2. For an Apple account, the app asks for Sign in with Apple again to get a fresh authorization code.
3. The app calls the `delete-account` Edge Function with its session token and the code.
4. The function exchanges the code for an Apple refresh token and revokes it at `https://appleid.apple.com/auth/revoke`. The `.p8` key lives only in the function secrets.
5. The function deletes the user through the Supabase admin API. `on delete cascade` deletes the records.
6. The app erases the local data, as in Sign Out, and shows a confirmation.

For a Google account, the function revokes the Google token through `https://oauth2.googleapis.com/revoke` if the app keeps one. This is good practice. Apple does not require it.

### Privacy

- **Legal basis.** In the EU, health data is a special category (GDPR Article 9). Ask for explicit consent to store it in the cloud, separate from the Terms. Keep the privacy policy current.
- **Region and contract.** Use one EU region, for example Frankfurt, for all users. Sign the Supabase DPA. See [Markets](#markets).
- **HIPAA.** A consumer app that does not work for a clinic or an insurer is usually not a HIPAA covered entity. In the US, the FTC Health Breach Notification Rule applies to health apps, and some states have health-data laws. Ask a lawyer before launch.
- **Data minimization.** Do not upload HealthKit sleep data. The app does not store it now. Do not put health data in auth user attributes.
- **No third-party SDKs that see records.** No analytics or ads on these screens (Guideline 5.1.3(i)).
- **Encryption.** TLS in transit. At rest, the provider encryption; confirm it in the DPA. End-to-end encryption is a later option, see [End-to-end encryption](#end-to-end-encryption).
- **App Store privacy labels.** Add "Health & Fitness" and "Contact Info: Email Address", linked to the user, not used for tracking.
- **Export.** The clinical report export stays. A JSON export of all records gives data portability (GDPR Article 20).

### Native work in Kotlin Multiplatform

| Task | Where | How |
|---|---|---|
| Sign in with Apple, iOS | Swift bridge or `compose-auth` | `ASAuthorizationAppleIDProvider` with a SHA-256 nonce; returns the ID token and the raw nonce. Entitlement `com.apple.developer.applesignin`. |
| Google, iOS | Swift bridge with `GoogleSignIn-iOS`, or `compose-auth` | `GIDClientID` and the reversed client id URL scheme in Info.plist. |
| Google, Android | `androidMain` | Credential Manager with `GetSignInWithGoogleOption`. |
| Apple, Android | `androidMain` | Supabase OAuth in a Custom Tab. This needs a Services ID and a client secret that you renew every 6 months. |
| Session storage | `iosMain` | A `SessionManager` for supabase-kt on top of the Keychain. Check the default storage of supabase-kt; tokens must not stay in `NSUserDefaults`. |
| Everything else | common Kotlin | Sync rules, codecs, gateway, view models, screens. |

## Budget

| Phase | Supabase plan | Cost per month | Notes |
|---|---|---|---|
| Development | Free, two projects: development and beta | $0 | The Free plan allows two active projects. |
| TestFlight beta | Free | $0 | Free has no automatic backups ([Backups](https://supabase.com/docs/guides/platform/backups)). Each device keeps a full copy, so a cloud loss loses no data. Run `supabase db dump` before each schema change. |
| Public release, up to 100,000 MAU | Pro | $25–27 | Daily backups for 7 days, no pause, 8 GB, 100,000 MAU. |

- A Free project pauses only after a week of low activity ([Project pausing](https://supabase.com/docs/guides/platform/free-project-pausing)). Real users keep it active. The reason to pay at the release is the backups, not the pause.
- Edge Functions are in the plan: 500,000 calls per month on Free, 2 million on Pro.
- Sign in with Apple, Google OAuth and the privacy policy page (for example on GitHub Pages) cost nothing. The Apple Developer Program is already paid.
- Do not pay for PowerSync, the HIPAA add-on or an own server now.
- If $25 per month is too much at the release, Firebase on the Blaze plan costs $0 up to about 50,000 MAU. The difference is $300 per year. The recommendation stays Supabase, for the KMP support and the low lock-in.
- The largest real cost for all markets is one-time: a lawyer's review of the privacy policy, the Terms and the consent texts.

## Markets

The app goes to all markets. One EU region serves all users: GDPR is the strictest common rule set, and a few kilobytes of sync do not need a near server. This section is not legal advice.

| Market | What applies | What the app does |
|---|---|---|
| EU, EEA, United Kingdom | GDPR and UK GDPR. Health data is a special category (Article 9). | Explicit consent for cloud storage, a DPA with Supabase, export and deletion in the app. |
| United States | The FTC Health Breach Notification Rule. State health-data laws, for example the Washington My Health My Data Act: separate consent to collect and to share health data, and a separate health-data privacy policy. | Separate consent screen, a health-data section in the privacy policy, a breach plan. |
| Brazil, Canada, India, Japan, Australia and others | National data-protection laws (LGPD, PIPEDA, DPDP Act, APPI). Consent and a privacy policy usually cover them. | The lawyer checks the texts once. |
| Russia | Federal Law 152-FZ requires that the personal data of Russian citizens is stored in databases in Russia. An EU-only cloud conflicts with this. | Local-only mode in this storefront, or no release there, or a separate cloud in Russia. |
| China mainland | Google sign-in does not work. PIPL treats health data as sensitive and limits its transfer out of China. | Local-only mode in this storefront, or no release there. |

"Continue without an account" makes local-only mode cheap: the app reads the App Store storefront (`Storefront.current.countryCode` in StoreKit) and hides sign-in where the cloud is not available. The same build then goes to every storefront.

Age: set a minimum age in the Terms, for example 16. Children under 13 in the US (COPPA) and under 16 in some EU countries need parental consent. A caregiver feature for children needs its own design.

## Sign-in before cloud storage

Sign-in can come before the cloud storage, and a later update can move the data to the cloud. Use Supabase Auth for it now, without data tables, not a sign-in that exists only on the device.

| | Sign-in only on the device | Supabase Auth now, data later (recommended) |
|---|---|---|
| Where the account lives | The app keeps the provider and the user id (`sub`) in SQLite. | Supabase keeps the account with a stable UUID. The data stays on the device. |
| Server | Still necessary: the Apple token revocation needs the `.p8` key. | One Edge Function for account deletion. |
| Cost | $0 | $0 on the Free plan |
| When storage comes | The user must sign in again: an Apple ID token is valid for minutes, and the app cannot reuse it. | No new sign-in. The update uploads the local data to the existing account. |
| New dependency now | `GoogleSignIn-iOS` | `GoogleSignIn-iOS` and supabase-kt, which the cloud needs later anyway |

The move to the cloud is the case "data on this iPhone, no data in the account" of [First sign-in](#first-sign-in): the update uploads a snapshot of every record. Before the upload, the app must ask for the consent to store health data in the cloud. An account does not give this consent.

Limits of this stage:

- **App Review.** An account that only signs in has no account-based features. Guideline 5.1.1(v) then asks for use without a login. Build the sign-in now, but release it in the App Store only together with sync, or behind "Continue without an account".
- **Sign Out.** The iPhone keeps the only copy of the data. Sign Out keeps today's warning and erases the data.
- **Paid team.** A free Personal Team does not support the Sign in with Apple capability. Development of this step needs the paid Apple Developer Program.
- **The team is part of the user id.** The Apple user id is the same in all apps of one team, and different in another team. Release from the final team. A later transfer to another team needs a user migration with transfer identifiers within 60 days ([Transferring your apps and users to another team](https://developer.apple.com/documentation/signinwithapple/transferring-your-apps-and-users-to-another-team)).
- **The name from Apple.** Apple sends the full name only at the first authorization. Save it in `user_profile.name` at once.

## Plan

Each step ends with `./gradlew check` green and can ship alone.

For [sign-in before cloud storage](#sign-in-before-cloud-storage), do steps 3 and 4 first with a Supabase project that has no data tables, then steps 1 and 2.

1. **Domain and local store.** Add the interfaces, migration 3 with `cloud_outbox`, `RemoteApplier`, and `CloudSync` with a fake gateway. Add the time zone to the episode and a device id. Tests with two in-memory databases, like `SyncTest`: edits, deletes, conflicts, the four first-sign-in cases, the watch path. No new dependencies.
2. **Cloud project.** Create the Supabase projects on the Free plan in the EU. Add the `records` and `consents` tables, the RLS policies, a test that one user cannot read another user's rows, and `push_records` as SQL migrations in the repository. Add `shared/cloud` with supabase-kt and `SupabaseSyncGateway`. Test against a local Supabase (Supabase CLI and Docker).
3. **Sign in with Apple.** Welcome screen with Apple and "Continue without an account", the consent screen, and local-only mode by storefront. `AuthState` drives the root: welcome, onboarding, or main tabs.
4. **Account in Settings.** Account row, sync status, the new Sign Out, Delete Account, and the `delete-account` Edge Function.
5. **Google sign-in on iOS.** Google Cloud project, consent screen, brand verification, `GoogleSignIn-iOS`. Identity linking in Settings, so an Apple account can add Google before a move to Android.
6. **Release work.** Move the production project to Pro. Privacy policy and Terms with a lawyer's review, consent text, privacy labels, the storefront list, review notes with a test account for App Review.
7. **Later.** Realtime or silent push between devices, Android sign-in, the Apple server-to-server notification endpoint (account deletion and consent withdrawal on appleid.apple.com), the features in [Features that an account opens](#features-that-an-account-opens), end-to-end encryption as an option.

## Open questions for the project owner

The questions of 2026-10-14 are answered in [Decisions](#decisions). These stay open:

1. **Is sign-in required, or optional?** The proposal keeps "Continue without an account": Guideline 5.1.1(v) asks for it, and local-only mode for Russia and China needs it. The welcome screen in the design has only two buttons, so it needs a third, quieter action.
2. **Google sign-in at the release, or later?** Sign in with Apple alone is enough on iOS. Google adds one Swift Package and a brand verification at Google.
3. **Russia and China:** local-only mode, no release, or a local cloud?
4. **Who is the legal entity?** A company or a person. It is in the App Store listing, the privacy policy and the DPA.
5. **Do you need email from users?** Sign in with Apple can hide it. If you plan to send email, register the sending domain for the Apple private relay.
6. **For the first-sign-in merge, which single record wins**: the baseline on this iPhone or the one in the account? The default proposal is the account, because it is likely the older and fuller profile.
7. **Which features of [Features that an account opens](#features-that-an-account-opens) come first?** The answer decides the order after the release and the consent texts.

## Sources

All accessed on 2026-10-14.

- Apple. [App Review Guidelines](https://developer.apple.com/app-store/review/guidelines/): 4.8, 5.1.1(v), 5.1.3.
- Apple. [Offering account deletion in your app](https://developer.apple.com/support/offering-account-deletion-in-your-app/).
- Apple. [CKDatabase.Scope.private](https://developer.apple.com/documentation/cloudkit/ckdatabase/scope/private), [CloudKit JS](https://developer.apple.com/documentation/cloudkitjs).
- Google. [Firebase pricing](https://firebase.google.com/pricing), [Firestore pricing](https://cloud.google.com/firestore/pricing), [Identity Platform pricing](https://cloud.google.com/identity-platform/pricing), [Firebase Auth limits](https://firebase.google.com/docs/auth/limits), [Google Cloud HIPAA](https://cloud.google.com/security/compliance/hipaa).
- Google. [Sign in with Google on Android](https://developer.android.com/identity/sign-in/credential-manager-siwg).
- Supabase. [Pricing](https://supabase.com/pricing), [Backups](https://supabase.com/docs/guides/platform/backups), [Project pausing](https://supabase.com/docs/guides/platform/free-project-pausing), [Billing](https://supabase.com/docs/guides/platform/billing-on-supabase), [Login with Apple](https://supabase.com/docs/guides/auth/social-login/auth-apple), [Login with Google](https://supabase.com/docs/guides/auth/social-login/auth-google), [Platform security](https://supabase.com/docs/guides/security/platform-security).
- GitHub releases: [supabase-kt](https://github.com/supabase-community/supabase-kt) 3.8.0, [compose-auth](https://github.com/supabase-community/supabase-kt-plugins/tree/main/ComposeAuth), [firebase-kotlin-sdk](https://github.com/GitLiveApp/firebase-kotlin-sdk) 2.7.0, [powersync-kotlin](https://github.com/powersync-ja/powersync-kotlin) 1.15.2, [GoogleSignIn-iOS](https://github.com/google/GoogleSignIn-iOS) 10.0.0.
- PowerSync. [Pricing](https://www.powersync.com/pricing).

Not checked against a primary source in this research: the Apple token revocation endpoint parameters (the Apple page needs JavaScript), the Supabase encryption at rest and DPA terms, and the end date of Atlas Device Sync. Confirm them before implementation.
