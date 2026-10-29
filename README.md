# DraftRoom — Interior Design Editor

A **Java Swing** desktop application for designing room layouts. Users organize their work as **projects → buildings → rooms**, then furnish each room on a 2D canvas by placing, moving, resizing, rotating and zooming furniture elements (bed, wardrobe, table, door, bathtub, sink, toilet, boiler, washing machine).

Built as a software design course project, with a strong focus on **clean architecture and object-oriented design patterns**.

## Features

- Project explorer tree (`JTree`) for creating, renaming and deleting projects, buildings and rooms
- 2D room editor with selection, move, resize, rotate (left/right), copy/paste, delete and zoom
- **Undo / redo** for all editing operations
- Save and open projects as **JSON** files, plus reusable room templates (save / load pattern)
- Automatic organization of room elements

## Architecture and Design Patterns

| Pattern | Where it is used |
|---|---|
| **MVC** | separation of model (`model/`), Swing views (`gui/`) and controllers/actions (`actions/`) |
| **State** | editor tools: select, add, move, resize, rotate, copy, paste, delete, zoom (`concrete/`, `StateManager`) |
| **Command** | undoable operations with a `CommandManager` history (`command/`) |
| **Composite** | project hierarchy as `DraftNode` / `DraftNodeComposite` / `DraftNodeLeaf` (`model/nodes/`) |
| **Observer** | model changes propagated to views through `IPublisher` / `ISubscriber` (`observer/`) |
| **Factory** | creation of tree nodes, room elements and their painters (`model/nodefactory/`, `RoomElementPainterFactory`) |
| **Singleton** | central `ApplicationFramework` and `MainFrame` |
| **Painter** | dedicated painter class per element type (`gui/swing/view/painters/`) |

Serialization uses **Jackson** with custom serializers for colors and polymorphic node types.

## Tech Stack

Java 22 · Swing · Maven · Jackson
