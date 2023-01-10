module TFlipFlop (clk, toggle, q);
    input clk, toggle;
    output reg q;

    always @(posedge clk) begin
        if(toggle)
            q <= ~q;
        else 
            q <= q;
    end
endmodule
module DFlipFlop (clk, data, q);
    input clk, data;
    output reg q;
    
    always @(posedge clk) begin
        q <= data;
    end
endmodule