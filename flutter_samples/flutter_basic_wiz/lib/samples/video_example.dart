import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPage extends StatefulWidget {
  const VideoPage({super.key, required this.title}); // The widget itself is immutable
  final String title;

  @override
  State<VideoPage> createState() => _VideoPageState(); // Creates the mutable state object
}

class _VideoPageState extends State<VideoPage> {

  late VideoPlayerController _controller = VideoPlayerController.network(
    'https://flutter.github.io/assets-for-api-docs/assets/videos/bee.mp4',
  );

  late Future<void> _initializeVideoPlayerFuture = _controller.initialize();

  @override
  void initState() {
    super.initState();
    _controller.setLooping(true);
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          foregroundColor: Colors.white,
          title: new Text(widget.title),
        ),
        body: FutureBuilder(
          future: _initializeVideoPlayerFuture,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              return AspectRatio(
                aspectRatio: _controller.value.aspectRatio,
                child: VideoPlayer(_controller),
              );
            } else if (snapshot.hasError) {
              return Center(
                child: Text('Error loading video: ${snapshot.error}'),
              );
            } else {
              return const Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    CircularProgressIndicator(),
                    SizedBox(height: 16),
                    Text('Loading video...'),
                  ],
                ),
              );
            }
          },
        ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          setState(() {
            if (_controller.value.isPlaying) {
              _controller.pause();
            } else {
              _controller.play();
            }
          });
        },
        child: Icon(
          _controller.value.isPlaying ? Icons.pause : Icons.play_arrow,
        ),
      ),
    );
  }

}