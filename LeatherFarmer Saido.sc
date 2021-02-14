Program LeatherFarmer;
const
LagWait = 10000;
Butcher = $1401 ;
Container = ;
AnimalTypes = ;
HideTypes = ;
WaitTime = 500;
procedure kill();
begin
end
procedure GoFarm();
begin
    //  NewMoveXY(1420,1710,true,0,true);
    //  NewMoveXY(1412,1733,true,0,true); 
    //  NewMoveXY(1384,1749,true,0,true);
    //  NewMoveXY(1353,1765,true,0,true);
    //  NewMoveXY(1330,1787,true,0,true); 
   //   NewMoveXY(1310,1804,true,0,true);
    //  OpenDoor();   
      NewMoveXY(1316,1804,true,0,true);    
end;
procedure GoFarm();
begin
    //  NewMoveXY(1420,1710,true,0,true);
    //  NewMoveXY(1412,1733,true,0,true); 
    //  NewMoveXY(1384,1749,true,0,true);
    //  NewMoveXY(1353,1765,true,0,true);
    //  NewMoveXY(1330,1787,true,0,true); 
   //   NewMoveXY(1310,1804,true,0,true);
    //  OpenDoor();   
      NewMoveXY(1420,1,true,0,true);    
end;
procedure carve(animal:cardinal);
begin

end;
procedure drop(Item:Array of Word);
begin
 while FindType(HideTypes, Backpack)> 0 do begin
 if not Connected then Exit;
 MoveItem(FindItem,GetQuantity(Finditem),Container,0,0,0);
 CheckLag(LagWait);
 end;

begin
 
end.