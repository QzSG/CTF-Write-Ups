# Tuesday

## Change

We download a jpg, using exif tool shows javascript hidden in copyright field

Extract to a js file using 

```bash
exiftool change.jpg -s -s -s -copyright > extract.js
```

Run the javascript in chrom devtools and see the flag output to console.

Flag : `syskronCTF{l00k5l1k30bfu5c473dj5}`

## Bash history
We download a file containing bash_history of commands run

There are a few base64 encoded strings, most of them are bash commands to upload files to termbin, things like passwd and list of files.

There are two echos that does not end in `| bash`

```bash
	Line 130: echo xYTjBNR3hsTFdGc2JDMUVZWFJoSVNGOQ==
	Line 137: echo ZWNobyBjM2x6YTNKdmJrTlVSbnQwU0dWNU
```

if u decode them on their own they seem illegible but if u put them together and do 

```bash
echo ZWNobyBjM2x6YTNKdmJrTlVSbnQwU0dWNUxYTjBNR3hsTFdGc2JDMUVZWFJoSVNGOQ | base64 -d
``` 
you get `echo c3lza3JvbkNURnt0SGV5LXN0MGxlLWFsbC1EYXRhISF9`, base64 decode that again to get flag

Flag: ```syskronCTF{tHey-st0le-all-Data!!}```


### Security.txt

Verify the PGP message and you get the flag

[Cyberchef link](https://gchq.github.io/CyberChef/#recipe=PGP_Verify('-----BEGIN%20PGP%20PUBLIC%20KEY%20BLOCK-----%5Cn%5CnmDMEX1IeNRYJKwYBBAHaRw8BAQdAGkGzrffXJoSEuPxIZ%2BADdMAH1COdISkwrmFC%5CnZyCWGP%2B0X0JCIEluZHVzdHJ5IGEucy4gUFNJUlQgKHN5c2tyb25DVEZ7V2gwLXB1%5CndDMtZmxhZzMtMW50by0wcGVuUEdQLWtleTM/Pz99KSA8cHNpcnRAYmItaW5kdXN0%5CncnkuY3o%2BiJYEExYIAD4WIQQb0Dqaer1Y3W4NxowpcUAVAB/owgUCX1IeNQIbAwUJ%5CnAE8aAAULCQgHAgYVCgkICwIEFgIDAQIeAQIXgAAKCRApcUAVAB/owkYsAP9uMtdg%5Cn0YInW%2BJgxdZbGhP7dQS7Bv1fKARx2GDcVUt7BAD/cgkM1BSC3jT1PuutPA7HDwC7%5Cn709QGbka8o/G1t9EBwE%3D%5Cn%3DmLiy%5Cn-----END%20PGP%20PUBLIC%20KEY%20BLOCK-----')&input=LS0tLS1CRUdJTiBQR1AgU0lHTkVEIE1FU1NBR0UtLS0tLQpIYXNoOiBTSEE1MTIKCiMgQ2Fub25pY2FsIFVSTApDYW5vbmljYWw6IGh0dHBzOi8vd3d3LnNlbm9yay5kZS8ud2VsbC1rbm93bi9zZWN1cml0eS50eHQKCiMgT3VyIHNlY3VyaXR5IHBvbGljeQpQb2xpY3k6IGh0dHBzOi8vd3d3LnNlbm9yay5kZS9zZWN1cml0eS8KCiMgT3VyIHNlY3VyaXR5IGFja25vd2xlZGdtZW50cyBwYWdlCkFja25vd2xlZGdtZW50czogaHR0cHM6Ly93d3cuc2Vub3JrLmRlL3NlY3VyaXR5LyNhY2tub3dsZWRnbWVudHMKCiMgT3VyIHNlY3VyaXR5IGFkZHJlc3MKbWFpbHRvOnBzaXJ0QGJiLWluZHVzdHJ5LmN6CgojIE91ciBPcGVuUEdQIGtleQpFbmNyeXB0aW9uOiBodHRwczovL3d3dy5zZW5vcmsuZGUvb3BlbnBncC5hc2MKCiMgUHJlZmVycmVkIGxhbmd1YWdlcwpQcmVmZXJyZWQtTGFuZ3VhZ2VzOiBlbiwgY3MKCiMgRXhwaXJpbmcgZGF0ZSBvZiB0aGlzIGZpbGUKRXhwaXJlczogVGh1LCAzMSBEZWMgMjAyMCAyMDowMDowMCArMDEwMAotLS0tLUJFR0lOIFBHUCBTSUdOQVRVUkUtLS0tLQoKaUhVRUFSWUtBQjBXSVFRYjBEcWFlcjFZM1c0Tnhvd3BjVUFWQUIvb3dnVUNYMUllZkFBS0NSQXBjVUFWQUIvbwp3c3BqQVFERGdFL2NIZWJwb0pRS0lGVlF1a1ZXb05UaEErNTNQdjduSGFaZzJlOUt2UUQrTHJvZXJ1YjRJalBFCjc5NDFJQmJGbnNpWVI5ZU9ic0F5aDYrc0x4WlJyd2M9Cj1xNFZVCi0tLS0tRU5EIFBHUCBTSUdOQVRVUkUtLS0tLQ)

Flag : `syskronCTF{Wh0-put3-flag3-1nto-0penPGP-key3???}`

## Leak audit

Simple sql statements, load the db file with your sql server or use `https://sqliteonline.com/`

1. ```sql
   SELECT count(*) from personal;
   ```

   Ans : `376`

2. ```sql
   SELECT distinct(p1.password) FROM personal p1 JOIN personal p2 ON p1.password = p2.password WHERE p1.givenname || p1.surname != p2.givenname || p2.surname;
   ```
   
   Ans : mah6geiVoo

3. ```sql
   SELECT password FROM personal where password REGEXP '^\$2[ayb]\$.{56}$';
   ```

   Ans : 21

Flag : `376_mah6geiVoo_21`

