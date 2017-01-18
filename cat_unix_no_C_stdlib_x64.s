## 64 bit CAT BARE BONE NO C Library INMPLEMENT
## In the lines below, I reference the following tutorial in helping me
## with writing the correct code, especially with $60, $rax, etc: 
## The credit for learning and implementing the correct syscall op codes:
## https://en.wikibooks.org/wiki/X86_Assembly/Interfacing_with_Linux
    
    .equ bufferSize, 1024
   	.lcomm buffer, bufferSize #creates buffer
	.globl	_start

_start:
	pushq	%rbp                # save %rbp on stack
	movq	%rsp, %rbp          # store the value of %rsp (stack pointer) in %rbp (set base pointer to the stack pointer's address)
	jmp	.L2                     # calling .L2 next 
.L3:
	movq	$1, %rax            # The write syscall 
	movq    $1, %rdi            # write to stdout (what's seen on terminal)
	movq    $buffer, %rsi       # contents of what to stdout
	movq    %rsp, %rdx          # size of bytes, size to stdout
	syscall                     # invoke syscall
.L2:
	movq    $0, %rax            # x86 to read  
	movq    $0, %rdi            # x86 for stdin (to read from command line)
	movq    $buffer, %rsi       # stdin store what was read from command line
	movq    $bufferSize, %rdx   # the size to be read per buffer
	syscall                     # invoke syscall
	movq	%rax, %rsp          # 
	cmpq	$0, %rsp            # if end of file, then jump.
	jne	.L3                     # begin writing out
	movq    $60, %rax           # use the _exit syscall (60 in x86 Assembly Interfacing with Linux)
	xor 	%rdi, %rdi          # return error code 0
	syscall                     # actually make syscall, invoke OS to exit

	leave					    # destroys stack frame
	ret
	