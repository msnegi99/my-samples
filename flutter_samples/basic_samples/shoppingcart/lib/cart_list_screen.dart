import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:shoppingcart/cartmodel.dart';
import 'package:shoppingcart/db_helper.dart';
import 'package:shoppingcart/db_provider.dart';

class CartListScreen extends StatefulWidget {
  const CartListScreen({super.key});

  @override
  State<CartListScreen> createState() => _CartListScreenState();
}

class _CartListScreenState extends State<CartListScreen> {
  DbHelper dbHelper = DbHelper();

  @override
  Widget build(BuildContext context) {
    var cart = Provider.of<DbProvider>(context);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blue,
        shadowColor: Colors.blue,
        elevation: 3,
        title: Text("Shopping Cart", style: TextStyle(color: Colors.white)),
        centerTitle: true,
        actions: [
          Stack(
            alignment: Alignment.topRight,
            children: [
              Icon(Icons.shopping_bag_outlined, color: Colors.white, size: 32),
              Badge(
                backgroundColor: Colors.red,
                smallSize: 16,
                alignment: Alignment.topRight,
                label: Consumer<DbProvider>(
                  builder: (context, value, child) {
                    return Text(
                      value.counter.toString(),
                      style: TextStyle(color: Colors.white),
                    );
                  },
                ),
              ),
            ],
          ),

          SizedBox(width: 10),
        ],
      ),
      body: Column(
        children: [
          FutureBuilder(
            future: cart.getDataList(),
            builder: (context, snapshot) {
              if (snapshot.data?.isEmpty ?? true) {
                return Expanded(
                  child: Center(
                    child: Text(
                      "Cart is Empty",
                      style: TextStyle(fontSize: 21),
                    ),
                  ),
                );
              }
              return Expanded(
                child: ListView.builder(
                  itemCount: snapshot.data!.length,
                  itemBuilder: (context, index) {
                    return Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Card(
                        child: Row(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Image(
                              image: NetworkImage(
                                snapshot.data![index].image.toString(),
                              ),
                              width: 100,
                              height: 100,
                            ),
                            SizedBox(width: 20),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  snapshot.data![index].productName.toString(),
                                  style: TextStyle(
                                    fontSize: 18,
                                    fontWeight: FontWeight.w500,
                                  ),
                                ),
                                Row(
                                  children: [
                                    Text(
                                      snapshot.data![index].unitTag.toString(),
                                      style: TextStyle(
                                        fontSize: 18,
                                        fontWeight: FontWeight.w500,
                                      ),
                                    ),
                                    SizedBox(width: 7),
                                    Text(
                                      "\$${snapshot.data![index].productPrice.toString()}",
                                      style: TextStyle(
                                        fontSize: 18,
                                        fontWeight: FontWeight.w500,
                                      ),
                                    ),
                                  ],
                                ),
                              ],
                            ),
                            Expanded(
                              child: Column(
                                children: [
                                  Align(
                                    alignment: Alignment.topRight,
                                    child: InkWell(
                                      onTap: () {
                                        dbHelper
                                            .deleteData(
                                              snapshot.data![index].id!,
                                            )
                                            .then((value) {
                                              cart.removeCounter();
                                              cart.removeTotalPrice(
                                                double.parse(
                                                  snapshot
                                                      .data![index]
                                                      .productPrice
                                                      .toString(),
                                                ),
                                              );
                                            });
                                      },
                                      child: Icon(Icons.delete),
                                    ),
                                  ),
                                  SizedBox(height: 10),
                                  Align(
                                    alignment: Alignment.centerRight,
                                    child: Container(
                                      padding: EdgeInsets.all(7),
                                      width: 100,
                                      decoration: BoxDecoration(
                                        color: Colors.green,
                                        borderRadius: BorderRadius.circular(7),
                                      ),
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceEvenly,
                                        children: [
                                          InkWell(
                                            onTap: () async {
                                              int quantity =
                                                  snapshot
                                                      .data![index]
                                                      .quantity!;
                                              quantity++;
                                              int productPrice =
                                                  snapshot
                                                      .data![index]
                                                      .productPrice!;
                                              productPrice =
                                                  snapshot
                                                      .data![index]
                                                      .initialPrice! *
                                                  quantity;
                                              dbHelper.updateQuantity(
                                                Cartmodel(
                                                  id: snapshot.data![index].id,
                                                  productId:
                                                      snapshot
                                                          .data![index]
                                                          .productId,
                                                  productName:
                                                      snapshot
                                                          .data![index]
                                                          .productName,
                                                  initialPrice:
                                                      snapshot
                                                          .data![index]
                                                          .initialPrice,
                                                  productPrice: productPrice,
                                                  quantity: quantity,
                                                  unitTag:
                                                      snapshot
                                                          .data![index]
                                                          .unitTag,
                                                  image:
                                                      snapshot
                                                          .data![index]
                                                          .image,
                                                ),
                                              );
                                              cart.addTotalPrice(
                                                double.parse(
                                                  snapshot
                                                      .data![index]
                                                      .initialPrice
                                                      .toString(),
                                                ),
                                              );
                                            },
                                            child: Text(
                                              "+",
                                              style: TextStyle(
                                                color: Colors.white,
                                                fontSize: 18,
                                              ),
                                            ),
                                          ),
                                          Text(
                                            snapshot.data![index].quantity
                                                .toString(),
                                            style: TextStyle(
                                              color: Colors.white,
                                              fontSize: 18,
                                            ),
                                          ),
                                          InkWell(
                                            onTap: () async {
                                              int quantity =
                                                  snapshot
                                                      .data![index]
                                                      .quantity!;
                                              quantity--;
                                              if (quantity > 0) {
                                                int productPrice =
                                                    snapshot
                                                        .data![index]
                                                        .productPrice!;
                                                productPrice =
                                                    snapshot
                                                        .data![index]
                                                        .initialPrice! *
                                                    quantity;
                                                dbHelper.updateQuantity(
                                                  Cartmodel(
                                                    id:
                                                        snapshot
                                                            .data![index]
                                                            .id,
                                                    productId:
                                                        snapshot
                                                            .data![index]
                                                            .productId,
                                                    productName:
                                                        snapshot
                                                            .data![index]
                                                            .productName,
                                                    initialPrice:
                                                        snapshot
                                                            .data![index]
                                                            .initialPrice,
                                                    productPrice: productPrice,
                                                    quantity: quantity,
                                                    unitTag:
                                                        snapshot
                                                            .data![index]
                                                            .unitTag,
                                                    image:
                                                        snapshot
                                                            .data![index]
                                                            .image,
                                                  ),
                                                );
                                                cart.removeTotalPrice(
                                                  double.parse(
                                                    snapshot
                                                        .data![index]
                                                        .initialPrice
                                                        .toString(),
                                                  ),
                                                );
                                              }
                                            },
                                            child: Text(
                                              "-",
                                              style: TextStyle(
                                                color: Colors.white,
                                                fontSize: 18,
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),

                            SizedBox(width: 10),
                          ],
                        ),
                      ),
                    );
                  },
                ),
              );
            },
          ),

          Visibility(
            visible: cart.totalPrice == 0.0 ? false : true,
            child: Column(
              children: [
                ReusableRow(title: "Sub Total", totalPrice: cart.totalPrice),
                SizedBox(height: 10),
                ReusableRow(
                  title: "Discount 5%",
                  totalPrice: cart.totalPrice * 0.05,
                ),
                SizedBox(height: 10),
                ReusableRow(
                  title: "Total",
                  totalPrice: cart.totalPrice - cart.totalPrice * 0.05,
                ),
              ],
            ),
          ),
          SizedBox(height: 30),
        ],
      ),
    );
  }
}

class ReusableRow extends StatelessWidget {
  final String? title;
  final double? totalPrice;
  const ReusableRow({required this.title, required this.totalPrice, super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(title.toString()),
          Text("\$${totalPrice!.toStringAsFixed(2)}"),
        ],
      ),
    );
  }
}
