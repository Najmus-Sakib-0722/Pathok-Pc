# PROGRESS

## এখন পর্যন্ত
- [x] পুরনো WebView প্রজেক্ট GitHub-এ আপলোড করা হয়েছে
- [x] Phase 0 (আংশিক) — build.gradle-এ Jetpack Compose + Firebase (Auth, Firestore) dependency যোগ করা হয়েছে
- [x] minSdkVersion 21 → 23 করা হয়েছে (Firebase Auth 23.1.0-এর জন্য দরকার ছিল)
- [x] Sync + build সফল হয়েছে
- [x] Design tokens (রং/ফন্ট/স্পেসিং) — `index.html`-এর CSS থেকে Theme.kt বানানো — **এটাই পরের কাজ**
- [ ] ডেটা মডেল (Book, Chapter, Author, UserProfile)
- [ ] Phase 1 — Auth (Login/Signup/Google Sign-In)
- [ ] Phase 2 — Home
- [ ] Phase 3 — Book Detail + Reader
- [ ] Phase 4 — Library
- [ ] Phase 5 — Profile
- [ ] Phase 6 — Settings + Bottom nav
- [ ] Phase 7 — Polish/QA

## পরের নির্দিষ্ট পদক্ষেপ
Design tokens বানানো: `app/src/main/assets/index.html`-এর CSS থেকে রং/ফন্ট/স্পেসিং বের করে `app/src/main/kotlin/com/pathok/app/ui/theme/` ফোল্ডারে `Color.kt`, `Type.kt`, `Theme.kt` বানানো।

## সিদ্ধান্ত/নোট
- minSdk 23 (Android 6.0+) সাপোর্ট করা হবে, এর নিচে না
- Compose BOM: 2024.12.01, Firebase BOM: 33.7.0
- পুরনো WebView কোড (`assets/index.html`, `MainActivity.kt`) মুছে ফেলা হয়নি — এটাই ডিজাইন/লজিক রেফারেন্স, রূপান্তর শেষ না হওয়া পর্যন্ত রেখে দেওয়া হবে
