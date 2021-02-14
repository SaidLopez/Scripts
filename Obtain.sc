Program New;
const
Axe=$4006D073;
LogType=$1BDD;
BoardType=$1BD7;
IngotsStorage=$4006D073;
LagWait=10000;
WaitTime=500;
Procedure Obtain(Item:Word);
begin
FindType(Item, IngotsStorage);
MoveItem(FindItem,300,Backpack,0,0,0);
wait(LagWait);
end;
begin
Obtain(LogType);
end.