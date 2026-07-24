# CLAUDE.md — Pathok প্রজেক্ট নির্দেশনা

## প্রজেক্ট
Pathok — বাংলা ই-বুক/লাইব্রেরি Android অ্যাপ। মূল টার্গেট: `app/src/main/assets/index.html`-এ থাকা WebView অ্যাপের ডিজাইন ও ফিচার হুবহু রেখে সম্পূর্ণ **নেটিভ Kotlin + Jetpack Compose** অ্যাপে রূপান্তর করা।

**গুরুত্বপূর্ণ:** পুরনো `index.html` (assets ফোল্ডারে) মুছে ফেলো না — এটাই ডিজাইন/লজিকের একমাত্র রেফারেন্স সোর্স। নতুন প্রতিটা স্ক্রিন বানানোর আগে এই ফাইলে সংশ্লিষ্ট অংশ (CSS + JS) পড়ে দেখো।

## টেক স্ট্যাক
- Kotlin, Jetpack Compose, Navigation-Compose
- Firebase Auth (KTX) + Firestore (KTX) — official Android SDK (JS SDK না)
- Coil (ইমেজ লোডিং), DataStore (লোকাল প্রেফারেন্স/last-read position)
- ন্যূনতম SDK / target SDK: `app/build.gradle`-এ যা আছে তা বজায় রাখো, পরিবর্তনের দরকার হলে আগে জিজ্ঞেস করো

## ডিজাইন নিয়ম
- রং, ফন্ট (Hind/Kalpurush/Noto — `assets/fonts/`-এ আছে), স্পেসিং, radius — সব `index.html`-এর CSS থেকে হুবহু তুলে `ui/theme/`-এ token আকারে রাখো
- বাংলা UI টেক্সট অবিকল রাখো (হোম, পড়া, লাইব্রেরি, প্রোফাইল, সেটিং ইত্যাদি)
- Bottom nav-এর animated pill স্টাইল রিক্রিয়েট করতে হবে Compose animation দিয়ে

## Firestore স্কিমা (বর্তমান, অপরিবর্তিত রাখো)
- `contents` — বই (প্রতিটার sub-collection `chapters`, `order` ফিল্ড দিয়ে সাজানো)
- `authors` — লেখক তালিকা
- `app_config/settings` — অ্যাপ কনফিগ ডকুমেন্ট

## কোডিং কনভেনশন
- প্যাকেজ: `com.pathok.app`
- ফোল্ডার স্ট্রাকচার: `ui/screens/<feature>/`, `ui/theme/`, `data/model/`, `data/repository/`
- প্রতিটা স্ক্রিন আলাদা ফাইলে, ViewModel দিয়ে state হ্যান্ডল

## যা করা যাবে না
- ডিজাইন/লেআউট নিজে থেকে বদলানো যাবে না — শুধু রূপান্তর, রিডিজাইন না
- `google-services.json`, keystore, বা কোনো secret কমিট করা যাবে না (`.gitignore`-এ আছে)
- নতুন কোনো বড় dependency/লাইব্রেরি যোগ করার আগে জানিয়ে দিতে হবে

## ওয়ার্কফ্লো
- প্রতিটা কাজের অংশ শেষ হলে সাথে সাথে git commit করো (ছোট, বর্ণনামূলক কমিট মেসেজ)
- প্রতি সেশনের শেষে `PROGRESS.md` আপডেট করো: কী শেষ হলো, পরের নির্দিষ্ট পদক্ষেপ কী
