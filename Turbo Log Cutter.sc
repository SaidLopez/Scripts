Program New;
const
Axe=$40070664;
LogType=$1BDD;
IngotsStorage=;
LagWait=10000;
WaitTime=500;
Procedure Obtain(Item:Array of Word);
begin
while FindType(Logtype, IngotsStorage)> 0 do begin
if not Connected then Exit;
MoveItem(FindItem,300,Backpack,0,0,0);
CheckLag(LagWait);
end;
 procedure Move(Item:Array of Word);
var
j:Byte;
begin
  while FindType(LogType,Backpack)>0 do begin
    if Dead or not Connected then Exit;
    UseObject(Axe);
    CheckLag(LagWait);
    WaitForTarget(LagWait);
    TargetToObject(FindItem);
  end;
  CheckLag(LagWait);
  for j:=0 to Length(Item)-1 do begin
    if Dead or not Connected then Exit;
    CheckLag(LagWait);
    While (FindType(Item[j], Backpack)>1) do begin
      if Dead or not Connected then Exit;
      MoveItem(Finditem,GetQuantity(Finditem),IngotsStorage,0,0,0);
      CheckLag(LagWait);
      Wait(WaitTime);
    end;
  end;
end;
end.
