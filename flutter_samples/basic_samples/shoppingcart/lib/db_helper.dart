import 'package:shoppingcart/cartmodel.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class DbHelper {
  Database? _database;

  Future<Database> get db async {
    if (_database != null) {
      return _database!;
    }
    _database = await initDatabse();
    return _database!;
  }

  Future<Database> initDatabse() async {
    var path = join(await getDatabasesPath(), "cart.db");
    return await openDatabase(
      path,
      version: 1,
      onCreate: (db, version) {
        return db.execute(
          "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, productId VARCHAR UNIQUE, productName TEXT, initialPrice INTEGER, productPrice INTEGER, quantity INTEGER, unitTag TEXT, image TEXT)",
        );
      },
    );
  }

  Future<Cartmodel> insertData(Cartmodel cartmodel) async {
    var dbHelper = await db;
    await dbHelper.insert('cart', cartmodel.toMap());
    return cartmodel;
  }

  Future<List<Cartmodel>> getData() async {
    var dbHelper = await db;
    Future<List<Map<String, Object?>>> dataList = dbHelper.query("cart");
    return dataList.then((value) {
      return value.map((e) => Cartmodel.fromMap(e)).toList();
    });
  }

  Future<int> deleteData(int id) async {
    var dbHelper = await db;
    return await dbHelper.delete('cart', where: "id = ?", whereArgs: [id]);
  }

  Future<int> updateQuantity(Cartmodel cartmodel) async {
    var dbHelper = await db;
    return await dbHelper.update(
      'cart',
      cartmodel.toMap(),
      where: "id=?",
      whereArgs: [cartmodel.id],
    );
  }
}
