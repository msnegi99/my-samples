// lib/services/database_helper.dart

import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

import '../Models/TaskModel.dart';


class DatabaseHelper {
  static final DatabaseHelper _instance = DatabaseHelper._internal();
  factory DatabaseHelper() => _instance;
  DatabaseHelper._internal();

  static Database? _database;

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDb();
    return _database!;
  }

  Future<Database> _initDb() async {
    String path = join(await getDatabasesPath(), 'todo_app.db');
    return await openDatabase(
      path,
      version: 2, // <-- Version ko 1 se 2 kar dein
      onCreate: _onCreate,
      onUpgrade: _onUpgrade, // <-- Naya function add karein
    );
  }

// Ye Naya Function Add Karein
  Future _onUpgrade(Database db, int oldVersion, int newVersion) async {
    if (oldVersion < 2) {
      await db.execute("ALTER TABLE tasks ADD COLUMN dueDate TEXT NOT NULL DEFAULT 'No Date'");
    }
  }

  Future _onCreate(Database db, int version) async {
    await db.execute('''
    CREATE TABLE tasks (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT NOT NULL,
      dueDate TEXT NOT NULL, -- Naya column add kiya
      isDone INTEGER NOT NULL
    )
  ''');
  }
  // CRUD Operations (Create, Read, Update, Delete)

  // Naya Task Add Karna
  Future<int> insertTask(TaskModel task) async {
    Database db = await database;
    return await db.insert('tasks', task.toMap());
  }

  // Saray Tasks Get Karna
  Future<List<TaskModel>> getTasks() async {
    Database db = await database;
    final List<Map<String, dynamic>> maps = await db.query('tasks');
    return List.generate(maps.length, (i) {
      return TaskModel.fromMap(maps[i]);
    });
  }

  // Task ko Update Karna
  Future<int> updateTask(TaskModel task) async {
    Database db = await database;
    return await db.update(
      'tasks',
      task.toMap(),
      where: 'id = ?',
      whereArgs: [task.id],
    );
  }

  // Task ko Delete Karna
  Future<int> deleteTask(int id) async {
    Database db = await database;
    return await db.delete(
      'tasks',
      where: 'id = ?',
      whereArgs: [id],
    );
  }
}