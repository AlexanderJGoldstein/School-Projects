`include "DMux.v"
module DMux4Way(    input in,
                    input [1:0] sel,
                    output a, b, c, d);
    DMux Dmux1(in, sel[1:1], ab, cd);
    DMux Dmux2(ab, sel[0:0], a, b);
    DMux Dmux3(cd, sel[0:0], c, d);
endmodule