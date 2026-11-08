# 🏥 HospiTron: Intelligent Hospital Management & Resource Optimization System

[![Java Version](https://img.shields.io/badge/Java-27%20LTS-orange?logo=openjdk)](https://openjdk.org/)
[![Architecture](https://img.shields.io/badge/Architecture-MVC%20Layered-blue)](#-system-architecture)
[![UI Framework](https://img.shields.io/badge/UI-Java%20Swing%20(Nimbus)-teal)](#-user-interface--features)
[![Algorithms](https://img.shields.io/badge/DSA-Dijkstra%20%7C%20Knapsack%20%7C%20Greedy%20%7C%20PriorityQueue-purple)](#-algorithmic-intelligence--dsa-deep-dive)
[![Build Status](https://img.shields.io/badge/Build-Passing%20(0%20errors)-brightgreen)](#-getting-started--execution)

**HospiTron** is an intelligent, desktop-based Hospital Management and Resource Optimization System engineered with modern **Java 27 (LTS)** and **Java Swing (Nimbus Modern Theme)**.

Unlike typical student CRUD applications that simply act as passive databases, **HospiTron integrates core computational Data Structures & Algorithms (DSA)** directly into clinical workflows—automating emergency acuity triage, eliminating doctor appointment scheduling conflicts, calculating fastest internal transit routes across hospital wings, and optimizing medical equipment allocation under strict budgetary constraints.

---

## 📌 Table of Contents
1. [The Problem Statement](#-the-problem-statement)
2. [Existing Systems vs. HospiTron](#-existing-systems-vs-hospitron)
3. [Algorithmic Intelligence & DSA Deep-Dive](#-algorithmic-intelligence--dsa-deep-dive)
4. [System Architecture](#-system-architecture)
5. [Java 27 Modernization Highlights](#-java-27-modernization-highlights)
6. [Functional Modules](#-functional-modules)
7. [Getting Started & Execution](#-getting-started--execution)
8. [Demonstration Guide (For Viva / Evaluation)](#-demonstration-guide-for-viva--evaluation)
9. [Project Team & Contributors](#-project-team--contributors)

---

## ⚠️ The Problem Statement

Healthcare facilities operate in high-pressure, time-critical environments. Despite rapid digitalization, most mid-sized and public hospital facilities suffer from severe operational bottlenecks:

1. **Passive "First-Come, First-Served" Triage**:
   Traditional systems log emergency walk-ins sequentially. When patient volume spikes, critical life-or-death emergencies (e.g., acute trauma, cardiac arrest) can sit in the queue behind non-urgent outpatients.
2. **Scheduling Conflicts & Doctor Idle Time**:
   Manual or basic calendar booking lacks computational interval verification. Overlapping appointments cause doctor burnout, overcrowded waiting rooms, and disrupted clinical care.
3. **Inefficient Emergency Transit Routing**:
   Large multi-ward medical complexes lack automated routing. Emergency teams lose precious minutes determining the fastest transit corridor between the ambulance entrance, triage, trauma OT, and the ICU.
4. **Sub-optimal Medical Budget Allocation**:
   Procurement of intensive care machinery (ventilators, hemodialysis units, defibrillators) is often done via ad-hoc estimates rather than mathematical optimization, leading to budget exhaustion with suboptimal clinical patient capacity.
5. **Data Fragmentation & Calculation Errors**:
   Disconnected spreadsheets and paper registers result in lost medical histories and manual billing discrepancies (such as omitted emergency critical care surcharges).

---

## ⚖️ Existing Systems vs. HospiTron

| Feature / Dimension | Traditional / Existing Systems | HospiTron Intelligent System |
| :--- | :--- | :--- |
| **Emergency Triage** | First-Come-First-Served (FIFO) Queue | **PriorityQueue Max-Heap** dynamically ordering by acuity (Severity 10 $\to$ 1) |
| **Appointment Booking** | Static calendar with frequent overlaps | **Greedy Interval Scheduling** mathematically eliminating conflicts in $\mathcal{O}(N \log N)$ |
| **Emergency Routing** | Manual staff discretion and signage | **Dijkstra’s Algorithm** calculating exact shortest transit corridors in minutes |
| **Equipment Budgeting** | Manual estimation / spreadsheets | **0/1 Knapsack Dynamic Programming** maximizing clinical benefit score |
| **Data Architecture** | Disconnected files or heavy external DBs | **Unified Java NIO persistence** with atomic directory verification |
| **Language Standards** | Legacy Java 7 / 8 boilerplate | **Cutting-Edge Java 27 LTS** (Records, Text Blocks, Sequenced Collections) |
| **Invoice Breakdown** | Manual arithmetic calculation | **Automated Itemized Engine** with emergency critical care surcharges & file export |

---

## ⚡ Algorithmic Intelligence & DSA Deep-Dive

HospiTron is fundamentally an **academic demonstration of applied computer science in healthcare**. Four core algorithms drive the decision-making engine:

### 1. Priority Queue Emergency Triage ($\mathcal{O}(\log N)$)
* **Class**: `HospitalService.java` & `EmergencyFrame.java`
* **Mechanism**: A binary max-heap orders incoming emergency cases based on a clinician-assigned severity score ($1 \le s \le 10$).
* **Clinical Impact**: When an emergency room physician clicks *"Process Next"*, the system mathematically guarantees that the patient with the highest clinical acuity is dequeued first, regardless of arrival order.

### 2. Greedy Interval Scheduling ($\mathcal{O}(N \log N)$)
* **Class**: `IntervalScheduling.java` & `GreedyScheduler.java`
* **Mechanism**: Given a set of overlapping appointment requests $A = \{a_1, a_2, \dots, a_n\}$ with start times $s_i$ and finish times $f_i$, the algorithm sorts appointments in ascending order of their finish times ($f_1 \le f_2 \le \dots \le f_n$) and greedily selects the maximum subset of mutually compatible slots.
* **Clinical Impact**: Guarantees zero doctor double-booking while maximizing total consultation throughput.

### 3. Dijkstra's Shortest Path Routing ($\mathcal{O}(V^2)$ / $\mathcal{O}(E + V \log V)$)
* **Class**: `DijkstraRouting.java` & `OptimizationFrame.java`
* **Mechanism**: Hospital corridors and wings are modeled as a weighted directed graph $G = (V, E)$, where vertices $V$ represent hospital units (Ambulance Bay, ER Reception, Trauma OT, ICU, Radiology, Wards) and edge weights $w(u, v)$ represent transit minutes.
* **Clinical Impact**: Dispatches step-by-step navigation instructions and transit time estimates for emergency ambulance gurney transfers.

### 4. 0/1 Knapsack Dynamic Programming ($\mathcal{O}(N \cdot W)$)
* **Class**: `KnapsackAllocator.java` & `OptimizationFrame.java`
* **Mechanism**: Solves the classic 0/1 Knapsack problem using dynamic programming recurrence:
  $$DP[i][w] = \max(DP[i-1][w], \; DP[i-1][w - \text{weight}[i]] + \text{value}[i])$$
  Subject to total hospital expenditure $\sum w_i \le W_{\text{budget}}$.
* **Clinical Impact**: Selects the optimal combination of medical equipment (ICU Ventilators, Defibrillators, Monitors, Dialysis Units) that delivers maximum clinical score within budget.

---

## 🏗️ System Architecture

HospiTron enforces a strict, decoupled **Model-View-Controller (MVC)** layered pattern:

```
┌────────────────────────────────────────────────────────────────────────┐
│                              UI LAYER                                  │
│   DashboardFrame     •     PatientFrame      •       DoctorFrame       │
│   AppointmentFrame   •     EmergencyFrame    •       BillingFrame      │
│                     OptimizationFrame (DSA Suite)                      │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ (Action Events & Form Input)
┌───────────────────────────────────▼────────────────────────────────────┐
│                          CONTROLLER LAYER                              │
│                         HospitalController                             │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ (Delegation & Mediation)
┌───────────────────────────────────▼────────────────────────────────────┐
│                           SERVICE LAYER                                │
│                HospitalService  •  BillingService                      │
└─────────────────┬──────────────────────────────────────┬───────────────┘
                  │                                      │
┌─────────────────▼───────────────┐    ┌─────────────────▼───────────────┐
│        ALGORITHM LAYER          │    │           DATA LAYER            │
│   DijkstraRouting (Graph)       │    │     FileHandler (Java NIO)      │
│   KnapsackAllocator (DP)        │    │    data/*.txt Atomic Storage    │
│   IntervalScheduling (Greedy)   │    └─────────────────────────────────┘
│   GreedyScheduler (Slots)       │
└─────────────────────────────────┘
```

---

## ☕ Java 27 Modernization Highlights

The entire codebase is compiled targeting **Java 27 LTS (`javac --release 27`)**:

* **Immutable Records**: Data models (`Patient`, `Doctor`, `Appointment`, `EmergencyCase`, `Result`) are declared as records, eliminating boilerplate getters, hash codes, and equals contracts.
* **Multi-Line Text Blocks (`"""`)**: Structured ASCII medical invoices and consultation summaries are formatted cleanly without string concatenation.
* **Modern Java NIO (`java.nio.file.Path`, `Files`)**: All persistence uses atomic NIO stream pipelines (`Files.lines()`, `Files.writeString()`, `StandardOpenOption.CREATE`).
* **Sequenced Collections (`addFirst`)**: Replaces cumbersome reverse loops during graph path reconstruction and dynamic programming backtracking.
* **Type Inference (`var`) & Immutability**: Applied across all controllers and frames, with defensive read-only collections via `List.copyOf()`.

---

## 📦 Functional Modules

1. **🔐 Authentication & Access Control**
   * Constant-time credential verification protecting against timing analysis attacks.
   * Default admin credentials: Username: `AMAAN`, Password: `12345`.
2. **🧑‍⚕️ Patient Management**
   * Registration, demographic recording, and regulatory foreigner compliance checks.
3. **🩺 Doctor & Faculty Management**
   * Specialization registry (Surgery, Cardiology, Neurology, Orthopedics, Emergency Medicine) with active status flags.
4. **📅 Appointment & Slot Optimization**
   * Real-time slot booking with date and time formatting.
   * Single-click **"⚡ Optimize Schedule"** button running interval scheduling to resolve conflicts.
5. **🚨 Priority Emergency Triage**
   * Severity rating system ($1 \to 10$) with single-click triage dispatch.
6. **🧾 Billing, Invoicing & Export**
   * Consultation + lab diagnostic breakdown with automatic **+50% emergency critical care surcharge**.
   * Single-click export of official medical receipts to `data/invoice_patient_<id>.txt`.
7. **⚡ Optimization Suite**
   * Interactive GUI tabbed panel running live Dijkstra route computations and 0/1 Knapsack resource allocations.
8. **🎓 One-Click Academic Demo Dataset Seeder**
   * Pre-loads 5 specialized doctors, 5 patients, 7 conflicting appointments, and prioritized emergency cases for instant demonstration.

---

## 🚀 Getting Started & Execution

### Prerequisites
* **Java Development Kit (JDK) 27** or higher installed. Verify in your terminal:
  ```powershell
  java -version
  javac -version
  ```

### Quick Run (Windows)
Double-click [`run.bat`](run.bat) or execute in PowerShell:
```powershell
cd HospiTron
.\run.bat
```

*The batch script automatically prepares directories, compiles the entire project using `javac --release 27`, and launches the system.*

### Resetting Data to Clean Slate
To clear all stored patient records and invoices:
```powershell
.\run.bat clean
```

---

## 🎯 Demonstration Guide (For Viva / Evaluation)

To demonstrate the full algorithmic capabilities of HospiTron in under 2 minutes:

1. **Launch the application** using `run.bat` and log in (`AMAAN` / `12345`).
2. **Click "🎓 Load Academic Demonstration Data"** on the Dashboard. This populates pre-configured conflicting appointments and emergencies.
3. **Demonstrate Interval Scheduling**:
   * Open **Appointment Module** $\to$ Click **"View Appointments"** (notice overlapping slots at 10:00, 10:10, 10:30, 10:40).
   * Click **"⚡ Optimize Schedule"** $\to$ Watch the algorithm instantly select the optimal conflict-free subset!
4. **Demonstrate Emergency Triage**:
   * Open **Emergency Triage** $\to$ Click **"⚡ Process Next"**.
   * Notice that *Fatima Bano* (Severity 9) is dispatched first ahead of *Tariq Shah* (Severity 4), proving the Max-Heap priority logic.
5. **Demonstrate Optimization Suite**:
   * Open **Optimization Suite** $\to$ Run Dijkstra from *Ambulance Bay* to *ICU* to display the shortest transit path.
   * Switch to the *Resource Allocation* tab $\to$ Input capacity limit (e.g. `45`) and watch 0/1 Knapsack compute the optimal medical equipment package.

---

## 👥 Project Team & Contributors

Developed as an advanced implementation of **Object-Oriented Programming & Data Structures in Healthcare**:

* **Project Supervisor**: **Dr. Shahid Sultan**, Assistant Professor
* **Project Contributors**:
  * **Amaan Qayum Wani** — *Lead Developer*
  * **Faheel Mehraj**

---

## 📜 Project Declaration

> *"This project was designed and implemented by the project team under the supervision of Dr. Shahid Sultan. It demonstrates the practical application of object-oriented programming, data structures, and algorithmic optimization in healthcare administration."*
