# Minecraft-Blocked-Servers

## Description

Since Minecraft 1.9.3 r2 Mojang wants to ban servers if they don't comply with the Minecraft EULA. Mojang contacts
these servers by email ("Mojang BrandEnforcement"). If they still not follow the EULA the server address will be added
(hashed with SHA-1) to the list below. If the Minecraft client wants to connect to the banned server, they will just
disconnect with the message: "java.net.SocketException: Network is unreachable" which actual means the server is down.
(=>misleading message)

Update: Found out that they have also wildcard support. So for example *.server.com can be banned too.

## List of all blocked servers

https://sessionserver.mojang.com/blockedservers

## Reddit post

https://www.reddit.com/r/Minecraft/comments/4h3c6u/mojang_is_blocking_certain_servers_as_of_193_r2/

## Minecraft related code

### In Minecraft 1.9.3 r2

* Disconnect: **File**: ```beg.class``` **Line**: ```52-67``` **Method**:
```java
private void a(String, int)
```
* Download the list and check every entry: **File**: ```bcd.class``` **Line**: 2968-3002 **Method**:
```java
public boolean a(String)
```
* Hash generate from address **File**: bcd.class **Line**: 3006 **Method**:
```java
public boolean a(String)
```