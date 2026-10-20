# SwiftBot-Secure-Morse-Messenger
This is a Java project built for the physical SwiftBot platform (a Raspberry Pi-driven robot). It was created as part of a first-year university robotics assignment

Note: This repository is a code showcase. It depends on the physical `swiftbot.SwiftBotAPI` library and hardware sensors, so it cannot be run locally without the physical robot.

## Overview
The robot acts as a physical courier moving between three network nodes (A, B, and C).

- Authentication: The sender scans a QR code (CALLSIGN:LOCATION) to unlock the robot at its starting position.
- Morse Input: The user inputs a message in Morse code using the physical buttons on top of the bot.
- Movement: The bot calculates its route to the target node, driving there while constantly checking an ultrasonic sensor for obstacles.
- Delivery: Upon arrival, the recipient presents their QR code to verify their identity. Once verified, the bot flashes the Morse message using its RGB underlights.
- Return Journey: The bot uses a saved movement stack to reverse its path back to the origin node.
