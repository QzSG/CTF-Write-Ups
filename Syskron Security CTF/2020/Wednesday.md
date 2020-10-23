# Wednesday

## HID

We are given a binary file from the supposed USB device
using `file` just shows it as binary data

Using a hex viewer like `HxD` along with the free hint made me think of USB HID scancodes as well as some url

So I went to google for the scancodes and tried to convert `http` and got
`0B 00 17 00 17 00 13 00`, searched the hex and indeed found `https`, looks like its a url, I could have written a program to convert the rest for me but I decided to eyeball it. (If you didn't get it, its a german keyboard layout)

`https://pastebin.com/raw/ZRD8jsvd` whichs reveals some powershell code with the flag in plain sight

Flag: `syskronCTF{y0u_f0und_m3}`