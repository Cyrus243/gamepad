# Android Gamepad APP

## Overview
The **Android Gamepad APP** is a mobile application that allows users to control a mini-rover remotely using an Android device.It was designed to serve as a gamepad for my project named PathFinder, available on [GitHub](https://github.com/Cyrus243/PathFinder). The app connects to a server over a TCP socket and transmits control commands based on user input.

## Features

- **Real-time Control**: Send movement commands to the rover with low latency.
- **Intuitive Gamepad UI**: Emulates a traditional game controller layout.
- **Customizable Settings**: Configure IP address and port for TCP communication.
- **Responsive Feedback**: Display connection status.
- **Cross-Platform Compatibility**: Works with any server running a compatible TCP listener.

## Prerequisites

- An Android device (Android 6.0 or later)
- A TCP server capable of receiving and processing control commands
- A stable network connection (WiFi or mobile hotspot)

## Usage

1. **Start Server**: Ensure the TCP server is running and listening for incoming connections.
2. **Launch the App**: Open the app and enter the server's IP address and port.
3. **Connect**: Tap the connect button to establish a TCP connection.
4. **Control the Rover**:
   - Use the joystick or on-screen buttons to send movement commands.
   - Monitor the connection status on the UI.
5. **Disconnect**: Tap the disconnect button when done.

## Development

### Tech Stack

- **Android**: Kotlin + Jetpack Compose (UI)
- **Networking**: Java/Kotlin Socket API
- **Backend**: Python (Socket server handling commands)

### Building the Project

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/android-gamepad-mini-rover.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the app on a physical device.

## Future Improvements

- Bluetooth/BLE support configuration as an alternative connection method.
- Configurable button mappings.
- Support for additional sensors (e.g., camera streaming, telemetry data display).

## License

This project is open-source under the **MIT License**.

## Contact & Support

For issues or feature requests, create a GitHub issue or reach out via aaronsalo2016@gmail.com.

