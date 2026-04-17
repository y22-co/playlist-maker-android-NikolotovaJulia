# 🎵 Playlist Maker 🎵
[![Made with ❤️ by Playlist Maker](https://img.shields.io/badge/Made%20with%20❤️%20by-Xlazzzy-red)](https://github.com/y22-co)  
[![Open Source Love](https://img.shields.io/badge/Open%20Source-Yes-blueviolet)](https://github.com/y22-co/playlist-maker-android-NikolotovaJulia)  
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)  

**Playlist Maker** — Android-приложение для создания и управления музыкальными плейлистами.  
Создано на **Kotlin** с использованием **Jetpack Compose** и архитектуры **MVVM**.

---

## 📱 Основные возможности
- 🔍 Поиск треков и исполнителей
- ❤️ Добавление треков в избранное
- 🎧 Создание и редактирование плейлистов

---

## 🧩 Технические характеристики
| Компонент            | Версия / Настройка                                |
| -------------------- | ------------------------------------------------- |
| **Android Studio**   | Hedgehog / Jellyfish или новее                    |
| **Gradle Plugin**    | 8.2+                                              |
| **Kotlin**           | 1.9+                                              |
| **JDK / JVM Target** | 11                                                |
| **MinSdk**           | 29                                                |
| **TargetSdk**        | 36                                                |
| **CompileSdk**       | 36                                                |
| **UI Framework**     | Jetpack Compose + Material                      |
| **Архитектура**      | MVVM (Model-View-ViewModel)                       |

---

## ⚙️ Как собрать и запустить проект

1. Установите:
- [Android Studio](https://developer.android.com/studio) версии Hedgehog или выше  
- JDK 11  
- Android SDK API 36
2. Клонируйте репозиторий:
   ```sh
   git clone https://github.com/y22-co/playlist-maker-android-NikolotovaJulia.git
   ```
3. Откройте проект в Android Studio:  
   **File → Open → выберите папку проекта**
4. Дождитесь синхронизации Gradle.
5. Запустите проект:  
   **Run ▶️** (или **Shift + F10**)

---

## 🧠 Архитектура проекта

Проект реализует архитектуру **MVVM**, где:

- **UI** — экраны и компоненты Compose (**app/src/main/java/.../ui/**)  
- **Domain** — бизнес-логика и модели (**app/src/main/java/.../domain/**)  
- **Data** — репозитории, хранилища и источники данных (**app/src/main/java/.../data/**)  
- **Creator** — вспомогательные классы и утилиты (**app/src/main/java/.../creator/**)  

---

## 📂 Структура проекта

```
playlist-maker-android--NikolotovaYG/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/playlist_maker_android_nikolotovayulia/
│   │   │   │   ├── data/         # Репозитории и работа с данными
│   │   │   │   ├── domain/       # Модели и бизнес-логика
│   │   │   │   ├── navigation/   # Навигация
│   │   │   │   └── ui/           # Экраны и ViewModel
│   │   │   └── res/              # Ресурсы (layout, drawable, values и т.д.)
│   │   ├── androidTest/          # UI / интеграционные тесты
│   │   └── test/                 # Unit-тесты
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── gradle/                       # Скрипты Gradle
├── build.gradle.kts              # Корневой Gradle-файл
├── settings.gradle.kts
├── LICENSE
└── README.md
```
---

## 👨‍💻 Автор проекта

**Playlist Maker**  
🌐 [GitHub Profile](https://github.com/y22-co)   

> 💬 “Не жди возможностей. Создавай их.”

---
## 📜 Лицензия

Проект распространяется под лицензией [MIT License](LICENSE).  
Свободно используйте и улучшайте приложение под свои нужды.
