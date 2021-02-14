Program New;
Function Honor(TargetID:Integer):Boolean;
var
    myResult : Boolean;
begin
    myResult := False;
    ReqVirtuesGump();
    UseVirtue('Honor');
    if(WaitForTarget(500) = True)and (GetDistance(TargetID) < 6) then
    begin
        TargetToObject(TargetID);
        myResult := False;        
    end;                  
    Result := myResult;
end;
begin
 //Body of Script
end.