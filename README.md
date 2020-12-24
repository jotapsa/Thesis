### Wifi Signal Scan

https://developer.android.com/reference/android/net/wifi/WifiManager
https://developer.android.com/guide/topics/connectivity/wifi-scan#java

#### Permissions

ACCESS_FINE_LOCATION
CHANGE_WIFI_STATE
ACCESS_WIFI_STATE

Location services enabled

Also turn throttling off for the frequency of scans using WifiManager.startScan(). (under Developer Options > Networking > Wi-Fi scan throttling).
(Opções de Programador -> Redes -> Controlo da procura de Wi-Fi)

### Cellphone Signal Scan

https://github.com/usnistgov/LTECoverageTool
Maybe get information from other CellInfo Instances like Wcdma ?


Data Row
?????
Timestamp - Type of signal (3G / WIFI) - RSSI (dBm)
?????

?????
Take a sample every .5s
?????
Model should require a minimum amount of samples to work.

To train the model -
Capture samples with the activity status constant: either stopped or moving.

Create models using python (for easier hyper-parameter tuning and testing)

Create a web-server to interface with our android app.

To make a prediction require a minimum amount of rows.
20 Rows = 10 secs

Still need to think about data preparation (outliers if we have them and stuff)
