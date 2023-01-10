module Mux(a, b, sel, out);
    input a, b, sel;
    output out;
    assign out = (a & ~sel) || (b & sel);
endmodule