/** * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @file:  The utopia servrer print logo source cpp
 * Using MIT License
 * Copyright (c) 2020-2021 GOSCPS 保留所有权利.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include <string>
#include <iostream>
#include <sstream>

using namespace std;

namespace {
	string truecolor(unsigned char r, unsigned char g, unsigned char b) {
		ostringstream os;
		os << "\x1b[38;2;" << to_string(r) << ";" << to_string(g) << ";" << to_string(b) << "m";
		return os.str();
	}

	string on_truecolor(unsigned char r, unsigned char g, unsigned char b) {
		ostringstream os;
		os << "\x1b[48;2;" << to_string(r) << ";" << to_string(g) << ";" << to_string(b) << "m";
		return os.str();
	}

	constexpr char const* clear_style = "\x1b[0m";

	inline void print_blue(std::string str) {
		cout << truecolor(0, 0, 0) << on_truecolor(91, 207, 250)
			<< str
			<< clear_style << "\n";
	}


	inline void print_pink(std::string str) {
		cout << truecolor(0, 0, 0) << on_truecolor(245, 171, 185)
			<< str
			<< clear_style << "\n";
	}

	inline void print_white(std::string str) {
		cout << truecolor(0, 0, 0) << on_truecolor(255,255,255)
			<< str
			<< clear_style << "\n";
	}
}

namespace utopia {
	/// @brief 打印logo的函数
	void print_logo() {
		print_blue(" _    _ _              _          _____                          ");
		print_blue("| |  | | |            (_)        / ____|                         ");
		print_blue("| |  | | |_ ___  _ __  _  __ _  | (___   ___ _ ____   _____ _ __ ");
		print_blue("| |  | | __/ _ \\| '_ \\| |/ _` |  \\___ \\ / _ \\ '__\\ \\ / / _ \\ '__|");
		print_blue("| |__| | || (_) | |_) | | (_| |  ____) |  __/ |   \\ V /  __/ |   ");

		print_pink(" \\____/ \\__\\___/| .__/|_|\\__,_| |_____/ \\___|_|    \\_/ \\___|_|   ");
		print_pink("                | |                                              ");
		print_pink("                |_|                                              ");
		print_pink("              __  __           _        _                        ");
		print_pink("             |  \\/  |         | |      | |                       ");

		print_white("             | \\  / | __ _  __| | ___  | |__  _   _              ");
		print_white("             | |\\/| |/ _` |/ _` |/ _ \\ | '_ \\| | | |             ");
		print_white("             | |  | | (_| | (_| |  __/ | |_) | |_| |             ");
		print_white("             |_|  |_|\\__,_|\\__,_|\\___| |_.__/ \\__, |             ");
		print_white("                                               __/ |             ");

		print_pink("                                              |___/              ");
		print_pink("GOSCPS Team and its leader:                                      ");
		print_pink("             __  __ _             __  __                         ");
		print_pink("            |  \\/  (_)           |  \\/  |                        ");
		print_pink("            | \\  / |_ _ __   __ _| \\  / | ___   ___              ");

		print_blue("            | |\\/| | | '_ \\ / _` | |\\/| |/ _ \\ / _ \\             ");
		print_blue("            | |  | | | | | | (_| | |  | | (_) |  __/             ");
		print_blue("            |_|  |_|_|_| |_|\\__, |_|  |_|\\___/ \\___|             ");
		print_blue("                             __/ |                               ");
		print_blue("                            |___/                                ");
		return;
	}

}