# RageElement - Custom ProjectKorra Element

A custom Rage element plugin for ProjectKorra 1.12.0 on Spigot 1.20.4.

## Abilities

### Outrage
Creates an explosion that flies out 7 blocks in a random direction, knocking back the user and dealing 5 damage to anything it hits.
- **Activation:** Left click

### Berserk (Passive)
When the user falls below half health, their movement speed is doubled and their damage gets a 1.5x multiplier, but their jump height is halved.
- **Activation:** Passive

### Fury Hook
Summons particles slightly behind the user's right arm, which stay until right click is released. Upon release, the particles move in a large arc in front of the user. The longer right click is held, the more damage this does, gaining 1 damage every 2 seconds for up to 6 damage.
- **Activation:** Hold right click to charge, release to attack

### Berserker Tackle
User dashes in the direction they're looking extremely fast for a distance of up to 20 blocks. If the user hits an enemy, that enemy is stunned for 1 second and takes 2 hearts of damage. If the user does not hit an enemy, the user is stunned for 1 second and takes 1 heart of damage.
- **Activation:** Left click

### Furious Roar
User lets out a loud yell that creates a slowly expanding shockwave in the direction they're looking, which deals 3 damage.
- **Activation:** Left click

### Boxer's Gambit
Throws out a line of particles 6 blocks. Increases speed for 20 seconds if it lands. Gives you slowness for 10 seconds if it misses.
- **Activation:** Left click

### Wild Swipe
Throws out three swipes which are slightly further away from the user on each swipe, each dealing 1 damage and coming from the side opposite to the last swipe.
- **Activation:** Left click

### Despise the Weak
Standard punch that does 3 damage to enemies, but does 4 damage to the user if the target is above 6 hearts.
- **Activation:** Left click

### Flex
User stands and flexes their muscles for two seconds. If they take no damage during this time, they gain Strength 2 for 10 seconds.
- **Activation:** Shift to activate

### Chaotic Stomp
User stomps the ground, creating randomly placed 3 block tall pillars in a 15 block radius around the stomp.
- **Activation:** Left click

### Flying Elbow
User creates a small explosion below their feet, which deals 1 damage to both the user and enemies and sends the user up into the air. After a moment, the user flies diagonally downward in the direction they're looking, creating another explosion and dealing 2 hearts to any enemy hit.
- **Activation:** Left click

## Building

### Requirements
- Java 17 or higher
- Maven 3.6 or higher

### Build Steps
1. Navigate to the RageElement directory
2. Run: `mvn clean package`
3. The compiled JAR will be in the `target` folder

## Installation

1. Build the plugin using Maven
2. Copy `RageElement-1.0.0.jar` from the `target` folder to your server's `plugins` folder
3. Ensure ProjectKorra 1.12.0 is already installed
4. Restart your server
5. Configure abilities in `plugins/RageElement/config.yml`

## Configuration

All abilities can be configured in `config.yml`. You can adjust:
- Cooldowns
- Damage values
- Ranges
- Duration
- Special ability-specific parameters

## Dependencies

- Spigot 1.20.4
- ProjectKorra 1.12.0

## Version

1.0.0

## Author

Created for custom ProjectKorra server gameplay.
