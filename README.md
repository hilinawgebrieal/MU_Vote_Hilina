 MU VOTE - Android Voting App

Author: Hilina Weldegebrieal  
Email: weldegebrielhilina@gmail.com
### Project Overview
    MU VOTE is an Android voting application developed using Kotlin and XML in Android Studio,
    with Firebase Firestore as the backend database. The app allows students to securely vote
    for their president.

App Features
- Home Page: Initial screen with options to log in or sign up.
- Register Page: Register using email, student ID, and password.
- Login Page:Log in using student ID and password.
- Vote Page: Displays a list of presidential candidates with radio buttons to select and vote.
- Vote Successful Page: Displays a confirmation message and current voting results.

 Navigation Flow
1. **Home Page:**
   - Click **Log In**to go to the Login Page.
   - Click **Sign Up** if you don't have an account to navigate to the Register Page.

2. **Register Page:**
   - Enter your email, student ID, and password.
   - Click **Done** to complete registration and navigate back to the Login Page.

3. **Login Page:**
   - Enter your student ID and password.
   - Click **Go** to navigate to the Vote Page upon successful login.

4. **Vote Page:**
   - View a list of candidates.
   - Select a candidate using radio buttons.
   - Click **Vote** to submit your vote and navigate to the Vote Successful Page.

5. **Vote Successful Page:**
   - Displays a success message and current voting results.

 Firebase Integration
- **Authentication:** Register and log in using Firebase Authentication (email, student ID, and password).
- **Firestore Database:** Store user data, candidate list, and voting results.

 Project Setup Instructions
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/hilinawgebrieal/MU_Vote_Hilina.git
   ```
2. **Open in Android Studio:**
   - Open Android Studio and select the cloned project.

3. **Configure Firebase:**
   - Create a Firebase project.
   - Add your Android app to the Firebase project.
   - Download `google-services.json` and place it in the `app/` directory.
   - Enable Firebase Authentication and Firestore Database.

4. **Build and Run the Project:**
   - Connect an Android device or use an emulator.
   - Run the project through Android Studio.

 Dependencies
```gradle
implementation 'com.google.firebase:firebase-auth:latest_version'
implementation 'com.google.firebase:firebase-firestore:latest_version'
```

 Code Structure
- **activities/** - Contains Kotlin files for each page.
- **layouts/** - XML files for the app UI.
- **firebase/** - Firebase configuration and utility classes.


