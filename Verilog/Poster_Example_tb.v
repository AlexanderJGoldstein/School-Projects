`include "Poster_Example1.v"
module TFlipFlop_TB;
    reg clk = 0;
    reg toggle = 0;
    wire q;

    TFlipFlop TFF1 (clk, toggle, q);
    always begin
        #2 clk = 1;
        #2 clk = 0;
    end

    initial begin
        $dumpfile("TFFdump.vcd");
        $dumpvars(clk, clk, toggle, q);
            #5 toggle = 1;
            #4 toggle = 0;
            #6 toggle = 1;
            #9 toggle = 0;
            #5
        $finish;
    end

endmodule