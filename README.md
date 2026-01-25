# Task Tracker CLI

Hello, there! 👋

This is a project following the Java Roadmap from https://roadmap.sh/projects/task-tracker

# Use

- Create: add <task-description>
- Update: update <id> <task-descriptio>
- List All: list
- List by status: list <status>
- Delete: delete <id>

# Features

- Builder pattern and inmutability for Task construction.
- Streams and Optionals for filtering tasks.
- Main class instantiation.
- Jackson for serialization and deserialization.
- All actions returns the Task handled.
- Custom Exceptions.

# Learnings

1. I find it interesting to mix functional and object-oriented programming. The former offers elegance and concision, while the latter helps maintain a readable and extensible code structure.
2. Immutability helps prevent unintended behavior.
3. Design patterns can be mixed and matched according to need.
4. Interfaces are useful for maintaining code coherence.
