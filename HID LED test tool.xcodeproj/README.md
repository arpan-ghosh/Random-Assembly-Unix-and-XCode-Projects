# Coolermaster Keyboards (Light all keys in 5 passes, download and run my xcodeproj)

Non-native Apple keyboards that have the ability to use LED can be powered using Apple's little xcode hack caled HID LED test tool, since Apple's default state does not automatically detect LED enabled keys in most keyboards.

For this file, which is originally created by Apple, I edit the amount of passes for this particular keyboard: http://www.coolermaster.com/peripheral/keyboards/devastator/. Instead of waiting up to three minutes to detect all 256 combination of passes, we can do a simple hack to have it stop within 10 seconds at 5 passes. This works great for the cooler master line of keyboards, but other keyboard companies may need a total of 256 passes to be safe. 
