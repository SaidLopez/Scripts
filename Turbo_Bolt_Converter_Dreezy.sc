Program Bolts_of_Cloth;

const
wool = $0DF8; 
bolt = $0F95;
Spinning = $40001A1E;
yarn = $0E1D;
assy =$40001A2F;
Backpackid = $4000543D;


begin
    While Connected do begin      
    NewMoveXY(1471,1699,true,0,true);
    NewMoveXY(1471,1696,true,0,true); 
    OpenDoor;
    wait(200);
    NewMoveXY(1472,1690,true,0,true);
    while (Findtype(wool,backpack)>0) do begin
       UseObject(Finditem);
       wait(500);
       TargetToObject(Spinning);
       wait(3000);
    end;          
    NewMoveXY(1473,1686,True,0,true);
      while (Findtype(yarn,backpack)>0) do begin
       UseObject(Finditem);
       wait(500);
       TargetToObject(assy);
       wait(500);
    end; 
     NewMoveXY(1471,1694,True,0,true);
     Opendoor;
     wait(200); 
     NewMoveXY(1434,1694,True,0,true);
     UOSay('Bank'); 
     If (Findtype(bolt,backpack)>0)then MoveItem(Finditem,GetQuantity(Finditem),Backpackid,0,0,0);  
     wait(500);
     UseObject(BackpackID);
     wait(500);
     If (Findtype(wool,backpackid)>0)then MoveItem(Finditem,20,Backpack,0,0,0);
     wait(500);
    end;
end.