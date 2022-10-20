// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

//Alex Goldstein
// Put your code here.
(LOOP)
    @KBD
    D=M;
    @i
    M=0
    @CHANGE_TO_BLACK
    D;JNE
    @CHANGE_TO_WHITE
    D;JEQ
    @LOOP
    0;JMP


(CHANGE_TO_BLACK)
    @KBD
    D=M
    @LOOP
    D;JEQ
    @i
    D=M
    @SCREEN
    A=A+D
    M=-1
    @8192
    D=A-D
    @LOOP
    D;JLE
    @i
    M=M+1
    @CHANGE_TO_BLACK
    0;JMP
(CHANGE_TO_WHITE)
    @KBD
    D=M
    @LOOP
    D;JNE
    @i
    D=M
    @SCREEN
    A=A+D
    M=0
    @8192
    D=A-D
    @LOOP
    D;JLE
    @i
    M=M+1
    @CHANGE_TO_WHITE
    0;JMP