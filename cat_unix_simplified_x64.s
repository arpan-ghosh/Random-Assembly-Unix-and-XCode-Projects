#Arpan Ghosh

## BAREBONE CAT ASSEMBLY

## Platform I ran this on: BASH (UBUNTU) on Windows 10

## We don't need to display what the file name is through
## the .file AT&T syntax. It's not necessary for the assembler.
## We also don't need .text.
## UNIX assembler is natively able to figure out what the
## file is as long as the .glob1 main header is there.
## Removed all the .cfi (those are directives for GNU
## assemblers). These could have been disabled by
## running -fno-asynchronous-unwind-tables with gcc
## We also do not need .lfe2 because it does not have
## anything to do with the compilation, only debugging
## and additional information for developers.

## %eax and %ecx are general purpose registers
## %rbp and %rsp are special purpose registers
## %rbp is the base pointer which points to the base of the current stack frame
## %rsp is the stack pointer which points to the top of the current stack frame
## registries that start with "r" 64 bit, "e" 32 bit
## can get rid of subq because UNIX is smart enough to allocate stack space without
## assembly code instruting it to do so


	.globl	main
main:                           # main function
.LFB2:
	pushq	%rbp                # save %rbp on stack
	movq	%rsp, %rbp          # store the value of %rsp (stack pointer) in %rbp (set base pointer to the stack pointer's address)
	jmp	.L2                     # calling .L2 next 
.L3:
	movl	-4(%rbp), %eax      # getchar contents now moved to %eax register (de-allocation)
	movl	%eax, %edi          # getchar contents now moved to %edi to be called
	call	putchar             # this is the purchar(c), display contents onto command line
.L2:
	call	getchar             # In C, this is the begining of the 
	                            # while(c=getchar) call
	movl	%eax, -4(%rbp)      # move getchar contents from eax into stack
	cmpl	$-1, -4(%rbp)       # comparing values in stack of end of file (get char while not end of file)
	jne	.L3                     # conditional jump, if end of file go to L3 --> putchar(c);
	movl	$0, %eax            # register is zeroed and cleared due to end of program
	leave					    # destroys stack frame
	ret
