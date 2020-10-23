# Thursday

## Contact Card

Extract all the files
Run `binwalk` on all the files, one of them is a Win PE Executable
Open `www.random4.cpl` in Ghildra, or simply use strings
We get 

> Thanks for the entrance! We pasted this for you: xSAvEyND. Accept?

pasted this for you is human speak for go to pastebin.com/raw/xSAvEyND

(Why is this 400 points, solved this in a min but I cannot solve the rest the day before LOL)

Flag: `syskronCTF{n3v3r_c11ck_unkn0wn_11nk5}`

## Security Advisory

We get a pdf that seems to contain gibberish at first

Looking closely we see `Blowfish/8` , it is not a product but an encryption algorithm.

Further on we see a suspicious `OFB` mode, one of the encryption modes for blowfish, but we don't have any text to decrypt or any key & iv. Or do we?

The clients claimed to be vulnerable looks like valid hex
> cf1c1a057d47, 86a9ef0f5a74, 4e6fa75810fc, and 855f3945f731  
  826610fc022a, e726719dc183, b7451dc8f5bf, and 3e3c8ad7bc55

but they are not 8 bytes (expected for blowfish key and iv length)

Searching further we find the metadata has a suspicious field

`<xmp:CreatorTool>|64616d6e206d657461646174613b206865726520796f7520676f3a20325a646b4b5474707247646448683333|</xmp:CreatorTool>`

Decoding that from hex gives us

> damn metadata; here you go: 2ZdkKTtprGddHh33

`2ZdkKTtprGddHh33` is not 8 bytes but it is 16 bytes what if we split it into 2 for key and iv and use the rest as the encrypted text in CyberChef?

[Cyberchef](https://gchq.github.io/CyberChef/#recipe=Blowfish_Decrypt(%7B'option':'UTF8','string':'2ZdkKTtp'%7D,%7B'option':'UTF8','string':'rGddHh33'%7D,'OFB','Hex','Raw')&input=Y2YxYzFhMDU3ZDQ3ODZhOWVmMGY1YTc0NGU2ZmE3NTgxMGZjODU1ZjM5NDVmNzMxODI2NjEwZmMwMjJhZTcyNjcxOWRjMTgzYjc0NTFkYzhmNWJmM2UzYzhhZDdiYzU1)

AND we have our flag

Flag : `syskronCTF{you-Just-anaLyzed-your-1st-advisorY}`