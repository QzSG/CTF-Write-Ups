# Wednesday

## Screenshot

If you use StegSolve or StegOnline and browse the bit planes, you see something interesting

`Red 1` has `Doesnt seem so significant to me`
`Green 0` has some telltale signs of data and `This is more interesting`

After extracting selecting bit plane Green 0 with pixel order column and running strings through it we get the flag, easy

Flag: `syskronCTF{s3cr3T_m3sS4g3}`

## HID

We are given a binary file from the supposed USB device
using `file` just shows it as binary data

Using a hex viewer like `HxD` along with the free hint made me think of USB HID scancodes as well as some url

So I went to google for the scancodes and tried to convert `http` and got
`0B 00 17 00 17 00 13 00`, searched the hex and indeed found `https`, looks like its a url, I could have written a program to convert the rest for me but I decided to eyeball it. (If you didn't get it, its a german keyboard layout)

`https://pastebin.com/raw/ZRD8jsvd` whichs reveals some powershell code with the flag in plain sight

Flag: `syskronCTF{y0u_f0und_m3}`

## Stolen Licenses

We first tried to crack the zip using `zip2john` and `john` with both wordlists like rockyou and english lists so we used the first hint

It says possibly single word from recently added words to well know dictionary.

So we went and searched for recently added words by Oxford as well as Merriam-Webster and compiled them into a `list.txt`

For example for Merriam-Webster, we can do the following to get the list of words in their words at play column

```js
list = document.getElementsByTagName("em")
str = ""
list.forEach((val, ind, arr) => { str = str + val.innerHTML + "\n"})    
copy(str.toLowerCase())
```

u can then paste them into a list.txt and pass it to john using
```bash
zip2john licenses.zip > licenses.hash
fcrackzip licenses.zip -v -D -p list.txt
```

And our zip password `nosocomephobia`

Thats like 1/3 if the challenge

Now we have a list of images which we need to extract the text and get the check digits to see which one is valid

Extract and cd into the img folder and run

```bash
mkdir crop
for FILE in *.png; do magick -extract 600x50+100+450 $FILE crop/$FILE; done
```

After extracting the keys convert them all to text with 

```bash
mkdir txt && cd txt
for i in *.png; do b=`basename "$i" .png`; tesseract "$i" txt/$b ;done
cd txt
for i in *.txt; do cat $i | tr -cd [:digit:] > $i ;done
```

Python script to check if valid
```py3
import os
import fast_luhn as fl

def read_first_line(filename):
    with open(filename) as f:
        return f.readline()

for filename in os.listdir(os.getcwd()):
    if os.path.isfile(filename) and filename.endswith(".txt"): 
        str = read_first_line(filename)
        if fl.validate(str):
            print(str)
```

Run it and bam

flag : `78124512846934984669`