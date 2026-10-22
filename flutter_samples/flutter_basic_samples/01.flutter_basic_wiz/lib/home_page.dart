import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_basic_wiz/samples/alert_dialog_example.dart';
import 'package:flutter_basic_wiz/samples/carousel_slider_example.dart';
import 'package:flutter_basic_wiz/samples/checkbox_example.dart';
import 'package:flutter_basic_wiz/samples/clip_rect_example.dart';
import 'package:flutter_basic_wiz/samples/clipper_example.dart';
import 'package:flutter_basic_wiz/samples/date_picker_example.dart';
import 'package:flutter_basic_wiz/samples/drop_down_example.dart';
import 'package:flutter_basic_wiz/samples/expandid_example.dart';
import 'package:flutter_basic_wiz/samples/font_example.dart';
import 'package:flutter_basic_wiz/samples/grid_view_api.dart';
import 'package:flutter_basic_wiz/samples/grid_view_manual.dart';
import 'package:flutter_basic_wiz/samples/list_view_cards/list_view_cards.dart';
import 'package:flutter_basic_wiz/samples/listview_expansion_cards/expansion_tile_card.dart';
import 'package:flutter_basic_wiz/samples/load_asset_json.dart';
import 'package:flutter_basic_wiz/samples/load_local_image.dart';
import 'package:flutter_basic_wiz/samples/load_network_image.dart';
import 'package:flutter_basic_wiz/samples/page_structure_example.dart';
import 'package:flutter_basic_wiz/samples/progress_bar_example.dart';
import 'package:flutter_basic_wiz/samples/radio_button_example.dart';
import 'package:flutter_basic_wiz/samples/snack_bar_example.dart';
import 'package:flutter_basic_wiz/samples/staggered_grid_example.dart';
import 'package:flutter_basic_wiz/samples/time_picker_example.dart';
import 'package:flutter_basic_wiz/samples/video_example.dart';
import 'samples/bottom_sheet_example.dart';
import 'samples/list_view_example.dart';

class MyHomePage extends StatefulWidget {
  static const String routeName = '/home-screen';

  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  void exitDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Exit App?'),
        content: const Text('Do you want to exit the application?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false), // Don't exit
            child: const Text('No'),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true), // Exitexit(0)
            child: const Text('Yes'),
          ),
        ],
      ),
    ).then((value) {
      if (value == true) {
        // Exit the app (e.g., SystemNavigator.pop() for Android)
        // Note: SystemNavigator.pop() is for Android; iOS handles app exit differently.
        SystemNavigator.pop();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Container(
          decoration: BoxDecoration(
            image: DecorationImage(
              image: AssetImage('assets/images/city.jpg'),
              fit: BoxFit.cover,
            ),
          ),
        ),
        PopScope(
          canPop: false,
          onPopInvoked: (didPop) async {
            if (didPop)
              return; // If the system already handled the pop, do nothing
            if (!Navigator.of(context).canPop()) {
              // This means we are on the home screen (root route)
              // You can show an exit confirmation dialog here, or simply exit the app.
              exitDialog(context);
            } else {
              // We are not on the home screen, so perform regular pop
              Navigator.of(context).pop();
            }
          },
          child: Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.black,
              foregroundColor: Colors.white,
              title: new Text(widget.title),
            ),
            body: ListView(
              shrinkWrap: true,
              children: <Widget>[

                new ListTile(
                  title: Text('ListView'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            ListViewWidgetPage(title: 'ListView Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('BottomSheet'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            BottomSheetPage(title: 'BottomSheet Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('DropDown'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            DropDownPage(title: 'DropDown Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('SnackBar'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            SnackBarPage(title: 'SnackBar Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Clipper'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            ClipperPage(title: 'Clipper Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('GridView Manual'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            GridViewManualPage(title: 'GridView Manual Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('GridView API'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            GridViewPage(title: 'GridView API Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Load Asset JSON'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            AssetJSONPage(title: 'Asset JSON Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Load Asset Image'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            LoadImagePage(title: 'Asset Image Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Load Network Image'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            NetworkImagePage(title: 'Network Image Demo'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Font Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => FontPage(title: 'Font Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('List View Card Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            ListViewCardsPage(title: 'List View Card Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('CheckBox Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            CheckBoxPage(title: 'CheckBox Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Carousel Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            CarouselPage(title: 'Carousel Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('ClipRRect Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            ClipRRectPage(title: 'ClipRRect Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Expandid Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            ExpandidPage(title: 'Expandid Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('StaggeredGrid Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            StaggeredGridPage(title: 'StaggeredGrid Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Page Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => AssistPage(title: 'Page Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('ProgressBar Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            ProgressBarPage(title: 'ProgressBar Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('AlertDialog Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) =>
                            AlertDialogPage(title: 'AlertDialog Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('Video Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => VideoPage(title: 'Video Example'),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('ExpansionCards Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => ListViewExpansionCardsPage(
                          title: 'ExpansionCards Example',
                        ),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('RadioButton Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => RadioButtonPage(
                          title: 'RadioButton Example',
                        ),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('DatePicker Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => DatePickerPage(
                          title: 'DatePicker Example',
                        ),
                      ),
                    );
                  },
                ),
                new ListTile(
                  title: Text('TimePicker Example'),
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (context) => TimePickerPage(
                          title: 'TimePicker Example',
                        ),
                      ),
                    );
                  },
                ),

              ],
            ),
          ),
        ),
      ],
    );
  }
}
