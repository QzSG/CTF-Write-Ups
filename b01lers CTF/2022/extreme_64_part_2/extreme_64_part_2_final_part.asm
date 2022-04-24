mov r8, rsi;
mov r9, 0;
mov r12, 0x8282828282828282;
loop:
mov rax, [r8];
xor rax, r12;
push rax;
mov  rdx, 0x8     ;
mov  rsi, rsp     ;
mov  rax, 0x1     ;
mov  rdi, 0x1     ;
syscall           ;
add r9, 1		  ;
add r8, 8		  ;
cmp r9, 0x4;
jne loop;
