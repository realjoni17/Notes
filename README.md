# 📝 Notes App

A modern, full-featured notes application built with **Jetpack Compose**, **Room**, **Firebase Authentication**, and **Google Drive** integration. It supports local note management and cloud backup/restore via Google Drive's `appDataFolder`.

---

## 🚀 Features

- ✅ Google Sign-In (Firebase Authentication)
- 🗒️ Create, view, search, and delete notes locally (Room DB)
- ☁️ Backup and restore notes to/from **Google Drive**
- 🔁 Real-time updates using Kotlin Flows
- 🧼 Clean Architecture with Use Cases and Hilt DI
- 📱 Beautiful UI with Jetpack Compose

---

## 🧱 Tech Stack

| Layer              | Tech                         |
|-------------------|------------------------------|
| Language           | Kotlin                       |
| UI Framework       | Jetpack Compose              |
| DI                 | Hilt + KSP                   |
| Local DB           | Room 2.6.1                   |
| Auth               | Firebase Auth (Google)       |
| Cloud Storage      | Google Drive API             |
| Async              | Coroutines + Flows           |
| Gradle Tools       | KSP                          |
| Java Version       | 17                           |
| Min SDK            | 24 (Android 7.0)             |
| Target SDK         | 34 (Android 14)              |

---

---

## ⚙️ Setup Instructions

### ✅ Prerequisites

- Android Studio **Koala** or later
- JDK 17
- Firebase project (Auth enabled)
- Google Cloud project (Drive API + OAuth client)
- Device/Emulator with **API 24+**

---

### 🔧 Installation

#### 1. Clone the Repo

```bash
git clone https://github.com/realjoni17/notes.git
cd notes


