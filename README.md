https://developer.android.com/reference/android/net/wifi/WifiInfo
https://developer.android.com/reference/android/net/wifi/WifiManager

https://stackoverflow.com/questions/51305890/telephonymanager-requestnetworkscan-android-p-preview

Data Row
Timestamp - Type of signal (3G / WIFI) - RSSI (dBm)

Take a sample every .5s
Model should require a minimum amount of samples to work.

To train the model -
Capture samples with the activity status constant: either stopped or moving.

Create models using python (for easier hyper-parameter tuning and testing)

Create a web-server to interface with our android app.

To make a prediction require a minimum amount of rows.
20 Rows = 10 secs

Still need to think about data preparation (outliers if we have them and stuff)
