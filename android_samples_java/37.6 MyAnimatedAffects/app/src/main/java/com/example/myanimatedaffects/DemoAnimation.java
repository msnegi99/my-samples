package com.example.myanimatedaffects;

public class DemoAnimation {
		public int id;
		public String title;
		public boolean selected = false;

		public DemoAnimation(int id, String title) {
			this.id = id;
			this.title = title;
		}

		@Override
		public String toString() {
			return title;
		}
	}