import 'package:flutter/material.dart';

class BottomNavigation extends StatelessWidget
{
  @override
  Widget build(BuildContext context) {
    return Container(
      child: BottomNavigationBar(
          showUnselectedLabels: false,
          showSelectedLabels: false,
          items:const  [
        BottomNavigationBarItem(label: "HH",icon: Icon(Icons.home_filled,color: Colors.blue,)),
        BottomNavigationBarItem(label: "HH",icon: Icon(Icons.calendar_month_rounded,color: Colors.black,)),
        BottomNavigationBarItem(label: "HH",icon: Icon(Icons.whatshot_outlined,color: Colors.black,)),
        BottomNavigationBarItem(label: "HH",icon: Icon(Icons.account_circle_outlined,color: Colors.black,)),
      ]),
    );
  }
}

class BottomNavigationText extends StatelessWidget
{
  BottomNavigationText({super.key});

  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    _selectedIndex = index;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: BottomNavigationBar(
          items:const <BottomNavigationBarItem>[
            BottomNavigationBarItem(icon: Icon(Icons.home),label: 'Home',),
            BottomNavigationBarItem(icon: Icon(Icons.search),label: 'Search',),
            BottomNavigationBarItem(icon: Icon(Icons.person),label: 'Profile',),
          ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,),
    );
  }
}

