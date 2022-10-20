load securityCircuit.hdl,
output-file securityCircuit.out,
compare-to securityCircuit.cmp,
output-list mS%B3.1.3 dA%B3.1.3 pB%B3.1.3 rB%B3.1.3 out%B3.1.3;

set mS 1,
set dA 1,
set pB 0,
set rB 0,
eval,
output;

set mS 0,
set dA 0,
set pB 1,
set rB 0,
eval,
output;

set mS 0,
set dA 1,
set pB 0,
set rB 0,
eval,
output;

set mS 0,
set dA 0,
set pB 1,
set rB 1,
eval,
output;

set mS 1,
set dA 1,
set pB 0,
set rB 1,
eval,
output;

set mS 1,
set dA 0,
set pB 0,
set rB 0,
eval,
output;