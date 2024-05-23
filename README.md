# SoPra FS24 Group 27 Project - LyricLies 🎵

## Introduction
LyricLies is a multiplayer online game designed to combine music and emojis. Players use their knowledge of music and non-literal expression skills to identify the "lyric spy" among them. This entertaining social platform promotes the exploration of musical perception and non-verbal communication, thereby enriching the social gaming landscape with educational and fun elements.

## Technologies Used
- **Backend:** Java, Spring Boot, JPA/Hibernate
- **Frontend:** JavaScript, React, Material-UI
- **Communication:** REST
- **Testing:** JUnit, Mockito
- **Other:** Spotify API for song selection

## High-Level Components
1. **GameController.java**
   - Role: Controls all game-related activities through REST endpoints.
   - Link: [GameController.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/controller/GameController.java)

2. **GameService.java**
   - Role: Provides game logic and interacts with the GameController.
   - Link: [GameService.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/service/GameService.java)

3. **SpotifyService.java**
   - Role: Interacts with Spotify API to fetch songs for the game.
   - Link: [SpotifyService.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/service/SpotifyService.java)

4. **UserController.java**
   - Role: Manages user-related activities through REST endpoints.
   - Link: [UserController.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/controller/UserController.java)

5. **UserService.java**
   - Role: Provides user-related logic and interacts with the UserController.
   - Link: [UserService.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/service/UserService.java)

6. **AuthInterceptor.java & AuthConfig.java**
   - Role: Handles authentication for all activities.
   - Link: [AuthInterceptor.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/interceptor/AuthInterceptor.java), [AuthConfig.java](./src/main/java/ch/uzh/ifi/hase/soprafs24/config/AuthConfig.java)

## Launch & Deployment
### Server
1. **Build:**
   ```sh
   ./gradlew build
   ```
   This command compiles the source code, runs tests, and packages the application.

2. **Run:**
   ```sh
   ./gradlew bootRun
   ```
   This starts the application on your local machine. You can verify that the server is running by visiting `http://localhost:8080` in your browser.

3. **Test:**
   ```sh
   ./gradlew test
   ```
   This executes the test suite and provides feedback on the application's functionality and reliability.

4. **Deploy:**
   ```sh
   git add .
   git commit -m "message here"
   git push origin main
   ```
   These commands push the changes to GitHub and automatically deploy the app on Google Cloud.

## Roadmap
- **Real-Time In-Game Chat:** Add a chat function to allow players to communicate during voting.
- **Spotify OAuth Integration:** Allow users to log into their Spotify account and select songs from their playlist.
- **Full-Song Playback:** Enable players to listen to the full song during the game (requires Spotify Premium account).

## Authors and Acknowledgment
SoPra Group 27 2024 consists of:
- Yating Pan - [GitHub](https://github.com/YatingPan)
- Hepeng Fan - [GitHub](https://github.com/bazziprincess)
- Qingcheng Wang - [GitHub](https://github.com/wqc260615)
- Yuying Zhong - [GitHub](https://github.com/Yuying8777)
- Yi Zhang - [GitHub](https://github.com/imyizhang)

We would like to thank our teaching assistant Sven - [GitHub](https://github.com/SvenRingger) for his help throughout the semester. We also thank Spotify for providing its API and the game of "Who is Spy" for inspiring our idea. This semester has proven to be both challenging and intriguing, offering us valuable opportunities for growth.

## License
This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
