# Gym_management_system_Akshat
Gym Management System 💪
The Gym Management System is a desktop application designed to simplify gym operations, including managing members, tracking payments, scheduling classes, and logging attendance. Built using Java, MySQL, and JDBC, this system provides an intuitive and efficient way to handle day-to-day gym tasks, making it ideal for fitness centers of all sizes.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

🏋️ Key Features
👥 Member Management

Easily add, update, delete, and view member details.
Track personal details like name, email, phone, address, date of birth, and gender.
Manage membership types: Monthly, Yearly, or Lifetime.
Monitor member status (Active/Inactive) and join date.
📅 Class Scheduling

Schedule gym classes with specific timings, durations, and instructor assignments.
Define class capacity and include detailed descriptions.
View all classes at a glance, including their schedules and available spots.
👨‍🏫 Instructor Management

Maintain a record of all instructors, including their names, specializations, and hire dates.
Assign instructors to specific classes based on their expertise.
💳 Payment Tracking

Record payments with details like amount, payment method (Cash, Credit Card, Digital Wallet, etc.), and date.
Generate unique transaction IDs for secure tracking.
View pending payments and maintain a complete payment history.
📋 Attendance Logging

Log attendance for members participating in classes.
Generate reports to analyze member engagement and identify trends.
🎁 Promotions & Discounts

Create and manage promotional codes for special discounts.
Define discount percentages and validity periods for promotional offers.
Encourage new sign-ups and retain existing members.
🌟 Feedback Collection

Allow members to provide feedback on gym services and classes.
Collect star ratings (1 to 5) to gauge member satisfaction.
Use feedback to improve gym facilities and member experience.
📜 Membership Plans

Store and manage a variety of membership plans (e.g., Monthly, Yearly).
Specify durations, pricing, and plan benefits.
Help members choose the best plan based on their needs.

Allow members to submit feedback and ratings for gym services.
Use feedback to improve the overall experience.
Membership Plans

Manage various membership plans (e.g., monthly, yearly).
Define durations, pricing, and detailed plan descriptions.
📂 Project Structure
The project is organized into the following packages:

DAO (Data Access Objects): Handles database interactions for members, classes, payments, and more.
UI (User Interface): Provides a graphical interface built using Java Swing for user interaction.
📋 Database Schema
Here’s a summary of the database tables used in the system:
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
🚀 How to Run the Project on Your System

Follow these steps to set up and run the Gym Management System:

Prerequisites

Install Java JDK (Version 8 or above).
Install MySQL Server (Version 5.7 or above).
Use a Java IDE like IntelliJ IDEA, Eclipse, or NetBeans.

Set Up MySQL: Install MySQL Server (Version 5.7 or above) and create the required database using the provided SQL script.
Install SQL Connector:
Download the MySQL Connector JAR file from MySQL official site.
Add the JAR file to your code editor/IDE. In Eclipse, you can do this by:
Right-click the project > Build Path > Configure Build Path > Libraries > Add External JARs.
Select the downloaded JAR file and click Apply and Close.

Steps to Run
1. Clone the Repository

git clone https://github.com/AkshatSharma555/Gym-management-system__AkshatSharma.git
cd Gym-management-system__AkshatSharma

3. Set Up the Database
Import the provided gym_management.sql file into your MySQL server to create the database and tables.
Update the database connection details in DBConnection.java:

String url = "jdbc:mysql://localhost:3306/your_database_name";
String user = "your_username";
String password = "your_password";

4. Open and Compile the Project
Open the project in your Java IDE.
Compile all Java files to ensure there are no errors.

5. Run the Main frame( Launch the Main Frame)
Locate the main class in UI section (Main frame.java or equivalent) in the project.
Run the main class to launch the application.

📹 Demo Video
Check out the demo video to see the Gym Management System in action! 
https://drive.google.com/drive/folders/12-Ha8-ct0yQOa6actOg9qCVfpUH-iGRE?usp=sharing
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

🤝 Contribution
Contributions are welcome! If you'd like to improve the project or add new features:

Fork the repository.
Create a feature branch:

git checkout -b feature-name
Commit your changes:

git commit -m "Add a new feature"
Push to your branch:

git push origin feature-name
Open a pull request.

📜 License
This project is licensed under the MIT License. See the LICENSE file for more details.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
💡 Future Enhancements
Integrate payment gateways for online payments.
Add notifications for membership renewals via email or SMS.
Develop a mobile app for members to access their profiles and schedules.
Create analytics dashboards for gym performance insights.
