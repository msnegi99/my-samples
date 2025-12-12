import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:shoppingcart/cart_list_screen.dart';
import 'package:shoppingcart/cartmodel.dart';
import 'package:shoppingcart/db_helper.dart';
import 'package:shoppingcart/db_provider.dart';

class Productlist extends StatefulWidget {
  const Productlist({super.key});

  @override
  State<Productlist> createState() {
    return _ProductListState();
  }
}

class _ProductListState extends State<Productlist> {
  DbHelper dbHelper = DbHelper();

  final List<String> productNames = [
    "Apple",
    "Banana",
    "Orange",
    "Mango",
    "Strawberry",
    "Grapes",
    "Pineapple",
    "Papaya",
    "Kiwi",
  ];

  final List<String> productUnits = [
    "kg",
    "dozen",
    "kg",
    "kg",
    "box",
    "kg",
    "piece",
    "piece",
    "piece",
  ];

  final List<double> productPrices = [3, 2, 4, 5, 6, 4, 3, 4, 1];

  final List<String> productImages = [
    "https://upload.wikimedia.org/wikipedia/commons/1/15/Red_Apple.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/8/8a/Banana-Single.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/c/c4/Orange-Fruit-Pieces.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/9/90/Hapus_Mango.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/2/29/PerfectStrawberry.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/3/36/Kyoho-grape.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/c/cb/Pineapple_and_cross_section.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/6/6b/Papaya_cross_section_BNC.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/d/d3/Kiwi_aka.jpg",
  ];

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
          InkWell(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (_) => CartListScreen()),
              );
            },
            child: Stack(
              alignment: Alignment.topRight,
              children: [
                Icon(
                  Icons.shopping_bag_outlined,
                  color: Colors.white,
                  size: 32,
                ),
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
          ),

          SizedBox(width: 10),
        ],
      ),

      body: ListView.builder(
        itemCount: productNames.length,
        itemBuilder: (context, index) {
          return Padding(
            padding: const EdgeInsets.all(8.0),
            child: Card(
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Image(
                    image: NetworkImage(productImages[index]),
                    width: 100,
                    height: 100,
                  ),
                  SizedBox(width: 20),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        productNames[index],
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      Row(
                        children: [
                          Text(
                            productUnits[index],
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.w500,
                            ),
                          ),
                          SizedBox(width: 7),
                          Text(
                            "\$${productPrices[index]}",
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
                    child: Align(
                      alignment: Alignment.topRight,
                      child: InkWell(
                        onTap: () async {
                          await dbHelper
                              .insertData(
                                Cartmodel(
                                  id: index,
                                  productId: index.toString(),
                                  productName: productNames[index].toString(),
                                  initialPrice: productPrices[index].toInt(),
                                  productPrice: productPrices[index].toInt(),
                                  quantity: 1,
                                  unitTag: productUnits[index].toString(),
                                  image: productImages[index].toString(),
                                ),
                              )
                              .then((value) {
                                cart.addCounter();
                                cart.addTotalPrice(productPrices[index]);
                                print("added to cart");
                              })
                              .onError((e, _) {
                                print("error is $e");
                              });
                        },
                        child: Container(
                          padding: EdgeInsets.all(7),
                          decoration: BoxDecoration(
                            color: Colors.green,
                            borderRadius: BorderRadius.circular(7),
                          ),
                          child: Text("Add To Cart"),
                        ),
                      ),
                    ),
                  ),
                  SizedBox(width: 7),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
