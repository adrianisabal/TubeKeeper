## TubeKeeper

TubeKeeper is a Java desktop app for managing downloads. It can handle regular files, videos and playlists, and lets you keep everything in one place instead of juggling browser tabs and random folders.

[![Version](https://img.shields.io/badge/version-1.0.0-blue)](https://github.com/adrianisabal/TubeKeeper/releases)
[![License](https://img.shields.io/badge/license-GPLv2-green)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)
![Stars](https://img.shields.io/github/stars/adrianisabal/TubeKeeper?style=social)
![Forks](https://img.shields.io/github/forks/adrianisabal/TubeKeeper?style=social)

![logo](/resources/images/logoConFondo.png)

---

## Overview

The project is still under active development, but the main pieces are already there:

- A **download from internet** view to add new downloads (files, videos, playlists).
- A **local videos** view where you can browse all videos in a user‑defined directory.
- A **playlists** view that shows all playlists for a given user and lets you inspect or download them.

The goal is to keep the UI simple and focused on real‑world usage, not on exposing every possible knob.

### Current Features

- 🚀 **Download management**
  - Add downloads from URLs.
  - See progress and status of each download.
  - Pause / resume (where supported) and cancel downloads.

- 🎬 **Local video library**
  - Point TubeKeeper at a folder.
  - Browse all videos in that location from within the app.
  - Use this as a simple “library” view instead of opening the file manager all the time.

- 📚 **User playlists**
  - Look up all playlists associated with a user.
  - Inspect playlist contents.
  - Use playlists as a source for batch downloads.

- ⚙️ **Configurable basics**
  - Choose your default download directory.
  - Basic settings for how and where downloads are handled.

- 💾 **Persistent state**
  - Downloads and configuration are saved, so restarting the app doesn’t lose everything.

---

## Installation & Running

### Requirements

- **Java JDK 8+**  
  Any recent JDK should work (Oracle, Temurin, etc.).
- **Git** (optional, but convenient).

### Clone the repository

```bash
git clone https://github.com/adrianisabal/TubeKeeper.git
cd TubeKeeper
```

### Run with Gradle (recommended for development)

You don’t need to build a JAR just to try it out. From the project root:

```bash
# Linux / macOS
./gradlew run

# Windows
.\gradlew.bat run
```

Gradle will download dependencies (first run might take a bit) and then start the GUI.

### (Optional) Build a JAR

If you prefer a JAR:

```bash
# Linux / macOS
./gradlew build

# Windows
.\gradlew.bat build
```

You should then get a JAR under `build/libs`, e.g.:

```bash
java -jar build/libs/TubeKeeper-1.0.0.jar
```

(The exact filename may differ depending on your build configuration.)

---

## How to Use TubeKeeper

### 1. Download from Internet

The main view lets you add URLs for content you want to download.

Typical flow:

1. Open TubeKeeper (`./gradlew run` or via JAR).
2. Go to the **Download from Internet** section.
3. Paste the URL (file, video, or playlist).
4. Select or confirm the download directory.
5. Start the download and watch progress in the list.

### 2. Browse Local Videos

TubeKeeper can act as a simple viewer for a folder full of videos:

1. Open the **Local Videos** view.
2. Set the folder you want to use as your video library.
3. TubeKeeper will list the videos it finds there.
4. Use this view to quickly browse what you already have downloaded.

This is handy if you use TubeKeeper both to download and to keep track of where everything ended up.

### 3. View User Playlists

If you work a lot with playlists:

1. Go to the **Playlists** view.
2. Enter the user whose playlists you want to see.
3. The app lists all playlists for that user.
4. From there you can inspect contents and (where supported) download items from those playlists.

---

## Roadmap

The project is not finished yet. Some things we’d like to add or improve:

- **Multi‑part downloads** to speed up large files.
- **Better integration with browsers** (easier URL capture, maybe an extension).
- **Scheduling**: start downloads at a given time.
- **Theming**: light/dark theme and some basic customization.
- More robust error handling and clearer messages when something fails.
- Performance and UX polish as we get more feedback.

If you’re interested in any of these areas, contributions and ideas are welcome.

---

## Contributing

Contributions of all kinds are appreciated: code, testing, docs, feature ideas, UX feedback…

### Coding style

- Standard Java conventions.
- Clear names over clever names.
- Keep formatting consistent (use your IDE’s formatter).

### Branches

- `main` – stable branch.
- `feature/<name>` – new features.
- `bugfix/<description>` – bug fixes.
- `docs/<description>` – documentation changes.
- `refactor/<description>` – internal refactors.

### Pull Request flow

1. Fork the repo.
2. Create a branch from `main`, e.g.:

   ```bash
   git checkout -b feature/my-new-feature
   ```

3. Make your changes and commit with clear messages.
4. Push your branch to your fork.
5. Open a PR against `main` in this repository, explaining what you changed and why.

If your PR changes behavior in a user‑visible way, a short note or screenshot is very useful.

---

## License

TubeKeeper is released under the **GNU General Public License v2.0 (GPLv2)**.

You are free to use, modify and redistribute this software under the terms of the GPLv2.  
See the [LICENSE](LICENSE) file or the [official GPLv2 text](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html) for details.

**Copyright (c) 2023 fragi0, adrianisabal, bombitron**

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, version 2 of the License.
