`include "Mux16.v"
module Mux4Way16(
    input [15:0] a, 
    input [15:0] b, 
    input [15:0] c, 
    input [15:0] d, 
    input [1:0] sel,
    output [15:0] out);
    wire [15:0] muxout1;
    wire [15:0] muxout2;
    Mux16 muxset1 (a, b, sel[0], muxout1[15:0]);
    Mux16 muxset2 (c, d, sel[0], muxout2[15:0]);
    Mux16 finalMux (muxout1, muxout2, sel[1], out);
endmodule