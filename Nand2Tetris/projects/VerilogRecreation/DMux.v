module DMux (d, sel, a, b);
    input d, sel;
    output a, b;

    assign b = d & sel;
    assign a = d & ~sel;
endmodule