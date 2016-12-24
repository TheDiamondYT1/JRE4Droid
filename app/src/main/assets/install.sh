#!/system/bin/sh
# Java Installer by TheDiamondYT
#
# Set DIR variable
dir=$(dirname $0)
busybox=$dir/busybox
#
# Mount / read write
$busybox mount -o rw,remount /
#
# Create java directory
$busybox mkdir $dir/java
#
# Extract java.zip here
$busybox unzip /sdcard/.JRE4Droid/java.zip -d $dir/java
#
# Create directory for java libs
$busybox mkdir /lib
#
# Umount
$busybox umount /lib
#
# Bind folders
$busybox mount -o bind $dir/java/lib /lib
#
# Mount / read only
$busybox mount -o ro,remount /
#
#
#
# (The Android Shell doesn't like whitespaces)
