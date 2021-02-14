Program LeatherFarmer;
var
Animals,Hides,bodies:array of word;
i,j:integer;

const
StartX = 1431 ;
StartY = 1696;
Butcher = $40004DF5;
Scissors = $40004FD1;
//RuneBook = ;
//TripM = 75 // Recall 50 , 75 Sanctum
Container = $400051E5 ;
Cow1 = $00CF;
Cow2 = $00E9;
Ciervo1 = $00D8;
Ciervo2 = $00E7;
Cow3 = $00CD;
Plain = $1079;
wool = $0DF8;
cut = $1081;
//Spined = 
//Horned = 
//Barbed =
body1 = $2006;
body2 = $2006;
//body3 =
//body4 =
WaitTime = 500;


procedure Kill(ID:Cardinal);
begin
    while GetHP(ID)>0 do begin
    NewMoveXY(GetX(ID),GetY(ID),True,1,True);
    Wait(WaitTime);
    Attack(ID);
    end;
end;
procedure TravelFarm();

begin
    NewMoveXY(1308,1802,true,0,true);  
    NewMoveXY(1310,1803,true,0,true);
    OpenDoor;
    NewMoveXY(1316,1804,true,0,true); 
end;
procedure TravelBank();

begin
    NewMoveXY(1314,1804,true,0,true); 
    NewMoveXY(1312,1804,true,0,true); 
    Opendoor;
    Wait(WaitTime);
    NewMoveXY(1431,1696,true,0,true); 
end;
procedure  Carve(ID:Cardinal);
var
i:integer;
begin
    NewMoveXY(GetX(ID),GetY(ID),True,0,True);
    UseObject(Butcher);
    WaitForTarget(5000);
    TargetToObject(ID);
    Wait(WaitTime);
    UseObject(ID);
  for i:=0 to Length(Hides)-1 do begin 
   if FindType(Hides[i], ID)> 0 then MoveItem(FindItem,GetQuantity(Finditem),Backpack,0,0,0);
    CheckLag(WaitTime);
 end; 
 End;  

procedure Drop(Item:Array of Word);
var
i:integer;
begin
if not Connected then Exit;
 for i:=0 to Length(Item)-1 do begin 
 if FindType(Item[i], Backpack)> 0 then MoveItem(FindItem,GetQuantity(Finditem),Container,0,0,0);
 wait(WaitTime);
 end;  
 end;
 Procedure CheckWeight();
 Begin     
  if Weight > 190 then
    begin
        TravelBank();
       UOsay('Bank');
       Wait(WaitTime);
       Drop(Hides);
       Wait(3000);
       TravelFarm();
    end;
 end;
 
 
begin  
FindDistance:=20;
FindVertical:=5; 
Animals:=[Cow1,Cow2,Cow3,Ciervo1,Ciervo2]; 
Hides:=[Plain,wool,cut];
bodies:=[body1,body2];

begin
  While Connected do begin
  CheckWeight();
//  TravelFarm();
    for i:=0 to Length(Animals)-1 do begin 
    if not Connected then Connect;
       if Findtype(Animals[i],Ground)>0 then begin   
        Kill(Finditem);
        for j:=0 to Length(bodies)-1 do begin
        If FindType(bodies[j],Ground)>0 then begin
        Carve(Finditem);
        wait(WaitTime);
        Ignore(Finditem);
        if Findtype(Plain,Backpack)>0 then begin
            UseObject(Scissors);
            WaitForTarget(5000);
            TargetToObject(Finditem);  
            wait(WaitTime);
            end;
        end;
       end;
       CheckWeight();
      // NewMoveXY(StartX,StartY,True,0,True);
       
    //If GetX(Self)<>StartX or GetY(Self)<>StartY then NewMoveXY(StartX,StartY,True,1,True);
  end;
end;
end;
end;
end.