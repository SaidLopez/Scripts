Program LeatherFarmer;
var
Animals,Hides,bodies:Array of Word;
i,j:integer;

const
StartX= ;
StartY = ;
Butcher = ;
RuneBook = ;
TripM = 75 // Recall 50 , 75 Sanctum
Container = ;
Cow1 =
Cow2 =
Ciervo1 =
Ciervo2 =
Plain =
Spined = 
Horned = 
Barbed =
body1 = 
body2 =
body3 =
body4 =
WaitTime = 500;

procedure Kill(ID:Cardinal);
begin
    while GetHP(ID)>0 do begin
    NewMoveXY(GetX(ID),GetY(ID),True,1,True);
    Wait(2000);
    Attack(ID);
    Wait(2000);
    end;
end;
procedure Travel(ID:Cardinal,ref:integer);
var
x,y:integer
begin
    x:=GetX(Self);
    y:=GetY(Self);
   while x=GetX(self) and y=GetY(self) do begin
    UseObject(ID);
    Wait(WaitTime);     
    NumGumpButton(GetGumpsCount-1,ref);
    Wait(2000);
    x:=GetX(Self);
    y:=GetY(Self);    
    end;
end;
procedure  Carve(ID:Cardinal);
begin
    UseObject(Butcher);
    WaitForTarget(5000);
    TargetToObject(ID);
    Wait(WaitTime);
    UseObject(ID);
    for i:=0 to Length(Hides)-1 do begin 
    if FindType(Hides[i], ID)> 0 then MoveItem(FindItem,GetQuantity(Finditem),Backpack,0,0,0);
    CheckLag(LagWait);
    end;
    
end;
procedure Drop(Item:Array of Word);
var
i:integer;
begin
if not Connected then Exit;
 for i:=0 to Length(Item)-1 do begin 
 if FindType(Item[i], Backpack)> 0 then MoveItem(FindItem,GetQuantity(Finditem),Container,0,0,0);
 CheckLag(LagWait);
 end;
  
FindDistance:=20;
FindVertical:=5; 
Animals:=[Cow1,Cow2,Ciervo1,Ciervo2]; 
Hides:=[Plain,Spined,Horned,Barbed];
bodies:=[body1,body2,body3,body4];

begin
  While Connected do begin
  If GetX(Self)<>StartX or GetY(Self)<>StartY then Travel(Runebook,2*TripM);
    for i:=0 to Length(Animals)-1 do begin
       if Findtype(Animals[i],Ground)>0 then begin   
        Kill(Finditem);
        for j:=0 to Length(bodies)-1 do begin
        If FindType(bodies[i],Ground)>0 then begin
        Carve(Finditem);
        wait(WaitTime);
        Ignore(Finditem);
        end;
       end;
       if Weight > (MaxWeight-50) then begin
       Travel(Runebook,1*TripM);
       Wait(WaitTime);
       Drop(Hides);
       Wait(3000);
       Travel(Runebook,2*TripM);
       Wait(WaitTime);
       NewMoveXY(StartX,StartY,True,1,True);
    end;   
    If GetX(Self)<>StartX or GetY(Self)<>StartY then NewMoveXY(StartX,StartY,True,1,True);
  end;
end.