# 🎫 AJIRCTC - Java Ticket Booking Application

A **console-based ticket booking system** built in **Java**, simulating basic railway reservation features. This project uses **local JSON files** as a lightweight database for storing user, train, and ticket data.

---

## 📌 Features

- 👤 **User Authentication**
  - Sign up with username and password (hashed using **BCrypt**)
  - Secure login system

- 🔍 **Train Search**
  - Search trains by source and destination
  - View details like train number, route, available seats, and timings

- 🎟️ **Ticket Booking**
  - Book tickets by selecting train ID
  - Add passenger details (name, gender, number of seats)
  - Automatically assigns available seats and generates a **PNR-style Ticket ID (e.g., AJ123456)**

- 📄 **View Bookings**
  - Retrieve all tickets booked by the user

- ❌ **Cancel Tickets**
  - Cancel any previously booked ticket using the PNR

- 💾 **Persistent Storage**
  - All data (users, trains, tickets) stored in **local JSON files** using Jackson
  - `users.json`, `trains.json`, and `tickets.json`

---

## 🧱 Project Structure
ticket.booking/
│
├── App.java # Main driver class
│
├── entities/
│ ├── User.java
│ ├── Train.java
│ └── Ticket.java
│
├── services/
│ ├── UserBookingService.java
│ └── TrainServices.java
│
├── util/
│ ├── UserServiceUtil.java
│ └── TicketUtil.java
│
├── data/
│ ├── users.json
│ ├── trains.json
│ └── tickets.json

---

## 🛠️ Technologies Used

- **Java 8+**
- **Jackson (for JSON parsing)**
- **BCrypt (for secure password hashing)**

---

## 🚀 Future Enhancements

- 🌐 Integrate with **Spring Boot**
- 🗃️ Switch to **MySQL/MongoDB**
- 🖥️ Build a **frontend UI** using JavaFX or a web framework
- 👤 Add **admin panel** for managing trains and all bookings

---
