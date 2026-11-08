# 🏥 HospiTron: Intelligent Hospital Management & Resource Optimization System

[![Java Version](https://img.shields.io/badge/Java-27%20LTS-orange?logo=openjdk)](https://openjdk.org/)
[![Architecture](https://img.shields.io/badge/Architecture-MVC%20Layered-blue)](https://github.com/wani-amaan/HospiTron)
[![UI Framework](https://img.shields.io/badge/UI-Java%20Swing%20(Nimbus)-teal)](https://github.com/wani-amaan/HospiTron)
[![Algorithms](https://img.shields.io/badge/Algorithms-Dijkstra%20%7C%20Knapsack%20%7C%20Greedy-purple)](https://github.com/wani-amaan/HospiTron)
[![Institution](https://img.shields.io/badge/Institution-Central%20University%20of%20Kashmir-green)](https://www.cukashmir.ac.in/)

**HospiTron** is an intelligent, desktop-based Hospital Management and Resource Optimization System developed using modern **Java 27**, **Java Swing (Nimbus Modern Theme)**, and core **Data Structures & Algorithms (DSA)**.

Unlike traditional CRUD database projects, HospiTron directly addresses real-world clinical bottlenecks—such as emergency triage delays, overlapping doctor appointments, transit routing across hospital wings, and ICU equipment budgetary constraints—by embedding algorithmic intelligence into healthcare operations.

---

## 🏛️ Academic Background & Team

Developed as part of the academic curriculum for **Object-Oriented Programming & Data Structures** at the **Central University of Kashmir**:

* **Department**: Department of Information Technology & Engineering
* **Program**: B.Tech in Computer Science & Engineering (4th Semester)
* **Project Supervisor**: **Dr. Shahid Sultan**, Assistant Professor
* **Project Team**:
  * **Amaan Qayum Wani** (Enrollment: `2424CUKmr07`) — *Lead Developer*
  * **Faheel Mehraj** (Enrollment: `2424CUKmr19`)
  * **Abdul Mueed** (Enrollment: `2424CUKmr13`)

---

## ⚡ Core Algorithmic Implementations (DSA in Healthcare)

| Algorithm / Data Structure | Module | Clinical Purpose & Complexity |
| :--- | :--- | :--- |
| **Priority Queue (Max-Heap)** | Emergency Triage | **$\mathcal{O}(\log N)$** severity triage ensuring critical patients (Severity 10 down to 1) are attended immediately ahead of routine outpatients. |
| **Greedy Interval Scheduling** | Appointment Module | **$\mathcal{O}(N \log N)$** earliest-finish-time interval selection guaranteeing the maximum subset of mutually compatible, non-overlapping doctor consultation slots. |
| **Dijkstra’s Algorithm** | Route Optimizer | **$\mathcal{O}(V^2)$ / $\mathcal{O}(E + V \log V)$** shortest path computation determining minimum transit time and navigation steps between emergency gates, triage, trauma OT, and ICU. |
| **0/1 Knapsack (Dynamic Programming)** | Resource Allocator | **$\mathcal{O}(N \cdot W)$** dynamic programming optimization selecting the optimal medical equipment and ICU beds to maximize clinical benefit under hospital budget constraints. |
| **Java NIO & Stream Pipeline** | Persistent Storage | **$\mathcal{O}(N)$** memory-efficient file I/O for automatic loading and synchronization of patient, doctor, and appointment records across system restarts. |

---

## 🏗️ System Architecture & Java 27 Modernization

HospiTron is built following a clean, decoupled **Model-View-Controller (MVC)** layered design:

```
┌────────────────────────────────────────────────────────┐
│                      UI LAYER                          │
│   DashboardFrame  •  PatientFrame  •  DoctorFrame      │
│   AppointmentFrame • EmergencyFrame • BillingFrame     │
│             OptimizationFrame (DSA Suite)              │
└───────────────────────────┬────────────────────────────┘
                            │ (Events & Actions)
┌───────────────────────────▼────────────────────────────┐
│                  CONTROLLER LAYER                      │
│                 HospitalController                     │
└───────────────────────────┬────────────────────────────┘
                            │ (Delegation)
┌───────────────────────────▼────────────────────────────┐
│                   SERVICE LAYER                        │
│         HospitalService  •  BillingService             │
└──────────────┬──────────────────────────┬──────────────┘
               │                          │
┌──────────────▼─────────────┐ ┌──────────▼──────────────┐
│      ALGORITHM LAYER       │ │       DATA LAYER        │
│  DijkstraRouting (Graph)   │ │  FileHandler (NIO)      │
│  KnapsackAllocator (DP)    │ │  data/*.txt Flat-Files  │
│  IntervalScheduling        │ └─────────────────────────┘
│  GreedyScheduler           │
└────────────────────────────┘
```

### ☕ Modern Java 27 Language Features Used:
* **Java Records**: Immutable data carriers with compact validation constructors for `Patient`, `Doctor`, `Appointment`, `EmergencyCase`, and algorithm `Result`.
* **Multi-Line Text Blocks (`"""`)**: Clean, formatted strings for medical invoices, bills, and terminal output.
* **Modern Java NIO (`java.nio.file.Path`, `Files`)**: Robust file persistence with atomic directory creation and stream-based parsing.
* **Sequenced Collections (`addFirst`)**: Streamlined path reconstruction for Dijkstra and Knapsack backtracking.
* **Type Inference (`var`) & Immutability**: Clean local variables and defensive copying via `List.copyOf()`.

---

## 📦 Functional Modules

1. **🔐 Authentication & Access Control**
   * Secure credential verification with constant-time password comparison to prevent timing vulnerabilities.
   * Default credentials: Username: `AMAAN`, Password: `12345`.

2. **🧑‍⚕️ Patient Management**
   * Patient admission and demographic tracking.
   * Automated input validation (IDs, realistic age bounds) and regulatory foreigner compliance check.

3. **🩺 Doctor & Medical Staff Management**
   * Doctor registration with department specialization (Cardiology, Surgery, Neurology, Orthopedics) and live availability toggling.

4. **📅 Appointment Management & Conflict Optimization**
   * Booking with explicit Date (`dd-MM-yyyy`) and Time (`HH:mm`) slots.
   * **⚡ "Optimize Schedule" Button**: Executes greedy interval scheduling to mathematically resolve double-bookings and display an optimal timetable.

5. **🚨 Emergency Triage System**
   * Dynamic severity rating (scale 1 to 10).
   * **⚡ "Process Next" Dispatch**: Always polls the patient with the highest clinical acuity from the Max-Heap.

6. **🧾 Medical Billing & Invoicing**
   * Detailed breakdown: Doctor Consultation Fee + Lab Tests + 50% Critical Care Emergency Surcharge.
   * **"Auto-Lookup"**: Fetches registered patient details by ID.
   * **"Export Invoice"**: Generates and saves printable receipts to `data/invoice_patient_<id>.txt`.

7. **⚡ Optimization Suite (Interactive DSA Studio)**
   * **Tab 1 — Emergency Route**: Select source and destination hospital wings to compute transit paths with Dijkstra's algorithm.
   * **Tab 2 — Resource Allocation**: Configure equipment pool capacities and run 0/1 Knapsack to maximize clinical benefit.

8. **🎓 One-Click Academic Demonstration Data Seeder**
   * Instant button on Dashboard that populates realistic mock data (doctors, patients, conflicting appointments, and prioritized emergencies) for live evaluations and demonstrations.

---

## 🚀 Getting Started & How to Run

### Prerequisites
* **Java Development Kit (JDK) 27** or higher installed. Verify with:
  ```powershell
  java -version
  javac -version
  ```

### Quick Run (Windows)
Double-click [`run.bat`](file:///C:/Users/amaan/Documents/CU%20KASHMIR/PPT/SEM%204/JAVA/PROJECT/1/HospiTron/run.bat) or execute in PowerShell / Command Prompt:
```powershell
cd HospiTron
.\run.bat
```

*The batch script automatically prepares the build folder, compiles all packages targeting Java 27 (`javac --release 27`), and launches the application.*

### Resetting Data
To wipe persistent records and start with a completely fresh database:
```powershell
.\run.bat clean
```

---

## 📂 Repository Directory Structure

```text
HospiTron/
├── .gitignore               # Excludes bytecode (out/), temporary data, and IDE configs
├── run.bat                  # Automated compiler and launcher targeting Java 27
├── README.md                # Project documentation and architectural overview
├── data/                    # Persistent storage for patients, doctors, appointments, invoices
└── src/
    ├── main/
    │   └── Main.java                # Global theme setup and application launcher
    ├── model/
    │   ├── Patient.java             # Modern Java 27 record
    │   ├── Doctor.java              # Modern Java 27 record
    │   ├── Appointment.java         # Modern Java 27 record
    │   └── EmergencyCase.java       # Modern Java 27 record
    ├── controller/
    │   └── HospitalController.java  # Intermediary orchestration layer
    ├── service/
    │   ├── HospitalService.java     # Core business logic and collections
    │   └── BillingService.java      # Calculation engine and text-block invoice formatter
    ├── data/
    │   └── FileHandler.java         # Modern Java NIO flat-file persistence
    ├── algorithm/
    │   ├── DijkstraRouting.java     # Dijkstra's shortest path routing
    │   ├── KnapsackAllocator.java   # 0/1 Knapsack dynamic programming
    │   ├── IntervalScheduling.java  # Earliest-finish greedy interval scheduling
    │   └── GreedyScheduler.java     # Time-slot conflict resolver
    ├── security/
    │   └── AuthService.java         # Constant-time authentication service
    ├── util/
    │   └── ValidationUtil.java      # Data sanitation and compliance alerts
    └── ui/
        ├── LoginFrame.java          # Authentication interface
        ├── DashboardFrame.java      # Central navigation and demo data seeder
        ├── PatientFrame.java        # Patient registration and directory
        ├── DoctorFrame.java         # Doctor directory
        ├── AppointmentFrame.java    # Slot booking & Interval Scheduling optimizer
        ├── EmergencyFrame.java      # PriorityQueue emergency triage interface
        ├── BillingFrame.java        # Itemized invoice generator and exporter
        └── OptimizationFrame.java   # Interactive Dijkstra and Knapsack studio
```

---

## 📜 Academic Declaration

> *"This project was developed by the student team at the Department of IT & Engineering, Central University of Kashmir, under the supervision of Dr. Shahid Sultan. It demonstrates the practical application of object-oriented programming, data structures, and algorithmic optimization in healthcare administration."*
