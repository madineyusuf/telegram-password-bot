# 🔐 Password Generator Telegram Bot

A robust and secure Telegram bot designed to generate strong, random passwords. Built with **Java** and the **TelegramBots** library.

## ✨ Features
* **High Security**: Uses `SecureRandom` for cryptographically strong password generation.
* **Privacy First**: Passwords are sent as **spoilers** (hidden until clicked) to prevent shoulder surfing.
* **One-Tap Copy**: Generated passwords use monospaced formatting, allowing users to copy them with a single tap.
* **Multilingual Support**: Supports both **English** and **Turkish** commands.
* **Custom Length**: Users can specify the password length (up to 100 characters).

## 🚀 Commands
* `/start` or `Başla` — Displays the welcome message and instructions.
* `/generate [length]` or `Oluştur [length]` — Generates a random password. (Default is 12 characters).

## 🛠 Tech Stack
* **Language**: Java 17
* **Build Tool**: Maven
* **Library**: TelegramBots (Long Polling)

## ⚙️ How to Run Locally

1. **Clone the repository**:
   ```bash
   git clone [https://github.com/YOUR_USERNAME/PROJECT1.git](https://github.com/YOUR_USERNAME/PROJECT1.git)
