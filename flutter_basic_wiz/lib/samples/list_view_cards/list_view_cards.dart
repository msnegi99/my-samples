import 'package:flutter/material.dart';
import 'modal/contact.dart';
import 'modal/contact_list.dart';

class ListViewCardsPage extends StatefulWidget {
  const ListViewCardsPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<ListViewCardsPage> createState() => _ListViewCardsPageState(); // Creates the mutable state object
}

class _ListViewCardsPageState extends State<ListViewCardsPage> {

  _buildContactList() {
    return <ContactModal>[
      const ContactModal(fullName: 'Romain Hoogmoed', email: 'romain.hoogmoed@example.com'),
      const ContactModal(fullName: 'Emilie Olsen', email: 'emilie.olsen@example.com'),
      const ContactModal(fullName: 'Nishant Srivastava', email: 'nishant.srivastava@example.com'),
      const ContactModal(fullName: 'Romain Hoogmoed', email: 'romain.hoogmoed@example.com'),
      const ContactModal(fullName: 'Emilie Olsen', email: 'emilie.olsen@example.com'),
      const ContactModal(fullName: 'Nishant Srivastava', email: 'nishant.srivastava@example.com'),
      const ContactModal(fullName: 'Romain Hoogmoed', email: 'romain.hoogmoed@example.com'),
      const ContactModal(fullName: 'Emilie Olsen', email: 'emilie.olsen@example.com'),
      const ContactModal(fullName: 'Nishant Srivastava', email: 'nishant.srivastava@example.com'),
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        foregroundColor: Colors.white,
        title: new Text(widget.title),
      ),
      body: ContactsList(_buildContactList()),
    );
  }

}