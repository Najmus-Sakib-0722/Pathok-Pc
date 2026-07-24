# PROGRESS

## এখন পর্যন্ত
- [x] পুরনো WebView প্রজেক্ট GitHub-এ আপলোড করা হয়েছে
- [x] Phase 0 — build.gradle-এ Jetpack Compose + Firebase (Auth, Firestore) dependency
- [x] minSdkVersion 21 → 23
- [x] Design tokens — Color.kt, Type.kt, Theme.kt (৫টা কালার স্কিম: Sepia/Light/Dark/Night/Forest), প্যাকেজ com.pathok.app.theme
- [x] ডেটা মডেল — Book.kt, Chapter.kt, Author.kt, UserProfile.kt (প্যাকেজ com.pathok.app.data.model)
- [x] Build সফল — **Phase 0 সম্পূর্ণ**
- [ ] Phase 1 — Auth (Login/Signup/Google Sign-In) — **এটাই পরের কাজ**
- [ ] Phase 2 — Home
- [ ] Phase 3 — Book Detail + Reader
- [ ] Phase 4 — Library
- [ ] Phase 5 — Profile
- [ ] Phase 6 — Settings + Bottom nav
- [ ] Phase 7 — Polish/QA
it
## পরের নির্দিষ্ট পদক্ষেপ
Phase 1 শুরু: Auth স্ক্রিন (Login/Signup ট্যাব + Google Sign-In) বানানো, MainActivity-কে WebView-এর বদলে Compose UI দেখানো শুরু করা (auth state অনুযায়ী রাউটিং)।

## সিদ্ধান্ত/নোট
- minSdk 23, Compose BOM 2024.12.01, Firebase BOM 33.7.0
- Chapter document-এ `order`/`title` ফিল্ড অনিশ্চিত (কিছু ডকুমেন্টে নাও থাকতে পারে) — Chapter.kt-এ default/nullable রাখা হয়েছে, reader বানানোর সময় আবার চেক করতে হবে
- পুরনো WebView কোড (assets/index.html, MainActivity.kt) এখনো মোছা হয়নি — রেফারেন্স হিসেবে রাখা আছে