@256
D=A
@SP
M=D
@RET0
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
D=M
@5
D=D-A
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(RET0)
@SP
A=M

(Sys.init)
@SP
A=M
M=0
@SP
A=M

@6
D=A
@SP
A=M
M=D
@SP
M=M+1
A=M

@8
D=A
@SP
A=M
M=D
@SP
M=M+1
A=M

@RET1
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
D=M
@5
D=D-A
@2
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(RET1)
@SP
A=M

@R5
D=A
@SP
A=M
M=D
@SP
M=M-1
A=M
D=M
A=A+1
A=M
M=D
@SP
A=M

@23
D=A
@SP
A=M
M=D
@SP
M=M+1
A=M

@15
D=A
@SP
A=M
M=D
@SP
M=M+1
A=M

@RET2
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
D=M
@5
D=D-A
@2
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(RET2)
@SP
A=M

@R5
D=A
@SP
A=M
M=D
@SP
M=M-1
A=M
D=M
A=A+1
A=M
M=D
@SP
A=M

@RET3
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
D=M
@5
D=D-A
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.get
0;JMP
(RET3)
@SP
A=M

@RET4
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
D=M
@5
D=D-A
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.get
0;JMP
(RET4)
@SP
A=M

(WHILE)
@SP
A=M

@WHILE
0;JMP
@SP
M=M+1
A=M

(Class1.set)
@SP
A=M
M=0
@SP
A=M

@0
D=A
@ARG
A=M
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@Class1_static0.j
D=A
@SP
A=M
M=D
@SP
M=M-1
A=M
D=M
A=A+1
A=M
M=D
@SP
A=M

@1
D=A
@ARG
A=M
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@Class1_static1.j
D=A
@SP
A=M
M=D
@SP
M=M-1
A=M
D=M
A=A+1
A=M
M=D
@SP
A=M

@0
D=A
@SP
A=M
M=D
@SP
M=M+1
A=M

@LCL
D=M
@R13
M=D
@5
D=A
@R13
A=M-D
D=M
@R14
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@1
D=A
@R13
A=M-D
D=M
@THAT
M=D
@2
D=A
@R13
A=M-D
D=M
@THIS
M=D
@3
D=A
@R13
A=M-D
D=M
@ARG
M=D
@4
D=A
@R13
A=M-D
D=M
@LCL
M=D
@R14
A=M
0;JMP
@SP
A=M

(Class1.get)
@SP
A=M
M=0
@SP
A=M

@Class1_static0.j
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@Class1_static1.j
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M-D
@SP
M=M+1
A=M

@LCL
D=M
@R13
M=D
@5
D=A
@R13
A=M-D
D=M
@R14
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@1
D=A
@R13
A=M-D
D=M
@THAT
M=D
@2
D=A
@R13
A=M-D
D=M
@THIS
M=D
@3
D=A
@R13
A=M-D
D=M
@ARG
M=D
@4
D=A
@R13
A=M-D
D=M
@LCL
M=D
@R14
A=M
0;JMP
@SP
A=M

(Class2.set)
@SP
A=M
M=0
@SP
A=M

@0
D=A
@ARG
A=M
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@Class2_static0.j
D=A
@SP
A=M
M=D
@SP
M=M-1
A=M
D=M
A=A+1
A=M
M=D
@SP
A=M

@1
D=A
@ARG
A=M
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@Class2_static1.j
D=A
@SP
A=M
M=D
@SP
M=M-1
A=M
D=M
A=A+1
A=M
M=D
@SP
A=M

@0
D=A
@SP
A=M
M=D
@SP
M=M+1
A=M

@LCL
D=M
@R13
M=D
@5
D=A
@R13
A=M-D
D=M
@R14
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@1
D=A
@R13
A=M-D
D=M
@THAT
M=D
@2
D=A
@R13
A=M-D
D=M
@THIS
M=D
@3
D=A
@R13
A=M-D
D=M
@ARG
M=D
@4
D=A
@R13
A=M-D
D=M
@LCL
M=D
@R14
A=M
0;JMP
@SP
A=M

(Class2.get)
@SP
A=M
M=0
@SP
A=M

@Class2_static0.j
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@Class2_static1.j
D=M
@SP
A=M
M=D
@SP
M=M+1
A=M

@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M-D
@SP
M=M+1
A=M

@LCL
D=M
@R13
M=D
@5
D=A
@R13
A=M-D
D=M
@R14
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@1
D=A
@R13
A=M-D
D=M
@THAT
M=D
@2
D=A
@R13
A=M-D
D=M
@THIS
M=D
@3
D=A
@R13
A=M-D
D=M
@ARG
M=D
@4
D=A
@R13
A=M-D
D=M
@LCL
M=D
@R14
A=M
0;JMP
@SP
A=M
