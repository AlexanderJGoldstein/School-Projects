`include "DMux.v"
`include "Dmux4Way.v"
module DMux8Way(    input in,
                    input [1:0] sel,
                    output a, b, c, d, e, f, g, h);
    DMux Dmux1(in, sel[1:1], abcd, efgh);
    DMux4Way Dmux2(abcd, sel[1:0], a, b, c, d);
    DMux4Way Dmux3(efgh, sel[1:0], e, f, g, h);
endmodule