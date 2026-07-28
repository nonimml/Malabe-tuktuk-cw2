# Malabe Spares Depot — Inventory Management System

A JavaFX desktop application for managing vehicle spare-parts inventory: adding, updating, and deleting parts, filtering and searching stock, a point-of-sale cart with bulk and synergy discounts (in Rs.), low-stock alerts, and a dealer directory.

---

## 1. technical Requirement

| Item | Value |
| :--- | :--- |
| **Language** | Java |
| **JDK Version** | JDK 21 (Compatible with Java 17+) |
| **UI Framework** | JavaFX (`javafx.controls`, `javafx.fxml`) |
| **JavaFX Version** | 21.0.6 |
| **Build Tool** | Apache Maven (`pom.xml`) |
| **Test Framework** | JUnit 5 (JUnit Jupiter 5.12.1) |

---

## 2. Project Structure

```text
cw_malabe_tutuk2/
├── pom.xml                                    # Maven dependencies & build configurations
├── src/
│   └── main/
│       ├── java/com/cw_malabe_tutuk2/
│       │   ├── Main.java                      # Application entry point
│       │   ├── MainApplication.java           # JavaFX Application loader
│       │   ├── Controller.java                 # Main window controller (inventory + POS)
│       │   ├── UpdateInventoryController.java  # Add/Update product form controller
│       │   ├── DealerViewController.java       # Dealer directory controller
│       │   ├── Product.java                    # Product data model
│       │   ├── Dealer.java                     # Dealer data model
│       │   ├── Inventory.java                  # In-memory inventory/cart/dealer store
│       │   ├── FileHandler.java                 # Reads/writes flat-file data store
│       │   └── data/
│       │       ├── inventory_legacy.txt         # Legacy 8-field inventory data
│       │       ├── newinventory.txt              # 9-field inventory data (auto-generated)
│       │       ├── dealers_legacy.txt            # Dealer records
│       │       ├── audit_log.txt                 # Append-only audit trail
│       │       └── Images/                       # Product images folder
│       └── resources/com/cw_malabe_tutuk2/
│           ├── MainWindow.fxml                   # Main UI layout
│           ├── UpdateData.fxml                   # Add/Update form layout
│           └── Dealers_Deatails.fxml             # Dealer details layout
└── src/test/java/com/cw_malabe_tutuk2/          # JUnit 5 Unit Test Suite
    ├── InventoryAndCartTest.java                # Tests for sorting, stock, and cart discounts
    ├── DataParsingTest.java                     # Tests for dirty data & delimiter parsing
    └── InputValidationTest.java                 # Tests for input validation & exceptions
```
