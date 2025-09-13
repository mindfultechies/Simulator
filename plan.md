# Analytics Simulator Conversion to Java

## Project Overview
The analytics simulator is a tool for generating simulated events (CDR, device, SSE) and sending them to Kafka topics based on configurable templates. The Python version uses a modular architecture with core components and pluggable simulators.

## Current Python Structure
- **core/**: Engine, Loader, Scheduler, KafkaIO
- **simulators/**: BaseSimulator, CDRSimulator, DeviceSimulator, SSESimulator, Registry
- **templates/**: JSON configuration files
- Dependencies: kafka-python, faker

## Proposed Java Architecture

### Build System
- **Maven** for dependency management and build
- Standard Maven directory structure: `src/main/java`, `src/main/resources`

### Package Structure
```
src/main/java/com/analytics/simulator/
├── Main.java                    # Entry point
├── core/
│   ├── Engine.java              # Main orchestrator
│   ├── Loader.java              # JSON template loader
│   ├── Scheduler.java           # Rate controller
│   └── KafkaIO.java             # Kafka producer utilities
└── simulators/
    ├── BaseSimulator.java       # Abstract base class
    ├── CDRSimulator.java        # CDR event generator
    ├── DeviceSimulator.java     # Device event generator
    ├── SSESimulator.java        # SSE event generator
    └── Registry.java            # Simulator factory/registry
```

### Dependencies
- `org.apache.kafka:kafka-clients` - Kafka producer
- `com.fasterxml.jackson.core:jackson-databind` - JSON parsing
- `com.github.javafaker:javafaker` - Fake data generation

### Key Design Decisions
1. **Registry Pattern**: Use a Map<String, Supplier<BaseSimulator>> for simulator creation
2. **Template Handling**: Use Jackson ObjectMapper for JSON parsing, store as Map<String, Object>
3. **Rate Control**: Implement with Thread.sleep() between events
4. **Event Generation**: Convert faker calls to JavaFaker equivalents
5. **Error Handling**: Use try-catch blocks with proper logging
6. **Configuration**: Keep templates as JSON files in resources

### Conversion Mapping
| Python Component | Java Equivalent | Notes |
|------------------|-----------------|--------|
| dict | Map<String, Object> | For templates |
| str(uuid.uuid4()) | UUID.randomUUID().toString() | |
| faker.msisdn() | faker.phoneNumber().phoneNumber() | |
| datetime.utcnow().isoformat() | Instant.now().toString() | |
| random.randint() | ThreadLocalRandom.current().nextInt() | |
| time.sleep() | Thread.sleep() | |
| KafkaProducer | KafkaProducer<String, String> | JSON serialized |

### Implementation Steps
1. Set up Maven project structure
2. Convert core classes one by one
3. Convert simulator classes
4. Implement main entry point
5. Test with existing templates
6. Configure build and dependencies

### Potential Challenges
- Kafka producer configuration might need adjustment
- JavaFaker API differences from Python faker
- Exception handling for I/O operations
- Threading model for rate control

## Mermaid Diagram

```mermaid
graph TD
    A[Main] --> B[Engine]
    B --> C[Loader]
    B --> D[Registry]
    B --> E[KafkaIO]
    B --> F[Scheduler]
    D --> G[CDRSimulator]
    D --> H[DeviceSimulator]
    D --> I[SSESimulator]
    G --> J[BaseSimulator]
    H --> J
    I --> J
    C --> K[Template JSON]
    E --> L[Kafka Producer]
    F --> M[Rate Controller]