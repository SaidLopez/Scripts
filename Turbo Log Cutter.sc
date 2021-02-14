Program TurboLogCutter;
var
Resourses:Array of Word;
const
Axe=$4006D073;
LogType=$1BDD;
BoardType=$1BD7;
IngotsStorage=$40195C01;
LagWait=10000;
WaitTime=500;
Procedure Obtain(Item:Word);
begin
FindTypeEx($1BDD,$FFFF, IngotsStorage,false);
MoveItem(FindItem,200,Backpack,0,0,0);
end;
Procedure Move(Item:Array of Word);
var 
j:Byte;
begin
  while FindType(LogType,Backpack)>0 do begin
    if Dead or not Connected then Exit;
    CheckLag(WaitTime);
    UseObject(Axe);
    CheckLag(LagWait);
    WaitForTarget(LagWait);
    TargetToObject(FindItem);
  end;
  CheckLag(LagWait);
  if Dead or not Connected then Exit;
    While (FindType(Item[j], Backpack)>1) do begin
      if Dead or not Connected then Exit;
      MoveItem(FindItem,GetQuantity(FindItem),IngotsStorage,0,0,0);
      CheckLag(LagWait);
      Wait(WaitTime);
    end;
  end;
begin
Resourses:= [$1BD7, $318F, $3191, $2F5F, $3199, $3190];
While Connected() do begin
Obtain(LogType);
CheckLag(LagWait);
Move(Resourses);
end;
end.
