#  🚉 Transit Route Planner

## 📍 Overview

The Transit Route Planner is a JavaFX-based desktop application that allows users to find and visualize routes between transit stations on a transport network. It uses a graph-based data model to represent 
stations and connections, and supports functionalities such as viewing routes by specific lines, finding the shortest path between stations, and calculating travel costs based on geographical distances.

## 🚀 Features

- 🧭 **Station-to-Station Routing**: Find direct or multi-line paths between any two transit stations.
- 🧮 **Cost Calculation**: Calculate total cost based on geographical distance between stations, using the Haversine formula.
- 📈 **Shortest Path**: Uses a BFS-style algorithm to determine the shortest available route.
- 📋 **Multiple Route Display**: Lists all discovered routes with number of stops and station names.
- 🧵 **Multi-line Traversal Support**: Detects and displays routes that span across multiple lines.
- 🎛️ **Interactive UI**: Built with JavaFX, allowing selection of routes, stations, and viewing of cost details dynamically.

## 🔧 Technologies Used

- Java 17+
- JavaFX
- OOP Design (Generics, Graphs)
- Java Collections (Map, List, Queue)


## 💡 How It Works

1. **Data Representation**:
   - Stations are represented by a generic `GraphNodes<Stations>` class.
   - Each station contains ID, name, and geographical coordinates.

2. **Routing**:
   - Routes are searched via a line-aware depth-first traversal (`traverseGraphByLine()`).
   - Shortest route discovery is done using a queue-based method (`traverseGraphByLineB()`).

3. **Cost Calculation**:
   - The application uses the Haversine formula to compute distances.
   - Each kilometer is multiplied by a constant factor (1.2) to simulate real-world travel costs.

4. **UI Components**:
   - Users select start and destination stations from dropdowns.
   - Available routes are displayed in a `ListView`.
   - Route cost and total number of stops are shown dynamically upon selection.

## 🖥️ Usage

### 🏁 Running the Application

1. Clone the repository.
2. Import the project into an IDE like IntelliJ or Eclipse.
3. Make sure JavaFX is set up in your module/library path.
4. Run the `Main` class (or the relevant JavaFX launcher class).

### 🕹️ UI Controls

- **Start Station / Destination Station**: Select these from dropdown menus.
- **"Find Route" Buttons**:
  - `stationToStationByLine()`: Find paths within a single line.
  - `routeByAnyLine()`: Discover paths spanning multiple lines.
  - `findShortestRoute()`: Compute the shortest route using breadth-first search.
- **"Calculate Cost"**:
  - Displays the route cost and number of stops for the selected route.

## 📌 Known Limitations / To-Do

- Improve handling of multi-line intersections and transfers.
- Enhance user feedback for invalid or disconnected station pairs.
- Optimize route finding logic for larger networks (possibly Dijkstra's or A*).

