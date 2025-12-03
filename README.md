## TubeKeeper

Your ultimate Java-powered download manager for seamless content acquisition.

[![Version](https://img.shields.io/badge/version-1.0.0-blue)](https://github.com/adrianisabal/TubeKeeper/releases)
[![License](https://img.shields.io/badge/license-GPLv2-green)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)
![Stars](https://img.shields.io/github/stars/adrianisabal/TubeKeeper?style=social)
![Forks](https://img.shields.io/github/forks/adrianisabal/TubeKeeper?style=social)

![logo](/resources/images/logo.png)

---

## ✨ Features

TubeKeeper is designed to provide a robust and user-friendly experience for managing your downloads.

*   🚀 **Efficient Download Management:** Seamlessly handle multiple downloads, pause, resume, and organize your files with ease.
*   🔗 **Broad Protocol Support:** Download content from various sources, including HTTP, HTTPS, and potentially more in future updates.
*   ⚙️ **Configurable Settings:** Customize download directories, connection limits, and other preferences to suit your needs.
*   💾 **Persistent Downloads:** Automatically save download progress, allowing you to pick up where you left off even after closing the application.
*   🛡️ **Lightweight & Reliable:** Built with Java, TubeKeeper offers a stable and resource-efficient solution for all your downloading tasks.

---

## 🛠️ Installation Guide

To get TubeKeeper up and running, follow these steps. Ensure you have a Java Development Kit (JDK) installed on your system (version 8 or higher is recommended).

### Prerequisites

*   **Java Development Kit (JDK):** Version 8 or newer.
    You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use an open-source distribution like [Adoptium Temurin](https://adoptium.net/).
*   **Git:** For cloning the repository.

### Manual Installation (Building from Source)

1.  **Clone the Repository:**
    Open your terminal or command prompt and clone the TubeKeeper repository:

    ```bash
    git clone https://github.com/adrianisabal/TubeKeeper.git
    cd TubeKeeper
    ```

2.  **Build the Project with Gradle:**
    TubeKeeper uses Gradle for dependency management and building. Navigate to the project root and execute the build command:

    ```bash
    # On Linux/macOS
    ./gradlew build

    # On Windows
    .\gradlew.bat build
    ```
    This command will compile the source code, run tests, and package the application. The executable JAR file will typically be found in the `build/libs` directory.

3.  **Run the Application:**
    After a successful build, you can run TubeKeeper using the generated JAR file:

    ```bash
    java -jar build/libs/TubeKeeper-1.0.0.jar
    ```
    (Note: The exact JAR filename might vary based on the version number.)

---

## 🚀 Usage Examples

Once installed, TubeKeeper provides a simple interface for managing your downloads.

### Basic Usage

To launch the application, use the command provided in the installation steps. A graphical user interface (GUI) should appear, allowing you to add new download links.

```bash
java -jar build/libs/TubeKeeper-1.0.0.jar
```

### Adding a New Download

1.  Click the "Add Download" button (or equivalent in the UI).
2.  Paste the URL of the file you wish to download into the input field.
3.  Specify the desired save location.
4.  Click "Start Download."

![TubeKeeper UI Screenshot Placeholder](/preview_example.png)
_Screenshot: [placeholder] TubeKeeper's main interface showing active downloads._

### Common Use Cases

*   **Downloading a large file:** Paste the direct download link for a large software installer or video. TubeKeeper will manage the download, allowing you to pause and resume as needed.
*   **Batch Downloads:** While not explicitly a feature yet, future versions may allow adding multiple URLs for sequential or parallel downloading.
*   **Managing interrupted downloads:** If your internet connection drops, TubeKeeper will attempt to resume the download from where it left off once the connection is restored.

---

## 🗺️ Project Roadmap

TubeKeeper is continuously evolving. Here's a glimpse into our future plans:

### Upcoming Features

*   **Multi-part Downloads:** Implement parallel downloading for faster acquisition of large files.
*   **Browser Integration:** Develop browser extensions for easier link capture and direct download initiation.
*   **Scheduler:** Add a feature to schedule downloads for specific times.
*   **Theming Options:** Allow users to customize the application's appearance.

### Planned Improvements

*   **Enhanced Error Handling:** Improve robustness and provide clearer feedback on download failures.
*   **Performance Optimization:** Further optimize resource usage and download speeds.
*   **User Experience Refinements:** Streamline the UI/UX based on community feedback.

### Version Milestones

*   **v1.1.0:** Focus on multi-part download capabilities and initial browser integration.
*   **v1.2.0:** Introduce download scheduling and advanced configuration options.
*   **v2.0.0:** Major refactor for modularity and extensibility, including a plugin architecture.

---

## 🤝 Contribution Guidelines

We welcome contributions to TubeKeeper! Please follow these guidelines to ensure a smooth collaboration.

### Code Style

*   Adhere to standard Java coding conventions (e.g., Google Java Style Guide or Oracle Code Conventions).
*   Use clear and descriptive variable/method names.
*   Format your code using a consistent style (e.g., via an IDE formatter).

### Branch Naming Conventions

*   **`main`**: The stable production branch. All pull requests should target this branch.
*   **`feature/<feature-name>`**: For new features.
*   **`bugfix/<issue-description>`**: For bug fixes.
*   **`docs/<description>`**: For documentation updates.
*   **`refactor/<description>`**: For code refactoring that doesn't change external behavior.

### Pull Request (PR) Process

1.  **Fork** the repository.
2.  **Create a new branch** from `main` (e.g., `git checkout -b feature/my-awesome-feature`).
3.  **Make your changes** and commit them with clear, concise messages.
4.  **Push** your branch to your forked repository.
5.  **Open a Pull Request** to the `main` branch of the original TubeKeeper repository.
6.  Provide a clear description of your changes and reference any related issues.

### Testing Requirements

*   All new features should be accompanied by relevant **unit tests**.
*   Bug fixes should include a test that reproduces the bug and verifies the fix.
*   Ensure all existing tests pass before submitting a pull request.

---

## 📜 License Information

TubeKeeper is distributed under the **GNU General Public License v2.0**.

You are free to use, modify, and distribute this software under the terms of the GPLv2.0. For the full text of the license, please refer to the [LICENSE](LICENSE) file in this repository or visit [GNU General Public License v2.0](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html).

**Copyright (c) 2023 fragi0, adrianisabal, bombitron**

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
