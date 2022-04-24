from pwn import *

with open('strings.txt') as file:
	lines = [line.rstrip() for line in file]

print(len(lines))

r = remote('ctf.b01lers.com',9202)

for line in lines:
	resp = r.recvuntil("Guess me a string of length 4 with lowercase letters:").decode()
	print(resp + "\n")
	r.sendline(line.encode())    
	if "Your current balance: 990" in resp :
		break
	

for l in r.recvlines(5, timeout= 2):
	print(l.decode())
	
#bctf{n0_pr4153_f0r_RNGesus}
