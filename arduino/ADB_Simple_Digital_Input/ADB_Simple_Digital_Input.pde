#include <SPI.h>
#include <Adb.h>

#define  BUTTON1        2
#define  BUTTON2        3

Connection * connection;

// Bytes for each button state
byte b1, b2;

// Event handler for shell connection; called whenever data sent from Android to Microcontroller
void adbEventHandler(Connection * connection, adb_eventType event, uint16_t length, uint8_t * data)
{
  if (event == ADB_CONNECTION_RECEIVE)
  {
  // Unused in this case
  }
}

void setup()
{
  // Set pins as input
  pinMode(BUTTON1, INPUT);
  pinMode(BUTTON2, INPUT);

  // Enable the internal pullups
  digitalWrite(BUTTON1, HIGH);
  digitalWrite(BUTTON2, HIGH);
  
  // Init serial port for debugging
  Serial.begin(57600);

  // Init the ADB subsystem.  
  ADB::init();

  // Open an ADB stream to the phone's shell. Auto-reconnect. Use port number 4568
  connection = ADB::addConnection("tcp:4567", true, adbEventHandler);  
}

void loop()
{
  byte b;
  byte msg[2];

  b = digitalRead(BUTTON1);
  if (b != b1) {
    msg[0] = BUTTON1;
    msg[1] = b ? 0 : 1;
    Serial.println(msg[0],DEC);
    connection->write(2, (uint8_t*)&msg);
    b1 = b;
  }

  b = digitalRead(BUTTON2);
  if (b != b2) {
    msg[0] = BUTTON2;
    msg[1] = b ? 0 : 1;
    Serial.println(msg[0],DEC);
    connection->write(2, (uint8_t*)&msg);
    b2 = b;
  }

  // Poll the ADB subsystem.
  ADB::poll();
}




