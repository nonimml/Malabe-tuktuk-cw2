# Malabe Spares Depot

A JavaFX desktop application for managing vehicle spare-parts inventory, dealers, and a
point-of-sale style checkout cart. Built with Maven and the `javafx-maven-plugin`.

## Git Repository

repository link (https://github.com/nonimml/Malabe-tuktuk-cw2)

## Tech Stack / Versions

| Component        | Version                                                  |
|-------------------|-----------------------------------------------------------|
| Build tool        | Maven (uses `maven-compiler-plugin` 3.13.0 and `javafx-maven-plugin` 0.0.8) |
| JDK               | **JDK 17 or later recommended** (see note below)         |
| JavaFX            | `javafx-controls` and `javafx-fxml` **21.0.6**            |
| Testing framework | JUnit Jupiter **5.12.1** (declared as a test dependency)  |

## Requirements

- JDK 17+ installed and on your `PATH` (`java -version` / `javac -version`)
- Maven 3.6+ installed (`mvn -version`)
- No external database is required — the app reads/writes plain text files on disk
  (see **Assumptions & Data Files** below)

## Project Structure (relevant packages)

```
src/main/java/com/cw_malabe_tutuk2/
├── Main.java                     # application entry point (launches MainApplication)
├── MainApplication.java          # JavaFX Application, loads FXML windows
├── Controller.java                # main inventory/POS window controller
├── DealerViewController.java     # dealers window controller
├── UpdateInventoryController.java# add/update product form controller
├── Inventory.java                 # in-memory product/dealer/cart storage
├── Product.java                   # product/part model
├── Dealer.java                    # dealer/supplier model
├── FileHandler.java               # reads/writes text data files, audit log
├── MainWindow.fxml                # (expected resource, not included in upload)
├── UpdateData.fxml                # (expected resource, not included in upload)
├── Dealers_Deatails.fxml          # (expected resource, not included in upload)
└── data/
    ├── inventory_legacy.txt       # legacy 8-field inventory data (optional)
    ├── newinventory.txt           # 9-field inventory data (preferred, auto-created on save)
    ├── dealers_legacy.txt         # dealer/supplier data
    ├── audit_log.txt              # append-only audit trail (auto-created)
    └── Images/                    # product images referenced by file name
```

## How to Run the Application

1. Clone the repository and `cd` into the project root (the folder containing `pom.xml`).
2. Make sure the `data` folder described above exists under
   `src/main/java/com/cw_malabe_tutuk2/data/` with at least one of
   `inventory_legacy.txt` or `newinventory.txt`, plus `dealers_legacy.txt`, and an
   `Images` subfolder (create empty folders if you don't have sample data yet — see
   **Assumptions** below).
3. Run the app with the JavaFX Maven plugin:

   ```bash
   mvn clean javafx:run
   ```

   This uses the `default-cli` execution configured in `pom.xml`, which points at
   `com.cw_malabe_tutuk2.MainApplication` as the main class.

4. The main inventory/POS window should open, titled **"Malabe Spares Depot"**.

## How to Run the Tests

JUnit Jupiter 5.12.1 is declared as a test-scoped dependency in `pom.xml`, so tests
can be run with the standard Maven test lifecycle:

```bash
mvn test
```

## Assumptions

The following assumptions were made based on the code, since some referenced
resources/files were not part of what was reviewed:

1. **FXML files exist as resources.** `MainApplication.java` loads
   `MainWindow.fxml`, `UpdateData.fxml`, and `Dealers_Deatails.fxml` via
   `getResource(...)`. These `.fxml` files must be present alongside the compiled
   classes (typically under `src/main/resources/com/cw_malabe_tutuk2/` or
   `src/main/java/com/cw_malabe_tutuk2/` if resources aren't separated) — they were
   not included in the files reviewed for this README.
2. **Working directory is the project root.** `FileHandler` and `Controller` use
   *relative* paths such as `src/main/java/com/cw_malabe_tutuk2/data/...`. The
   application must therefore be launched with the project root as the current
   working directory (this is the default when running via `mvn javafx:run` from
   the root).
3. **Data file format / precedence:**
   - If `newinventory.txt` exists, it is used and parsed as **9 comma/semicolon/pipe
     separated fields** (`code, name, brand, price, quantity, type, date, image,
     minThreshold`).
   - Otherwise, `inventory_legacy.txt` is used and parsed as the **8-field legacy
     format** (no `minThreshold` column; defaults to `0`).
   - `dealers_legacy.txt` must contain **4 fields** per line: `supplierId, name,
     contactInfo, location`.
   - If none of these files exist, the app logs a message to the console and
     starts with an empty inventory/dealer list rather than failing.
4. **Product images** referenced by the `image` field must be placed in
   `src/main/java/com/cw_malabe_tutuk2/data/Images/` and be `.jpg`, `.jpeg`, or
   `.png` files; missing images are simply displayed as blank cells.
5. **Audit log and `newinventory.txt` are auto-created/overwritten** by
   `FileHandler.DataWriter` / `FileHandler.AuditLogger` on every add/update/delete/
   checkout action, so write permissions on the `data` directory are required.
6. **Single-user, single-instance usage** is assumed — there is no file locking or
   concurrency handling for the text data files.

## Known Variance to Be Aware Of

- `pom.xml` targets Java **8** for compilation, but declares JavaFX **21** as a
  dependency, which is not compatible with JDK 8 at runtime. Use **JDK 17+** and,
  if needed, update `<source>`/`<target>` in `pom.xml` to `17` to avoid build
  failures.
