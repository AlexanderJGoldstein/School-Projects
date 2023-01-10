`include "Mux16.v"
`timescale 1ms/1ms
module Mux16_tb;
    reg [15:0] a = 16'h8E34;
    reg [15:0] b = 16'h71cb;
    reg sel = 1'b1;
    wire [15:0] out;
    Mux16 mux(a, b, sel, out [15:0]);
    initial begin
        $dumpfile("Mux16_tb_dump.vcd");
        $dumpvars(a, a, b, sel, out);
        #1
        if(out == b)
            $display("First test pass");
        else begin
            $display("First test fail");
        end
        #1 sel = 1'b0;
        if(out == a)
            $display("Seconf test pass");
        else begin
            $display("Second test fail");
        end
        #1 b = 16'h921f;
        if(out == a)
            $display("Third test pass");
        else begin
            $display("Third test fail");
        end
        #1 sel = 1'b1;
        if(out == b)
            $display("Fourth test pass");
        else begin
            $display("Fourth test fail");
        end
        #1 b = 16'h6de0;
        if(out == b)
            $display("Fifth test pass");
        else begin
            $display("Fifth test fail");
        end
        #1;
    end
endmodule