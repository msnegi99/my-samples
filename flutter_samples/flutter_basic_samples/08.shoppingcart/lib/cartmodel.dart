class Cartmodel {
  final int? id;
  final String? productId;
  final String? productName;
  final int? initialPrice;
  final int? productPrice;
  final int? quantity;
  final String? unitTag;
  final String? image;

  Cartmodel({
    required this.id,
    required this.productId,
    required this.productName,
    required this.initialPrice,
    required this.productPrice,
    required this.quantity,
    required this.unitTag,
    required this.image,
  });

  factory Cartmodel.fromMap(Map<String, dynamic> mapData) {
    return Cartmodel(
      id: mapData["id"],
      productId: mapData["productId"],
      productName: mapData["productName"],
      initialPrice: mapData["initialPrice"],
      productPrice: mapData["productPrice"],
      quantity: mapData["quantity"],
      unitTag: mapData["unitTag"],
      image: mapData["image"],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'productId': productId,
      'productName': productName,
      'initialPrice': initialPrice,
      'productPrice': productPrice,
      'quantity': quantity,
      'unitTag': unitTag,
      'image': image,
    };
  }
}
